package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTGotoStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

public class Rule14_4_Req extends AbstractMisraCRule {

	public Rule14_4_Req(IASTTranslationUnit ast) {
		super("Rule14_4_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitDeclarators = true;
		shouldVisitComment = false;
	}

	@Override
	protected int visit(IASTFunctionDefinition functionDefinition) {

		for (IASTNode child : functionDefinition.getChildren()) {
			if (child instanceof IASTCompoundStatement) {
				for (IASTNode grandChild : child.getChildren()) {
					if (grandChild instanceof IASTGotoStatement) {
						String message1 = MessageFactory.getInstance().getMessage(2001);
						violationMsgs.add(new ViolationMessage(this,
								getRuleID() + ":" + message1 + "--" + grandChild.getRawSignature(), grandChild));
						isViolated = true;
					}
				}
			}
		}

		return super.visit(functionDefinition);
	}
}
