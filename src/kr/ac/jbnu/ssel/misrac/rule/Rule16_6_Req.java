package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashMap;

import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * The number of arguments passed to a function shall match the number of
 * parameters.
 * 
 * This problem is completely avoided by the use of function prototypes - see
 * Rule . This rule is retained since compilers may not flag this constraint
 * error.
 * 
 * 
 * @author sangjin
 *
 */
public class Rule16_6_Req extends AbstractMisraCRule {
	
	public static HashMap<String, String> store = new HashMap<String, String>();

	public Rule16_6_Req(IASTTranslationUnit ast) {
		super("Rule16_6_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitExpressions = true;
	}
	
	@Override
	protected int visit(IASTSimpleDeclaration declaration) {

		String name="";
		int count=0;
		
		for(IASTNode decl: declaration.getChildren()){
			if(decl instanceof IASTStandardFunctionDeclarator){
				for(IASTNode param: decl.getChildren()){
					if(param instanceof IASTName){
						name = param.getRawSignature();
					}
					else if(param instanceof IASTParameterDeclaration){
						count++;
					}
				}
			}
		}
		store.put(name, count+"");
		
		return super.visit(declaration);
	}

	@Override
	protected int visit(IASTFunctionCallExpression callExpression) {

		String name="";
		int count=0;
		
		for(IASTNode funcCall : callExpression.getChildren()){
			if(funcCall instanceof IASTIdExpression){
				name = funcCall.getRawSignature();
			}
			else if(funcCall instanceof IASTLiteralExpression){
				count++;
			}
		}
		
		if(store.get(name)!=null){
			if(!store.get(name).equals(count+"")){
				//Function call contains fewer arguments than prototype specifies.
				String message1 = MessageFactory.getInstance().getMessage(422);
				violationMsgs.add(new ViolationMessage(this,
						getRuleID() + ":" + message1 + "--" + callExpression.getRawSignature(), callExpression));
				//Function call contains more arguments than prototype specifies.
				String message2 = MessageFactory.getInstance().getMessage(423);
				violationMsgs.add(new ViolationMessage(this,
						getRuleID() + ":" + message2 + "--" + callExpression.getRawSignature(), callExpression));
				//Function called with number of arguments which differs from number of parameters in definition.
				String message3 = MessageFactory.getInstance().getMessage(3319);
				violationMsgs.add(new ViolationMessage(this,
						getRuleID() + ":" + message3 + "--" + callExpression.getRawSignature(), callExpression));
				isViolated = true;
			}
		}
		return super.visit(callExpression);
	}

}