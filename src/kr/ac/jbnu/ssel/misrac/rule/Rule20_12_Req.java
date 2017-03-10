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
 * MISRA-C:2004 Rule 20.12: (Required)
 * 
 * The time handling functions of library <time.h> shall not be used.
 *
 * Includes time, strftime. This library is associated with clock times.
 * 
 * Various aspects are implementation dependent or unspecified, such as the
 * formats of times. If any of the facilities of time.h are used then the exact
 * implementation for the compiler being used must be determined, and a
 * deviation raised.
 * 
 * This includes file and I/O functions fgetpos, fopen, ftell, gets, perror,
 * remove, rename, and ungetc.
 * 
 * Streams and file I/O have a large number of unspecified, undefined and
 * implementation-defined behaviours associated with them. It is assumed within
 * this standard that they will not normally be needed in production code in
 * embedded systems.
 *
 * [STATUS: DONE]
 * 
 * @author stkim
 *
 */

public class Rule20_12_Req extends AbstractMisraCRule
{

	private final static String _TIME_H = "time.h";
	private static final String[] VIOLATION_FUNCS =
		{"clock", "time", "difftime", "mktime", "asctime", "ctime", "gmtime", "localtime", "strftime"};

	private boolean isTimeHInclude = false;

	private HashSet<String> violationFunctions = new HashSet<String>();

	public Rule20_12_Req(IASTTranslationUnit ast)
	{
		super("Rule20_12_Req", false, ast);
		shouldVisitPreprocessor = true;
		shouldVisitExpressions = true;
		violationFunctions.addAll(Arrays.asList(VIOLATION_FUNCS));
	}

	@Override
	protected int visit(IASTPreprocessorIncludeStatement includeStatement)
	{
		if (includeStatement.toString().contains(_TIME_H))
		{
			isTimeHInclude = true;
			
			// "The time handling functions of library <time.h> shall not be used."
			String message1 = MessageFactory.getInstance().getMessage(5127);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "-"+ _TIME_H, includeStatement));

			isViolated = true;
		}
		
		return super.visit(includeStatement);
	}

	protected int visit(IASTFunctionCallExpression expression)
	{
		if( !isTimeHInclude) return super.visit(expression);
		
		IASTIdExpression functionNameExp = (IASTIdExpression) expression.getFunctionNameExpression();

		System.out.println("function name:" + functionNameExp.getName().toString());
		if (violationFunctions.contains(functionNameExp.getName().toString()))
		{
			isViolated = true;

			// "The time handling functions of library <time.h> shall not be used."
			String message1 = MessageFactory.getInstance().getMessage(5127);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "-" + functionNameExp.getName().toString(), expression));
		}

		return super.visit(expression);
	}
}
