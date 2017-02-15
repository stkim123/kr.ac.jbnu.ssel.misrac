package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * An unconditional break statement shall terminate every non-empty switch
 * clause.
 * 
 * The last statement in every switch clause shall be a break statement, or if
 * the switch clause is a compound statement, then the last statement in the
 * compound statement shall be a break statement.
 * 
 * 
 * DONE!!
 * 
 * @author sangjin
 *
 */
public class Rule15_2_Req extends AbstractMisraCRule {

	private static ArrayList<IASTNode> nodeList = new ArrayList<IASTNode>();
	private static ArrayList<String> indexNum = new ArrayList<String>();

	public Rule15_2_Req(IASTTranslationUnit ast) {
		super("Rule15_2_Req", false, ast);
		shouldVisitStatements = true;
		nodeList.clear();
	}

	@Override
	protected int visit(IASTSwitchStatement statement) {

		for (IASTNode comp : statement.getChildren()) {
			if (comp instanceof IASTCompoundStatement) {
				for (IASTNode target : comp.getChildren()) {
					nodeList.add(target);
					if ((target instanceof IASTCaseStatement) || (target instanceof IASTDefaultStatement)) {
						indexNum.add((nodeList.size() - 1) + "");
					}
				}
			}
		}

		indexNum.add(nodeList.size()+"");

		for (int i = 0; i < indexNum.size()+1; i++) {
			ArrayList<String> checkList = new ArrayList<String>();
			for (int k = Integer.parseInt(indexNum.get(i)); k < Integer.parseInt(indexNum.get(i + 1)); k++) {
				checkList.add(nodeList.get(k).getRawSignature());
			}
			
			
			if ((!checkList.contains("break;"))&&(checkList.size()>1)) {
				// The preceding 'switch' clause is not empty and does not end
				// with a 'jump' statement. Execution will fall through.
				String msg1 = MessageFactory.getInstance().getMessage(2003);
				violationMsgs.add(new ViolationMessage(this,
						getRuleID() + ":" + msg1+ nodeList.get(Integer.parseInt(indexNum.get(i))).getRawSignature(),
						nodeList.get(Integer.parseInt(indexNum.get(i)))));
				//Final 'switch' clause does not end with an explicit 'jump' statement.
				String msg2 = MessageFactory.getInstance().getMessage(2020);
				violationMsgs.add(new ViolationMessage(this,
						getRuleID() + ":" + msg2 + nodeList.get(Integer.parseInt(indexNum.get(i))).getRawSignature(),
						nodeList.get(Integer.parseInt(indexNum.get(i)))));
				isViolated = true;
			}
		}

		return super.visit(statement);
	}

}