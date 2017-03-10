package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 4.2: (Required) Trigraphs shall not be used.
 * 
 * Trigraphs are denoted by a sequence of 2 question marks followed by a
 * specified third character (e.g. ??- represents a '~' (tilde) character and
 * ??) represents a ']'). They can cause accidental confusion with other uses of
 * two question marks. For example the string
 * 
 * "(Date should be in the form ??-??-??)" - would not behave as expected,
 * actually being interpreted by the compiler as "(Date should be in the form
 * ~~]"
 * 
 * //
 * ----------------------------------------------------------------------------
 * // | trigraph | replacement | trigraph | replacement | trigraph | replacement
 * | //
 * ----------------------------------------------------------------------------
 * // | ??= | # | ??( | [ | ??< | { | // | ??/ | \ | ??) | ] | ??> | } | // |
 * ??’ | ˆ | ??! | | | ??- | ˜ | //
 * ----------------------------------------------------------------------------
 * 
 * 
 * Issue: Visitor를 통해서 전달 받는 것이 이미 replace된 토큰이 전달 되기 때문에, 우리는 ??x가 들어오는지 알수가 없음
 * 꺼구로 우리가 replacement가 들어오면 ??x이것이 들어온것으로 간주하고, 이 룰을 violation되게 체크함.
 * [STATUS: DONE]
 * @author stkim
 *
 */
public class Rule04_2_Req extends AbstractMisraCRule {

	public Rule04_2_Req(IASTTranslationUnit ast) {
		super("Rule04_2_Req", false, ast);
		shouldVisitExpressions = true;
	}

	@Override
	protected int visit(IASTLiteralExpression expression) {
		// Original
		// Trigraphs are: ??= ??( ??/ ??) ??' ??< ??! ??> ??-
		// String trigraphPrefix = "??";
		// String trigraphPostfix = "=()/'<!>-";

//		String trigraphPostfix = "[]#\\ˆ|{}˜";
		String trigraphPostfix = "[]#ˆ|{}˜";
		char[] trigraphClueChar = trigraphPostfix.toCharArray();

		String literalExpression = expression.toString();

		for (char c : trigraphClueChar) {
			if (literalExpression.indexOf(c) != -1) // char contains trigraph
													// postfix.
			{
				String msg = MessageFactory.getInstance().getMessage(3601);
				violationMsgs.add(new ViolationMessage(this,
						getRuleID() + ":" + msg + ":" + literalExpression + "(" + expression.hashCode() + ")",
						expression));

				isViolated = true;
				break;
			}
		}

		return super.visit(expression);
	}

}
