package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * All if ... else if constructs shall be terminated with an else clause.
 *
 * This rule applies whenever an if statement is followed by one or more else if
 * statements; the final else if shall be followed by an else statement. In the
 * case of a simple if statement then the else statement need not be included.
 * The requirement for a final else statement is defensive programming. The else
 * statement shall either take appropriate action or contain a suitable comment
 * as to why no action is taken. This is consistent with the requirement to have
 * a final default clause in a switch statement (15.3)
 * 
 * DONE!!
 * 
 * @author sangjin
 *
 */
public class Rule14_10_Req extends AbstractMisraCRule {

	public Rule14_10_Req(IASTTranslationUnit ast) {
		super("Rule14_10_Req", false, ast);
		shouldVisitStatements = true;
		shouldVisitComment = false;
	}

	@Override
	protected int visit(IASTIfStatement ifStatement) {

		if (ifStatement.getChildren()[2] instanceof IASTIfStatement) {
			IASTNode elseifStatement = ifStatement.getChildren()[2];

			String[] parseString = elseifStatement.getRawSignature().split("else");

			if (parseString[parseString.length - 1].startsWith("if")) {
				// No concluding 'else' exists in this 'if'-'else'-'if'
				// statement.
				String message1 = MessageFactory.getInstance().getMessage(2004);
				violationMsgs.add(
						new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + ifStatement, ifStatement));
				isViolated = true;
			}
		}
		return super.visit(ifStatement);
	}
}