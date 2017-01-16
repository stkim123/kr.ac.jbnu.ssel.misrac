package kr.ac.jbnu.ssel.misrac.rule;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
/**
 * MISRA-C:2004  Rule  20.11:  (Required)	[next]		
 * The library functions abort, exit, getenv and system from library <stdlib.h> shall not be used.
 * 
 * These functions will not normally be required in an embedded system, which does not normally 
 * need to communicate with an environment. If the functions are found necessary in an application, 
 * then it is essential to check on the implementation-defined behaviour of the function in the 
 * environment in question.
 * 
 */
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * The library functions abort, exit, getenv and system from library <stdlib.h> shall not be used.
 *
 * These functions will not normally be required in an embedded system, 
 * which does not normally need to communicate with an environment. 
 * If the functions are found necessary in an application, 
 * then it is essential to check on the implementation-defined behaviour of the function in the environment in question.
 * 
 * DONE!!
 * @author stkim
 *
 */
public class Rule20_11_Req extends AbstractMisraCRule {

    private static final String STDLIB_H_ = "stdlib.h";
    private static final String[] VIOLATION_FUNCS = { "abort", "exit", "getenv", "system" };

    private boolean isSTDLIB_H_included = false;

    private HashSet<String> violationFunctions = new HashSet<String>();

    public Rule20_11_Req(IASTTranslationUnit ast) {
	super("Rule20_11_Req", false, ast);
	shouldVisitPreprocessor = true;
	shouldVisitExpressions = true;
	violationFunctions.addAll(Arrays.asList(VIOLATION_FUNCS));
    }

    public Rule20_11_Req(String ruleID, boolean visitNodes, IASTTranslationUnit ast) {
	super(ruleID, visitNodes, ast);
    }

    @Override
    protected int visit(IASTPreprocessorIncludeStatement statement) {
	if (statement.toString().contains(STDLIB_H_)) {
	    isSTDLIB_H_included = true;
	}
	return super.visit(statement);
    }

    @Override
    protected int visit(IASTFunctionCallExpression expression) {
	IASTIdExpression functionNameExp = (IASTIdExpression) expression.getFunctionNameExpression();

	System.out.println("function name:" + functionNameExp.getName().toString());
	if (isSTDLIB_H_included == true && violationFunctions.contains(functionNameExp.getName().toString())) {
	    isViolated = true;
	    
//	    The library functions abort, exit, getenv and system from library <stdlib.h> shall not be used.
	    String message = MessageFactory.getInstance().getMessage(5126);
	    violationMsgs.add(
		    new ViolationMessage(this, message + "-- " + functionNameExp.getName().toString(), expression));
	}

	return super.visit(expression);
    }
}
