package kr.ac.jbnu.ssel.misrac.rule;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004  Rule  12.5:  (Required)	
 * 
 * 'Primary expressions' are defined in ISO/IEC 9899:1990 [2], section 6.3.1. Essentially they 
 * are either a single identifier, or a constant, or a parenthesised expression. 
 * 
 * The effect of this rule is to require that if an operand is other than a single identifier 
 * or constant then it must be parenthesised. Parentheses are important in this situation both 
 * for readability of code and for ensuring that the behaviour is as the programmer intended. 
 * Where an expression consists of either a sequence of only logical && or a sequence of only 
 * logical ||, extra parentheses are not required.

 * 
 * [DONE!]
 * @author stkim
 */
public class Rule12_5_Req extends AbstractMisraCRule
{
	private static final String[] SIDE_EFFECT_OPS = {"++"};
	
	public Rule12_5_Req(IASTTranslationUnit ast)
	{
		super("Rule12_5_Req", false, ast);
		shouldVisitExpressions = true;
	}

	@Override
	protected int visit(IASTBinaryExpression expression)
	{
		int operator = expression.getOperator();

		if (operator == IASTBinaryExpression.op_logicalAnd || operator == IASTBinaryExpression.op_logicalOr)
		{
			IASTExpression op1 = expression.getOperand1();
			IASTExpression op2 = expression.getOperand2();
			
			boolean violatedThisRuleSession = false;
			
			
			String opMsg = "";
			
			if( op1 instanceof IASTUnaryExpression || op1 instanceof IASTBinaryExpression || op1 instanceof IASTFunctionCallExpression)
			{
				if( !checkPrimary(op1))
				{
					violatedThisRuleSession = true;
					opMsg = opMsg + op1.getRawSignature() +",";
				}
			}
			
			if( op2 instanceof IASTUnaryExpression|| op2 instanceof IASTBinaryExpression || op2 instanceof IASTFunctionCallExpression)
			{
				if( !checkPrimary(op2))
				{
					violatedThisRuleSession = true;
					opMsg = opMsg + op2.getRawSignature() +",";
				}
			}

			//Extra parentheses recommended. A unary operation is the operand of a logical && or ||.
			if( violatedThisRuleSession)
			{
				isViolated = true;
				String msg = MessageFactory.getInstance().getMessage(3399);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":"+ msg + "("+opMsg.substring(0, opMsg.length() -1)+")", expression));
			}
		}

		return super.visit(expression);
	}

	// if unary expression has pharanthesis, it is primary. 
	private boolean checkPrimary(IASTExpression op)
	{
		String rawSignature = op.getRawSignature();
		
		if( rawSignature.startsWith("(") && rawSignature.endsWith(")"))
		{
			return true;
		}
		return false;
	}
}
