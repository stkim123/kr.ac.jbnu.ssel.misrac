package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * A switch label shall only be used when the most closely-enclosing compound
 * statement is the body of a switch statement.
 * 
 * The scope of a case or default label shall be the compound statement, which
 * is the body of a switch statement. All case clauses and the default clause
 * shall be at the same scope.
 * 
 * 
 * DONE!! 
 * 
 * @author sangjin
 *
 */
public class Rule15_1_Req extends AbstractMisraCRule {

	public Rule15_1_Req(IASTTranslationUnit ast) {
		super("Rule15_1_Req", false, ast);
		shouldVisitStatements = true;

	}

	@Override
	protected int visit(IASTCaseStatement statement) {

		IASTNode grandNode = statement.getParent().getParent();

		if (!(grandNode instanceof IASTSwitchStatement)) {
			// 'Switch' label is located within a nested code block.
			String msg1 = MessageFactory.getInstance().getMessage(2019);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + statement.getRawSignature(), statement));
			isViolated = true;
		}
		return super.visit(statement);
	}

}