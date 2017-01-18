package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorFunctionStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * There shall be at most one occurrence of the # or ## preprocessor operators
 * in a single macro definition.
 * 
 * There is an issue of unspecified order of evaluation associated with the #
 * and ## preprocessor operators. To avoid this problem only one occurrence of
 * either operator shall be used in any single macro definition (i.e. one #, or
 * one ## or neither).
 * 
 * 
 * DONE!!
 * 
 * @author sangjin
 *
 */
public class Rule19_12_Req extends AbstractMisraCRule {

	public Rule19_12_Req(IASTTranslationUnit ast) {
		super("Rule19_12_Req", false, ast);
		shouldVisitTranslationUnit = true;
	}

	@Override
	public int visit(IASTTranslationUnit translationUnit) {

		int count = 0;

		for (IASTNode node : translationUnit.getMacroDefinitions()) {
			String code = node.getRawSignature();

			for (int j = 0; j < code.length(); j++) {
				char data = code.charAt(j);

				if (data == '#') {
					count++;
				}
			}

			if (count > 2) {

				// Using # and ## operators in the same macro definition.
				String message1 = MessageFactory.getInstance().getMessage(842);
				violationMsgs.add(
						new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + node, node));
				// Using multiple ## operators in the same macro definition.
				String message2 = MessageFactory.getInstance().getMessage(842);
				violationMsgs.add(
						new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + node, node));
				// Using multiple # operators in the same macro definition.
				String message3 = MessageFactory.getInstance().getMessage(842);
				violationMsgs.add(
						new ViolationMessage(this, getRuleID() + ":" + message3 + "--" + node, node));

				isViolated = true;
			}

		}

		return super.visit(translationUnit);

	}
}
