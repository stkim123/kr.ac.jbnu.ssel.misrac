package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Precautions shall be taken in order to prevent the contents of a header file being included twice.
 * 
 * When a translation unit contains a complex hierarchy of nested header files it can happen that a particular header file is included more than once. 
 * This can be, at best, a source of confusion. If it leads to multiple or conflicting definitions,
 * the result can be undefined or erroneous behaviour.
 * 
 * Multiple inclusions of a header file can sometimes be avoided by careful design.
 * If this is not possible, a mechanism must be in place to prevent the file contents from being included more than once. 
 * A common approach is to associate a macro with each file;
 * the macro is defined when the file is included for the first time and used subsequently 
 * when the file is included again to exclude the contents of the file.
 * 
 * DOING	
 * 
 * @author sangjin
 *
 */
public class Rule19_15_Req extends AbstractMisraCRule {
	
	private static ArrayList<String> id_list = new ArrayList<String>();

	public Rule19_15_Req(IASTTranslationUnit ast) {
		super("Rule19_15_Req", false, ast);
		shouldVisitTranslationUnit = true;
		
		id_list.clear();
		
	}

	@Override
	public int visit(IASTTranslationUnit translationUnit) {
		
		String[] allCode = translationUnit.getRawSignature().split("\\n");
		
		for(int i=0; i<allCode.length; i++){
			
			String[] lineOfCode = allCode[i].split(" ");
			
			if(allCode[i].startsWith("#")&&!(id_list.contains(lineOfCode[1]))){
				id_list.add(lineOfCode[1]);
			}
			else if(allCode[i].startsWith("#")&&(id_list.contains(lineOfCode[1]))){
				if(!allCode[i+1].startsWith("/*")){
					//Include file code is not protected against repeated inclusion
					String message = MessageFactory.getInstance().getMessage(883);
					violationMsgs.add(
							new ViolationMessage(this, getRuleID() + ":" + message + "--" + allCode[i], translationUnit));
					isViolated = true;
				}
			}
		}

		return super.visit(translationUnit);

	}
}