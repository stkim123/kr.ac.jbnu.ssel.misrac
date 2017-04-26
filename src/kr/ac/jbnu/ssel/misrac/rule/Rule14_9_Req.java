package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTContinueStatement;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * An if (expression) construct shall be followed by a compound statement. The
 * else keyword shall be followed by either a compound statement, or another if
 * statement.
 * 
 * [STATUS: not statically checkable, partially support]
 * 
 * @author sangjin
 *
 */
public class Rule14_9_Req extends AbstractMisraCRule {

	public Rule14_9_Req(IASTTranslationUnit ast) {
		super("Rule14_9_Req", false, ast);
		shouldVisitStatements = true;
	}

	@Override
	protected int visit(IASTIfStatement ifStatement) {

		int count = 0;
		for (IASTNode compoundNode : ifStatement.getChildren()) {
			if (compoundNode instanceof IASTCompoundStatement) {
				count++;
			}
		}
		
		if (count == 0) {
			// Body of control statement is not enclosed within braces.
			String message1 = MessageFactory.getInstance().getMessage(2212);
			violationMsgs
					.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + ifStatement, ifStatement));
			// Body of control statement is on the same line and is not enclosed
			// within braces.
			String message2 = MessageFactory.getInstance().getMessage(2214);
			violationMsgs
					.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + ifStatement, ifStatement));
			// Braces are needed to clarify the structure of this
			// 'if'-'if'-'else' statement.
			String message3 = MessageFactory.getInstance().getMessage(3402);
			violationMsgs
					.add(new ViolationMessage(this, getRuleID() + ":" + message3 + "--" + ifStatement, ifStatement));
			isViolated = true;
		}

		if (ifStatement.getChildren()[2] instanceof IASTIfStatement) {
			IASTNode elseifStatement = ifStatement.getChildren()[2];

			String[] parseString = elseifStatement.getRawSignature().split("else");

			if (!parseString[parseString.length - 1].startsWith("{")) {
				// Body of control statement is not enclosed within braces.
				String message1 = MessageFactory.getInstance().getMessage(2212);
				violationMsgs.add(
						new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + ifStatement, ifStatement));
				// Body of control statement is on the same line and is not
				// enclosed within braces.
				String message2 = MessageFactory.getInstance().getMessage(2214);
				violationMsgs.add(
						new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + ifStatement, ifStatement));
				// Braces are needed to clarify the structure of this
				// 'if'-'if'-'else' statement.
				String message3 = MessageFactory.getInstance().getMessage(3402);
				violationMsgs.add(
						new ViolationMessage(this, getRuleID() + ":" + message3 + "--" + ifStatement, ifStatement));
				isViolated = true;
			}
		}

		return super.visit(ifStatement);
	}

}
