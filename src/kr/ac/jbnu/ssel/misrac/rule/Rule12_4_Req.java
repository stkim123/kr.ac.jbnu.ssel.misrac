package kr.ac.jbnu.ssel.misrac.rule;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
/**
 * MISRA-C:2004  Rule  12.4:  (Required)
 * 
 * The right hand operand of a logical && or || operator shall not contain side effects.
 * 
 * There are some situations in C code where certain parts of expressions may not be evaluated. 
 * If these sub-expressions contain side effects then those side effects may or may not occur, 
 * depending on the values of other sub-expressions.
 * 
 * The operators which can lead to this problem are &&, || and ?:. In the case of the first two 
 * (logical operators) the evaluation of the right-hand operand is conditional on the value of 
 * the left-hand operand. In the case of the ?: operator, either the second or third operands are 
 * evaluated but not both. 
 * 
 * The conditional evaluation of the right hand operand of one of the logical operators can 
 * easily cause problems if the programmer relies on a side effect occurring. 
 * 
 * The ?: operator is specifically provided to choose between two sub-expressions, 
 * and is therefore less likely to lead to mistakes.
 * 
 * [DONE!]
 * @author stkim
 */
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

public class Rule12_4_Req extends AbstractMisraCRule
{
	private static final String[] SIDE_EFFECT_OPS = {"++"};
	
	public Rule12_4_Req(IASTTranslationUnit ast)
	{
		super("Rule12_4_Req", false, ast);
		shouldVisitExpressions = true;
	}

	@Override
	protected int visit(IASTBinaryExpression expression)
	{
		int operator = expression.getOperator();

		if (operator == IASTBinaryExpression.op_logicalAnd || operator == IASTBinaryExpression.op_logicalOr)
		{
			IASTExpression op2 = expression.getOperand2();
			if( op2 instanceof IASTUnaryExpression)
			{
				IASTUnaryExpression rightHS = (IASTUnaryExpression)op2;
				String rightHSString = rightHS.getRawSignature();
				
				for (String sideEffectOp: SIDE_EFFECT_OPS)
				{
					if( rightHSString.indexOf(sideEffectOp) != -1)	// check if right hand side contains 'side effect'.
					{
						isViolated = true;
						// "Right hand operand of '&&' or '||' is an expression with possible side effects.";
						String msg = MessageFactory.getInstance().getMessage(3415);
						violationMsgs.add(new ViolationMessage(this, getRuleID() + ":"+ msg + "("+expression.hashCode()+")", expression));
					}
				}
			}
		}
		return super.visit(expression);
	}

}
