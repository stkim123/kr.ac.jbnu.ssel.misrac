package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.c.ICASTCompositeTypeSpecifier;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * All structure and union types shall be complete at the end of a translation
 * unit.
 * 
 * A complete declaration of the structure or union shall be included within any
 * translation unit that reads from or writes to that structure. A pointer to an
 * incomplete type is itself complete and is permitted, and therefore the use of
 * opaque pointers is permitted. See section 6.1.2.5 of ISO/IEC 9899:1990 [2]
 * for a full description of incomplete types.
 * 
 * 
 * [STATUS: TODO]
 * 
 * @author sangjin
 *
 */
public class Rule18_1_Req extends AbstractMisraCRule {

	public Rule18_1_Req(IASTTranslationUnit ast) {
		super("Rule18_1_Req", false, ast);
		shouldVisitDeclarations = true;
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {

		boolean isContainTranslation = false;

		for (IASTNode Decl : simpleDeclaration.getChildren()) {
			if (Decl instanceof ICASTCompositeTypeSpecifier) {
				isContainTranslation = true;
			}
		}

		if (!isContainTranslation && simpleDeclaration.getRawSignature().startsWith("struct")) {
			// [U] The value of an incomplete 'struct' may not be used.
			String message2 = MessageFactory.getInstance().getMessage(545);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + simpleDeclaration,
					simpleDeclaration));
			// [U] '%s' has incomplete type and no linkage - this is undefined.
			String message3 = MessageFactory.getInstance().getMessage(623);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message3 + "--" + simpleDeclaration,
					simpleDeclaration));
			// [U] There are no named members in this 'struct' or 'union'.
			String message4 = MessageFactory.getInstance().getMessage(636);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message4 + "--" + simpleDeclaration,
					simpleDeclaration));
			isViolated = true;
		}
		if (!isContainTranslation && simpleDeclaration.getRawSignature().startsWith("union")) {
			// [U] The value of an incomplete 'union' may not be used.
			String message1 = MessageFactory.getInstance().getMessage(544);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + simpleDeclaration,
					simpleDeclaration));
			// [U] '%s' has incomplete type and no linkage - this is undefined.
			String message3 = MessageFactory.getInstance().getMessage(623);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message3 + "--" + simpleDeclaration,
					simpleDeclaration));
			// [U] There are no named members in this 'struct' or 'union'.
			String message4 = MessageFactory.getInstance().getMessage(636);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message4 + "--" + simpleDeclaration,
					simpleDeclaration));
			isViolated = true;
		}

		return super.visit(simpleDeclaration);

	}
}
