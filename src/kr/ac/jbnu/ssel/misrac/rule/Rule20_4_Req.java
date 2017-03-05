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
 * MISRA-C:2004 Rule 20.04: (Required) Dynamic heap memory allocation shall not be used.
 * 
 * This precludes the use of the functions calloc, malloc, realloc and free.
 * 
 * There is a whole range of unspecified, undefined and implementation-defined behaviour associated with dynamic memory allocation, 
 * as well as a number of other potential pitfalls. Dynamic heap memory allocation may lead to memory leaks, data inconsistency,
 * memory exhaustion, non-deterministic behaviour.
 * 
 * Note that some implementations may use dynamic heap memory allocation to implement other functions
 *(for example functions in the library string.h). 
 *If this is the case then these functions shall also be avoided.
 *
 * 
 * [STATUS: DONE]
 * 
 * @author Seunghyeon Kang
 */

public class Rule20_4_Req extends AbstractMisraCRule
{

	private static final String[] VIOLATION_FUNCS = {"calloc","malloc", "realloc", "free" };

	private HashSet<String> violationFunctions = new HashSet<String>();

	public Rule20_4_Req(IASTTranslationUnit ast)
	{
		super("Rule20_4_Req", false, ast);
		shouldVisitExpressions = true;
		violationFunctions.addAll(Arrays.asList(VIOLATION_FUNCS));
	}


	protected int visit(IASTFunctionCallExpression expression)
	{
		IASTIdExpression functionNameExp = (IASTIdExpression) expression.getFunctionNameExpression();

		if (violationFunctions.contains(functionNameExp.getName().toString()))
		{
			isViolated = true;

			String message1 = MessageFactory.getInstance().getMessage(5118);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "-" + functionNameExp.getName().toString(), expression));
		}

		return super.visit(expression);
	}
}