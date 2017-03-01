package kr.ac.jbnu.ssel.misrac.rule;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationLevel;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004  Rule  4.1:  (Required)	[next]		
 * Only those escape sequences that are defined in the ISO C standard shall be used.
 * 
 * Only "simple-escape-sequences" in ISO/IEC 9899:1990 [3-6] Section 6.1.3.4 and \0 are permitted escape sequences.
 * All "hexadecimal-escape-sequences" are prohibited.
 * The "octal-escape-sequences" other than \0 are also prohibited under Rule 7.1.
 * 
 * @author Suntae Kim
 */
public class Rule04_1_Req extends AbstractMisraCRule {

	private static final String ESCAPE_CHAR_PREFIX = "\\";
	
	private HashSet<String> allowedEscapeChars = new HashSet<String>();
	private String[] escapeCharTypes = {"\\a","\\b","\\f","\\n","\\r","\\t","\\v","\\x"};
	
	
	public Rule04_1_Req(IASTTranslationUnit ast) {
		super("Rule04_1_Req", false, ast);
		shouldVisitExpressions = true;
		allowedEscapeChars.addAll(Arrays.asList(escapeCharTypes));
	}

	@Override
	protected int visit(IASTLiteralExpression expression) {
		String stringLiteral = expression.getRawSignature();
		
		if(stringLiteral.indexOf(ESCAPE_CHAR_PREFIX) != -1 )
		{
			if( !containAllowedEscapeChars(stringLiteral))
			{
				String message = MessageFactory.getInstance().getMessage(0235);
				isViolated = true;
				violationMsgs.add(new ViolationMessage(this,
						getRuleID() + ":" + message + "--" + stringLiteral, expression));
			}
		}

		return super.visit(expression);
	}
	
	private boolean containAllowedEscapeChars(String stringLiteral)
	{
		boolean isViolated = false;
		for (String allowedEscapeChar : allowedEscapeChars) {
			
			if( stringLiteral.indexOf(allowedEscapeChar) != -1)
			{
				isViolated = true;
			}
		}
		return isViolated;
	}

}
