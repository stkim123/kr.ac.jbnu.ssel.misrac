package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorUndefStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * #undef shall not be used.
 * 
 * #undef should not normally be needed. Its use can lead to confusion with
 * respect to the existence or meaning of a macro when it is used in the code.
 * Example Code:
 *
 *
 * #include "misra.h" #include "m2cmex.h"
 *
 * #define L 0 #undef L
 * 
 * extern S16 test_1906( void ) { return 1; }
 *
 *
 * 
 * 
 * 
 * @author sangjin
 *
 */
public class Rule19_6_Req extends AbstractMisraCRule {

	public Rule19_6_Req(IASTTranslationUnit ast) {
		super("Rule19_6_Req", false, ast);
		shouldVisitStatements = true;
	}

	@Override
	protected int visit(IASTPreprocessorIncludeStatement includeStatement) {
		
		System.out.println("asdfaef :: :: "+includeStatement.getParent());

		for (IASTNode node : includeStatement.getParent().getChildren()) {
			System.out.println("ch is :: "+node);
			if (node instanceof IASTPreprocessorUndefStatement) {
				// Using '#undef'.
				String message1 = MessageFactory.getInstance().getMessage(841);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + node, node));
				isViolated = true;
			}
		}

		return super.visit(includeStatement);
	}

}