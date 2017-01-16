package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Bit fields of signed type shall be at least 2 bits long.
 *
 * A signed bit field of 1 bit length is not useful.
 * 
 * DONE!!
 * @author sangjin
 *
 */
public class Rule06_5_Req extends AbstractMisraCRule {

	public Rule06_5_Req(IASTTranslationUnit ast) {
		super("Rule06_5_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitDeclarators = true;
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		String declSpecifier = simpleDeclaration.getDeclSpecifier().getRawSignature().split(" ")[0];
		if(declSpecifier.equals("signed")){
			int fieldDecl = Integer.parseInt(simpleDeclaration.getDeclarators()[0].getChildren()[1].getRawSignature());
			if(fieldDecl<2){
//				Unnamed zero-width bit-field declared with a signed type.
				String message1 = MessageFactory.getInstance().getMessage(3659);
				violationMsgs.add(
						new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + simpleDeclaration, simpleDeclaration));
//				Named bit-field consisting of a single bit declared with a signed type.
				String message2 = MessageFactory.getInstance().getMessage(3660);
				violationMsgs.add(
						new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + simpleDeclaration, simpleDeclaration));
//				Unnamed bit-field consisting of a single bit declared with a signed type.
				String message3 = MessageFactory.getInstance().getMessage(3665);
				violationMsgs.add(
						new ViolationMessage(this, getRuleID() + ":" + message3 + "--" + simpleDeclaration, simpleDeclaration));
				isViolated = true;
			}
		}
		return super.visit(simpleDeclaration);
	}

}
