package kr.ac.jbnu.ssel.misrac.rule;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 20.7: (Required)
 *
 * The setjmp macro and the longjmp function shall not be used.
 * 
 * setjmp and longjmp allow the normal function call mechanisms to be bypassed,
 * and shall not be used.
 * 
 * [STATUS: DONE]
 * 
 * @author stkim
 *
 */

public class Rule20_7_Req extends AbstractMisraCRule
{

	private final static String _SETJMP_H = "setjmp.h";
	private static final String[] VIOLATION_FUNCS =
	{ "setjmp","_setjmp", "longjmp" };

	private boolean isSETJMP_H_ = false;

	private HashSet<String> violationFunctions = new HashSet<String>();

	public Rule20_7_Req(IASTTranslationUnit ast)
	{
		super("Rule20_7_Req", false, ast);
		shouldVisitPreprocessor = true;
		shouldVisitExpressions = true;
		violationFunctions.addAll(Arrays.asList(VIOLATION_FUNCS));
	}

	@Override
	protected int visit(IASTPreprocessorIncludeStatement includeStatement)
	{
		if (includeStatement.toString().contains(_SETJMP_H))
		{
			isSETJMP_H_ = true;
		}

		return super.visit(includeStatement);
	}

	protected int visit(IASTFunctionCallExpression expression)
	{
		if (!isSETJMP_H_)
			return super.visit(expression);

		IASTIdExpression functionNameExp = (IASTIdExpression) expression.getFunctionNameExpression();

		System.out.println("function name:" + functionNameExp.getName().toString());
		if (violationFunctions.contains(functionNameExp.getName().toString()))
		{
			isViolated = true;

			// "The setjmp macro and the longjmp function shall not be used."
			String message1 = MessageFactory.getInstance().getMessage(5127);
			violationMsgs.add(new ViolationMessage(this,
					getRuleID() + ":" + message1 + "-" + functionNameExp.getName().toString(), expression));
		}

		return super.visit(expression);
	}
}
