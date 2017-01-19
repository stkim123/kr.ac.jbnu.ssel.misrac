package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Every switch statement shall have at least one case clause.
 * 
 * DONE!!
 * 
 * @author sangjin
 *
 */
public class Rule15_5_Req extends AbstractMisraCRule {

	private static ArrayList<IASTNode> list = new ArrayList<IASTNode>();

	public Rule15_5_Req(IASTTranslationUnit ast) {
		super("Rule15_5_Req", false, ast);
		shouldVisitStatements = true;
		list.clear();
	}

	@Override
	protected int visit(IASTSwitchStatement statement) {

		ArrayList<String> tempList = new ArrayList<String>();
		IASTCompoundStatement compoundStatement = null;

		for (IASTNode node : statement.getChildren()) {
			if (node instanceof IASTCompoundStatement) {
				compoundStatement = (IASTCompoundStatement) node;
			}
		}

		// add at list
		for (IASTNode node : compoundStatement.getChildren()) {
			list.add(node);
		}

		// check case Statement
		for (int i = 0; i < list.size(); i++) {
			IASTNode element = list.get(i);
			if (!(element instanceof IASTCaseStatement)) {

				for (int j = 0; j < i; j++) {
					if (list.get(j).getRawSignature().startsWith("case")) {
						tempList.add(list.get(j).getRawSignature().split(" ")[0]);
					}
				}

				if (!tempList.contains("case")) {
					// This 'switch' statement contains only a single path -
					// it is redundant.
					String message1 = MessageFactory.getInstance().getMessage(5123);
					violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1, element));
					isViolated = true;
				}
				tempList.clear();
			}

		}

		return super.visit(statement);
	}

}
