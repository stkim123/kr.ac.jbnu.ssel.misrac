package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

public class Rule05_3_Req extends AbstractMisraCRule {
	private static HashSet<String> declarations = new HashSet<String>();

	public Rule05_3_Req(IASTTranslationUnit ast) {
		super("Rule05_3_Req", false, ast);
		shouldVisitDeclarations = true;
		declarations.clear();
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		for (IASTNode node : simpleDeclaration.getDeclarators()) {
			String temp = node.getRawSignature();

			if (declarations.contains(temp)) {
				String message1 = MessageFactory.getInstance().getMessage(1506);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + temp, node));

				String message2 = MessageFactory.getInstance().getMessage(1507);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + temp, node));

				String message3 = MessageFactory.getInstance().getMessage(1508);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message3 + "--" + temp, node));

				String message4 = MessageFactory.getInstance().getMessage(3448);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message4 + "--" + temp, node));

				isViolated = true;
			} else {
				declarations.add(temp);
			}
		}
		return super.visit(simpleDeclaration);
	}
}
