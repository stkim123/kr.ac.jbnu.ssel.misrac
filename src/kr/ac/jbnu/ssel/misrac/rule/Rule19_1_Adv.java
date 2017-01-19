package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

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
 * DONE!! but don't catch target.
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
		
		String[] allCode = translationUnit.getRawSignature().split("\\n+");
		
		for(int i=0; i<allCode.length; i++){
			if(allCode[i].startsWith("#include")){
				
				if(allCode[i-1].startsWith("#")||allCode[i-1].startsWith("/*")){
					
				}else{
					//#include statements in a file should only be preceded by other preprocessor directives or comments.
					String message1 = MessageFactory.getInstance().getMessage(5087);
					violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + allCode[i],
							translationUnit));
					isViolated = true;
				}
				
			}
		}

		return super.visit(translationUnit);

	}
}
