package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTBreakStatement;
import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * The MISRA C switch syntax shall be used.
 * 
 * The syntax for the switch in C is weak, allowing complex, unstructured
 * behaviour. The following text describes the syntax for switch statements as
 * defined by MISRA-C and is normative. This, and the associated rules, enforces
 * a simple and consistent structure on the switch statement. The following
 * syntax rules are additional to the C standard syntax rules. All syntax rules
 * not defined below are as defined in the C standard.
 * 
 * switch-statement:
 * 
 * switch ( expression ) { case-label-clause-list default-label-clause }
 * case-label-clause-list:
 * 
 * case-label case-clauseopt case-label-clause-list case-label case-clauseopt
 * case-label:
 * 
 * case constant-expression : case-clause:
 * 
 * statement-listopt break ; { declaration-listopt statement-listopt break ; }
 * default-label-clause:
 * 
 * default-label default-clause default-label:
 * 
 * default : default-clause:
 * 
 * case-clause and
 * 
 * statement:
 * 
 * 
 * compound_statement expression_statement selection_statement
 * iteration_statement
 * 
 * The following terms are also used within the text of the rules:
 * 
 * switch label Either a case label or default label. case clause The code
 * between any two switch labels. default clause The code between the default
 * label and the end of the switch statement. switch clause Either a case clause
 * or a default clause.
 * 
 * DONE!!
 * 
 * @author sangjin
 *
 */
public class Rule15_0_Req extends AbstractMisraCRule {

	public Rule15_0_Req(IASTTranslationUnit ast) {
		super("Rule15_0_Req", false, ast);
		shouldVisitStatements = true;
	}

	// switch안의 모든 요소는 case 혹은 default 구문 안에 있어야한다.
	// switch안의 default 구문은 마지막에 위치해야 한다.
	// case 구문은 마지막에 break를 갖는다.

	@Override
	protected int visit(IASTSwitchStatement statement) {

		boolean isContainDefaultStatement = false;
		boolean isContainCaseStatement = false;
		ArrayList<IASTNode> list = new ArrayList<IASTNode>();

		IASTNode compound = statement.getChildren()[1];

		for (IASTNode switchElement : compound.getChildren()) {
			if ((switchElement instanceof IASTCaseStatement) || (switchElement instanceof IASTBreakStatement)) {
				isContainCaseStatement = true;
				list.add(switchElement);
			} else if ((switchElement instanceof IASTDefaultStatement)) {
				isContainDefaultStatement = true;
				list.add(switchElement);
			} else {
				// 모든 요소는 case 혹은 default 구문 안에 있어야한다.
				if(!isContainCaseStatement){
					String message1 = MessageFactory.getInstance().getMessage(3234);
					violationMsgs.add(new ViolationMessage(this,
							getRuleID() + ":" + message1 + "--" + switchElement.getRawSignature(), switchElement));
					isViolated = true;
				}
			}

		}

		if (isContainDefaultStatement) {
			IASTNode lastElement = list.get(list.size() - 1);
			IASTNode beforeLastElement = list.get(list.size() - 2);
			// 디폴트는 마지막에 있어야한다.
			if (!(lastElement instanceof IASTDefaultStatement) && !(lastElement instanceof IASTBreakStatement)) {
				String message1 = MessageFactory.getInstance().getMessage(3234);
				violationMsgs.add(new ViolationMessage(this,
						getRuleID() + ":" + message1 + "--" + lastElement.getRawSignature(), lastElement));
				isViolated = true;
			} else if (!(lastElement instanceof IASTDefaultStatement) && (lastElement instanceof IASTBreakStatement)) {
				if (!(beforeLastElement instanceof IASTDefaultStatement)) {
					String message1 = MessageFactory.getInstance().getMessage(3234);
					violationMsgs.add(new ViolationMessage(this,
							getRuleID() + ":" + message1 + "--" + lastElement.getRawSignature(), lastElement));
					isViolated = true;
				}
			}
		}

		//case 구문은 break를 갖는다.
		for (int i = 0; i < list.size(); i++) {
			IASTNode node = list.get(i);
			if (node instanceof IASTCaseStatement) {
				if (!(list.get(i + 1) instanceof IASTBreakStatement)) {
					String message1 = MessageFactory.getInstance().getMessage(3234);
					violationMsgs.add(new ViolationMessage(this,
							getRuleID() + ":" + message1 + "--" + node.getRawSignature(), node));
					isViolated = true;
				}
			}
		}

		// switch 구문에 case나 break, default가 하나도 없으면 위반
		if (list.isEmpty()) {
			String message1 = MessageFactory.getInstance().getMessage(3234);
			violationMsgs.add(new ViolationMessage(this,
					getRuleID() + ":" + message1 + "--" + list.toString(), statement));
			isViolated = true;
		}

		return super.visit(statement);
	}

}