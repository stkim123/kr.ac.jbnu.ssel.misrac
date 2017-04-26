package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Non-standard characters should not occur in header file names in #include
 * directives.
 * 
 * If the ', \, ", or /* characters are used between < and > delimiters or the
 * ', \, or /* characters are used between the " delimiters in a header name
 * preprocessing token, then the behaviour is undefined. Use of the \ character
 * is permitted in filename paths without the need for a deviation if required
 * by the host operating system of the development environment.
 * 
 * 
 * [STATUS: not statically checkable, parcially support] "이거 문자별로 메세지를 구분시켜야 됩니다. 얼마 안걸릴듯."
 * 
 * 
 * @author sangjin
 *
 */
public class Rule19_2_Adv extends AbstractMisraCRule {
	private static final String Non_Standard1 = "/*";
	private static final String Non_Standard2 = "$";
	private static final String Non_Standard3 = "'";
	private static final String Non_Standard4 = "\\";
	private static final String[] Non_Standard = {Non_Standard1,Non_Standard2,Non_Standard3,Non_Standard4};
	

	public Rule19_2_Adv(IASTTranslationUnit ast) {
		super("Rule19_2_Adv", false, ast);
		shouldVisitPreprocessor = true;
	}

	@Override
	protected int visit(IASTPreprocessorIncludeStatement includeStatement) {
		
		String[] parseString = includeStatement.getRawSignature().split(" ");
		String target = parseString[1];
				
		for(int i=0; i<Non_Standard.length; i++){
			if(target.contains(Non_Standard[i])){
				
				System.out.println("Non_Standard :: "+Non_Standard[i]);
				
				//[U] Using any of the characters ' " or /* in '#include <%s>' gives undefined behaviour.
				String message1 = MessageFactory.getInstance().getMessage(813);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + target,
						includeStatement));
				//[U] Using the characters ' or /* in '#include "%s"' gives undefined behaviour.
				String message2 = MessageFactory.getInstance().getMessage(814);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + target,
						includeStatement));
				//[E] Use of '\\' in this '#include' line is a PC extension - this usage is non-portable.
				String message3 = MessageFactory.getInstance().getMessage(831);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message3 + "--" + target,
						includeStatement));
				isViolated = true;
			}
		}

		return super.visit(includeStatement);
	}

}