package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * #undef shall not be used.
 * 
 * #undef should not normally be needed. Its use can lead to confusion with
 * respect to the existence or meaning of a macro when it is used in the code.
 * Example Code:
 *
 *
 * #include "misra.h" #include "m2cmex.h"
 *
 * #define L 0 #undef L
 * 
 * extern S16 test_1906( void ) { return 1; }
 *
 *
 * 
 * DONE!!
 * 
 * @author sangjin
 *
 */
public class Rule19_6_Req extends AbstractMisraCRule {

	public Rule19_6_Req(IASTTranslationUnit ast) {
		super("Rule19_6_Req", false, ast);
		shouldVisitTranslationUnit = true;
	}

	@Override
	public int visit(IASTTranslationUnit translationUnit) {

		String[] parseString = translationUnit.getRawSignature().split("#");

		for (int i = 0; i < parseString.length; i++) {
			if (parseString[i].startsWith("undef")) {
				// Using '#undef'.
				String message1 = MessageFactory.getInstance().getMessage(841);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + parseString[i],
						translationUnit));
				isViolated = true;
			}
		}

		return super.visit(translationUnit);
	}

}