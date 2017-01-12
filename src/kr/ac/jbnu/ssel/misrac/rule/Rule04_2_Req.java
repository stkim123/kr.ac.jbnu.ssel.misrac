package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;

public class Rule04_2_Req extends AbstractMisraCRule{
	
	public Rule04_2_Req(IASTTranslationUnit ast) {
		super("Rule04_2_Req", false, ast);
		shouldVisitComment = true;
	    }
}
