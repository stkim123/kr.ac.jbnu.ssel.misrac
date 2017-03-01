package kr.ac.jbnu.ssel.misrac.rule;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 13.05: (Required) The three expressions of a for statement
 * shall be concerned only with loop control.
 * 
 * The three expressions of a for statement shall be used only for these
 * purposes: First expression - Initialising the loop counter
 * 
 * Second expression - Shall include testing the loop counter, and optionally
 * other loop control variables
 * 
 * Third expression - Increment or decrement of the loop counter
 * 
 * The following options are allowed:
 * 
 * All three expressions shall be present; The second and third expressions
 * shall be present with prior initialisation of the loop counter; All three
 * expressions shall be empty for a deliberate infinite loop.
 * 
 * TODO - The variable incremented in the third expression of this 'for'
 * statement is not the variable identified as the 'loop control variable' (%s).
 * 룰이해부족으로 두번째 메세지 violation잡는부분 수정필요
 * 
 *  
 * @author Seunghyeon Kang
 */

public class Rule13_5_Req extends AbstractMisraCRule {

	public Rule13_5_Req(IASTTranslationUnit ast) {
		super("Rule13_5_Req", false, ast);

		shouldVisitStatements = true;
		shouldVisitExpressions = true;
	}

	public Rule13_5_Req(String ruleID, boolean visitNodes, IASTTranslationUnit ast) {
		super(ruleID, visitNodes, ast);
	}

	@Override
	protected int visit(IASTForStatement forStatement) {
		IASTNode[] forChild = forStatement.getChildren();

		for (IASTNode node : forChild[0].getChildren()) {
			if (node instanceof IASTUnaryExpression) {
				isViolated = true;

				String msg = MessageFactory.getInstance().getMessage(2462);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msg + ":" + forChild[0].getRawSignature(),
						forChild[0]));
			}
		}

		//Third Expression
		for (IASTNode node : forChild[1].getChildren()) {
			if (node instanceof IASTUnaryExpression) {
				isViolated = true;

				String msg = MessageFactory.getInstance().getMessage(2463);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msg + ":" + forChild[1].getRawSignature(),
						forChild[1]));
			}
		}

		return super.visit(forStatement);
	}

}