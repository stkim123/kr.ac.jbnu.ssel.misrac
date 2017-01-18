package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;

/**
 * #include statements in a file should only be preceded by other preprocessor
 * directives or comments.
 * 
 * All the #include statements in a particular code file should be grouped
 * together near the head of the file. The rule states that the only items which
 * may precede a #include in a file are other preprocessor directives or
 * comments.
 * 
 * 
 * 
 * 
 * @author sangjin
 *
 */
public class Rule19_1_Adv extends AbstractMisraCRule {

	public Rule19_1_Adv(IASTTranslationUnit ast) {
		super("Rule19_1_Adv", false, ast);
		shouldVisitPreprocessor = true;
		shouldVisitStatements = true;
		shouldVisitTranslationUnit = true;
	}

	@Override
	public int visit(IASTTranslationUnit translationUnit) {
		
		System.out.println("split line :: "+translationUnit.getRawSignature().split("\\s+")[0]);

		return super.visit(translationUnit);

	}
}
