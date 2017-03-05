package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * All #else, #elif and #endif preprocessor directives shall reside in the same
 * file as the #if or #ifdef directive to which they are related.
 * 
 * When the inclusion and exclusion of blocks of statements is controlled by a
 * series of preprocessor directives, confusion can arise if all of the relevant
 * directives do not occur within one file. This rule requires that all
 * preprocessor directives in a sequence of the form #if / #ifdef ... #elif ...
 * #else ... #endif shall reside in the same file. Observance of this rule
 * preserves good code structure and avoids maintenance problems. Notice that
 * this does not preclude the possibility that such directives may exist within
 * included files so long as all directives that relate to the same sequence are
 * located in one file.
 * 
 * 
 * TODO : 룰에 대해서 정확히 파악해야할 필요가 있음.
 * 
 * @author sangjin
 *
 */
public class Rule19_17_Req extends AbstractMisraCRule {

	public Rule19_17_Req(IASTTranslationUnit ast) {
		super("Rule19_17_Req", false, ast);
		shouldVisitTranslationUnit = true;
	}

	@Override
	public int visit(IASTTranslationUnit translationUnit) {
		
		int count = 0;
		
		String[] allCode = translationUnit.getRawSignature().split("\\n");
		
		for(int i=0; i<allCode.length; i++){
			if(allCode[i].startsWith("#else")||allCode[i].startsWith("#elif")||allCode[i].startsWith("#endif")){
				for(int j=0; j<i; j++){
					if(allCode[j].startsWith("#if")||allCode[j].startsWith("#ifdef")){
						count ++;
					}
				}
			}
		}
		
		if(count==0){
			//'#if...' not matched by '#endif' in included file. This is probably an error.
			String message1 = MessageFactory.getInstance().getMessage(3317);
			violationMsgs.add(
					new ViolationMessage(this, getRuleID() + ":" + message1, translationUnit));
			//'#else'/'#elif'/'#endif' in included file matched '#if...' in parent file. This is probably an error.
			String message2 = MessageFactory.getInstance().getMessage(3318);
			violationMsgs.add(
					new ViolationMessage(this, getRuleID() + ":" + message2, translationUnit));
			
			isViolated = true;
		}

		return super.visit(translationUnit);

	}
}
