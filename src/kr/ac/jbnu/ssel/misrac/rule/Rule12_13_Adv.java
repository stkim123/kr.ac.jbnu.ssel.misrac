package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 12.13: (Advisory)
 * 
 * The increment (++) and decrement (--) operators should not be mixed with
 * other operators in an expression.
 * 
 * It is the intention of the rule that when the increment or decrement operator
 * is used, it should be the only side effect in the statement. The use of
 * increment and decrement operators in combination with other arithmetic
 * operators is not recommended because:
 * 
 * It can significantly impair the readability of the code.
 * 
 * It introduces additional side effects into a statement with the potential for
 * undefined behaviour.
 * 
 * It is safer to use these operations in isolation from any other arithmetic
 * operators. For example a statement such as the following is not compliant:
 * 
 * u8a = ++u8b + u8c--; /* Not compliant The following sequence is clearer and
 * therefore safer: ++u8b; u8a = u8b + u8c; u8c--;
 * 
 * [STATUS: DONE]
 * 
 * @author Suntae Kim
 *
 */
public class Rule12_13_Adv extends AbstractMisraCRule {

    private static final String[] SIDE_EFFECT_OPS = { "++", "--" };

    public Rule12_13_Adv(IASTTranslationUnit ast) {
	super("Rule12_13_Adv", false, ast);
	shouldVisitExpressions = true;
    }

    @Override
    protected int visit(IASTBinaryExpression expression) {

	IASTExpression op1 = expression.getOperand1();
	IASTExpression op2 = expression.getOperand2();

	boolean violatedThisRuleSession = false;

	for (String seff : SIDE_EFFECT_OPS) {
	    if ((op1.getRawSignature().indexOf(seff) != -1)&&(op2.getRawSignature().indexOf(seff) != -1)) {
		violatedThisRuleSession = true;
	    }
	}

	// Using the value resulting from a ++ or -- operation.
	if (violatedThisRuleSession) {
	    isViolated = true;
	    String msg = MessageFactory.getInstance().getMessage(3440);
	    violationMsgs.add(new ViolationMessage(this,
		    getRuleID() + ":" + msg + "(" + expression.hashCode()+ ")", expression));
	}
	return super.visit(expression);
    }
}