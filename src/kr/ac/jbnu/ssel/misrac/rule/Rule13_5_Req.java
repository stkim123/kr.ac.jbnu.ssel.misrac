package kr.ac.jbnu.ssel.misrac.rule;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 13.05: (Required) The three expressions of a for statement
 * shall be concerned only with loop control.
 * 
 * The three expressions of a for statement shall be used only for these
 * purposes: First expression - Initialising the loop counter
 * 
 * Second expression - Shall include testing the loop counter, and optionally
 * other loop control variables
 * 
 * Third expression - Increment or decrement of the loop counter
 * 
 * The following options are allowed:
 * 
 * All three expressions shall be present; The second and third expressions
 * shall be present with prior initialisation of the loop counter; All three
 * expressions shall be empty for a deliberate infinite loop.
 * 
 * TODO
 * 
 * @author kang
 */

public class Rule13_5_Req extends AbstractMisraCRule {

    private static final String FOR_ = "for";
//    private static final String[] VIOLATIONOP = { "++", "--"};

    private boolean isFOR_included = false;

    //OPERATOR Array
    private HashSet<String> violationOps = new HashSet<String>();

    public Rule13_5_Req(IASTTranslationUnit ast) {
	super("Rule13_5_Req", false, ast);
	shouldVisitPreprocessor = true;
	shouldVisitExpressions = true;
//	violationOps.addAll(Arrays.asList(VIOLATIONOP));
    }

    public Rule13_5_Req(String ruleID, boolean visitNodes, IASTTranslationUnit ast) {
	super(ruleID, visitNodes, ast);
    }

    @Override
    protected int visit(IASTForStatement statement) {
	if (statement.toString().contains(FOR_)) {
	    isFOR_included = true;
	}
	return super.visit(statement);
    }

    @Override
    protected int visit(IASTExpressionStatement statement) {
	IASTUnaryExpression unaryExp1 = (IASTUnaryExpression) statement.getExpression();

	System.out.println("function name:" + unaryExp1.getOperator());
	if (isFOR_included == true && violationOps.contains(unaryExp1.getOperator())) {
	    isViolated = true;
	    
	    String message = MessageFactory.getInstance().getMessage(2462);
	    violationMsgs.add(
		    new ViolationMessage(this, message + "-- " + unaryExp1.getOperator(), statement));
	    }
	
	return super.visit(statement);
    }
    
    @Override
    protected int visit(IASTBinaryExpression binaryExp) {
	 IASTUnaryExpression unaryExp2 = (IASTUnaryExpression) binaryExp.getOperand1();

		System.out.println("function name:" + unaryExp2.getOperator());
		if (isFOR_included == true && violationOps.contains(unaryExp2.getOperator())) {
		    isViolated = true;
		    
		    String message = MessageFactory.getInstance().getMessage(2462);
		    violationMsgs.add(
			    new ViolationMessage(this, message + "-- " + unaryExp2.getOperator(), binaryExp));
		}
		return super.visit(binaryExp);
    }
}

//public class Rule13_5_Req extends AbstractMisraCRule {
//
//	private static final String FOR = "for";
//	private static final String[] VIOLATION_OPERATOR = { "++", "--" };
//
//    private boolean isFor_included = false;
//
//    private HashSet<String> violationOP = new HashSet<String>();
//
//
//	public Rule13_5_Req(IASTTranslationUnit ast) {
//		super("Rule13_5_Req", false, ast);
//		shouldVisitDeclarations = true;
//		shouldVisitStatements = true;
//	}
//
//	protected int visit(IASTSimpleDeclaration declaration) {
//
//		String[] splitArray = declaration.getRawSignature().split(";");
//
//				
//		if(UNION.equals(splitArray[0])){
//			isViolated = true;
//		
//			String message1 = MessageFactory.getInstance().getMessage(2462);
//			violationMsgs.add(new ViolationMessage(this,getRuleID() + ":" + message1 + "-- " +declaration, declaration));
//			System.out.println("IASTSimpleDeclaration : " + splitArray[0]);	
//			
//			String message2 = MessageFactory.getInstance().getMessage(2463);
//			violationMsgs.add(new ViolationMessage(this,getRuleID() + ":" + message2 + "-- " +declaration, declaration));
//			System.out.println("IASTSimpleDeclaration : " + splitArray[0]);	
//			}		
//		return super.visit(declaration);
//	}
