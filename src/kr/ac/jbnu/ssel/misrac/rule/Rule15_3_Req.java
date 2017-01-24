package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 15.3: (Required) The final clause of a switch statement
 * shall be the default clause.
 * 
 * The requirement for a final default clause is defensive programming. This
 * clause shall either take appropriate action or contain a suitable comment as
 * to why no action is taken.
 * 
 * 
 * TODO
 * 
 * @author kang
 *
 */

public class Rule15_3_Req extends AbstractMisraCRule {

	public Rule15_3_Req(IASTTranslationUnit ast) {
		super("Rule15_3_Req", false, ast);

		shouldVisitStatements = true;
		shouldVisitExpressions = true;
	}

	@Override
	protected int visit(IASTSwitchStatement switchStatement) {
		ArrayList<IASTNode> list = new ArrayList<IASTNode>();
		int count = 0;

		for (IASTNode switchNode : switchStatement.getChildren()) {
			if(switchNode instanceof IASTCompoundStatement){
				for(IASTNode childe : switchNode.getChildren()){
					list.add(childe);
					if(childe instanceof IASTDefaultStatement){
						count++;
					}
				}
			}
		}
		
		if (count == 0) {
			String message1 = MessageFactory.getInstance().getMessage(2002);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1, switchStatement));
			isViolated = true;
		}
		else{
			if (!(list.get(list.size() - 1) instanceof IASTDefaultStatement)) {
				String message2 = MessageFactory.getInstance().getMessage(2009);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2, list.get(list.size() - 1)));
				isViolated = true;
			}
		}
		
		return super.visit(switchStatement);
	}

}
