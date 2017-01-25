package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;
import kr.ac.jbnu.ssel.misrac.rulesupport.VisitorUtil;

/**
 * MISRA-C:2004  Rule  12.3:  (Required)
 * 
 * The sizeof operator shall not be used on expressions that contain side effects.
 * 
 * A possible programming error in C is to apply the sizeof operator to an expression 
 * and expect the expression to be evaluated. However the expression is not evaluated: 
 * sizeof only acts on the type of the expression. 
 * 
 * To avoid this error, sizeof shall not be used on expressions that contain side effects, 
 * as the side effects will not occur. sizeof() shall only be applied to an operand which 
 * is a type or an object. This may include volatile objects. For example:
 * 
 * TODO
 * @author stkim
 */

public class Rule12_3_Req extends AbstractMisraCRule
{
	private static final String[] SIDE_EFFECT_OPS = {"++"};
	
	public Rule12_3_Req(IASTTranslationUnit ast)
	{
		super("Rule12_3_Req", false, ast);
		shouldVisitExpressions = true;
	}

	@Override
	protected int visit(IASTUnaryExpression expression)
	{
		if( expression.getOperator() == IASTUnaryExpression.op_sizeof)
		{
			if( VisitorUtil.containsASTNodein(expression, IASTBinaryExpression.class))
			{
				isViolated = true;
				String msg = MessageFactory.getInstance().getMessage(3307);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":"+  msg + "("+expression.hashCode()+")", expression));
			}
		}
		return super.visit(expression);
	}
}
