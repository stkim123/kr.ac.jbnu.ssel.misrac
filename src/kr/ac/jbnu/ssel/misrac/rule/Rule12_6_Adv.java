package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004  Rule  12.6:  (Advisory)	[next]		
 * 
 * The operands of logical operators (&&, || and !) should be effectively Boolean. 
 * Expressions that are effectively Boolean should not be used as operands to operators other than 
 * (&&, ||, !, =, ==, != and ?:).
 * 
 * The logical operators &&, || and ! can be easily confused with the bitwise 
 * operators &, | and ~. See 'Boolean expressions' in the glossary.
 * 
 * [Done!]
 * @author stkim
 */

public class Rule12_6_Adv extends AbstractMisraCRule
{

	public Rule12_6_Adv(IASTTranslationUnit ast)
	{
		super("Rule12_6_Adv", false, ast);
		shouldVisitExpressions = true;
	}

	@Override
	protected int visit(IASTBinaryExpression expression)
	{
		int operator = expression.getOperator();
		if (operator == IASTBinaryExpression.op_binaryAnd 
				|| operator == IASTBinaryExpression.op_binaryOr
				|| operator == IASTBinaryExpression.op_binaryAndAssign
				|| operator == IASTBinaryExpression.op_binaryOrAssign
				|| operator == IASTBinaryExpression.op_binaryXor
				|| operator == IASTBinaryExpression.op_binaryXorAssign)
		{
			isViolated = true;
			String msg = MessageFactory.getInstance().getMessage(3322);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msg + "(BinaryOperator-"+operator+":"+expression.hashCode()+")", expression));
		}

		return super.visit(expression);
	}
	
	@Override
	protected int visit(IASTUnaryExpression expression)
	{
		int operator = expression.getOperator();
		if (operator == IASTUnaryExpression.op_not)
		{
			isViolated = true;
			String msg = MessageFactory.getInstance().getMessage(3322);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msg + "(UnaryOpreator"+operator+":"+expression.hashCode()+")", expression));
		}

		return super.visit(expression);
	}
}
