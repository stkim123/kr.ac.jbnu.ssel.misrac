package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTContinueStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;

public class Rule14_5_Req extends AbstractMisraCRule {

	public Rule14_5_Req(IASTTranslationUnit ast) {
		super("Rule14_5_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitDeclarators = true;
		shouldVisitComment = false;
	}

	@Override
	protected int visit(IASTFunctionDefinition functionDefinition) {
		System.out.println("functionDefinition : "+functionDefinition.getRawSignature());
		return super.visit(functionDefinition);
	}

}
