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
public class Rule19_13_Adv extends AbstractMisraCRule {

	public Rule19_13_Adv(IASTTranslationUnit ast) {
		super("Rule19_13_Adv", false, ast);
		shouldVisitTranslationUnit = true;
	}

	@Override
	public int visit(IASTTranslationUnit translationUnit) {

		for (IASTNode node : translationUnit.getMacroDefinitions()) {

			int count = 0;

			String[] parseString = node.getRawSignature().split(" ");

			String endStringOne = parseString[parseString.length - 1] + parseString[parseString.length - 2];

			for (int i = 0; i < endStringOne.length(); i++) {
				char data = endStringOne.charAt(i);

				if (data == '#') {
					count++;
				}
			}
			
			if (count > 0) {
				
				 //Using the stringify operator '#'.
				 String message1 = MessageFactory.getInstance().getMessage(341);
				 violationMsgs.add(
				 new ViolationMessage(this, getRuleID() + ":" + message1 + "--" +
				 node, node));
				 //Using the glue operator '##'.
				 String message2 = MessageFactory.getInstance().getMessage(342);
				 violationMsgs.add(
				 new ViolationMessage(this, getRuleID() + ":" + message2 + "--" +
				 node, node));
				
				 isViolated = true;
			
			}
		}

		return super.visit(translationUnit);

	}
}
