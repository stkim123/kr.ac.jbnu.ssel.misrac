package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * No identifier name should be reused.
 *
 * Regardless of scope, no identifier should be re-used across any files in the
 * system. This rule incorporates the provisions of , , , , and .
 * 
 * [STATUS: DONE]
 * 
 * @author sangjin
 *
 */
public class Rule05_7_Adv extends AbstractMisraCRule {
	private static HashSet<String> declarations = new HashSet<String>();

	public Rule05_7_Adv(IASTTranslationUnit ast) {
		super("Rule05_7_Adv", false, ast);
		shouldVisitDeclarations = true;
		declarations.clear();
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {

		String id = simpleDeclaration.getDeclarators()[0].getName().toString();
		if (declarations.contains(id)) {
			String message2 = MessageFactory.getInstance().getMessage(10001);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + simpleDeclaration.getRawSignature(), simpleDeclaration));
			isViolated = true;
		} else {
			declarations.add(id);
		}

		return super.visit(simpleDeclaration);
	}
}