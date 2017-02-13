package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;

/**
 * An unconditional break statement shall terminate every non-empty switch
 * clause.
 * 
 * The last statement in every switch clause shall be a break statement, or if
 * the switch clause is a compound statement, then the last statement in the
 * compound statement shall be a break statement.
 * 
 * 
 * 
 * 
 * @author sangjin
 *
 */
public class Rule15_2_Req extends AbstractMisraCRule {

	private static ArrayList<IASTNode> nodeList = new ArrayList<IASTNode>();

	public Rule15_2_Req(IASTTranslationUnit ast) {
		super("Rule15_2_Req", false, ast);
		shouldVisitStatements = true;

	}

	@Override
	protected int visit(IASTSwitchStatement statement) {

		for (IASTNode comp : statement.getChildren()) {
			if (comp instanceof IASTCompoundStatement) {
				for (IASTNode target : comp.getChildren()) {
					nodeList.add(target);
				}
			}
		}

		for (int i = 0; i < nodeList.size(); i++) {
		}

		// case - 다른것 - case(default)는 break가 필요

		return super.visit(statement);
	}

}