package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTConditionalExpression;
import org.eclipse.cdt.core.dom.ast.IASTContinueStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * The continue statement shall not be used.
 * 
 * @author stkim
 *
 */
public class Rule14_5_Req extends AbstractMisraCRule {

	public Rule14_5_Req(IASTTranslationUnit ast) {
		super("Rule14_5_Req", false, ast);
		shouldVisitStatements = true;
	}

	
	@Override
	protected int visit(IASTContinueStatement statment) {
		
		String msg = MessageFactory.getInstance().getMessage(0770);
		violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msg, statment));
		isViolated = true;
		
		return super.visit(statment);
	}

}
