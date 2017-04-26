package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorFunctionStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * The #include directive shall be followed by either a <filename> or "filename"
 * sequence.
 * 
 * For example, the following are allowed.
 * 
 * 
 * [STATUS: not statically checkable, parcially support] "define으로 filename이 정해진 경우 또한 검증해야한다. "
 * 
 * @author sangjin
 *
 */
public class Rule19_3_Req extends AbstractMisraCRule {

	public Rule19_3_Req(IASTTranslationUnit ast) {
		super("Rule19_3_Req", false, ast);
		shouldVisitPreprocessor = true;
	}

	@Override
	public int visit(IASTPreprocessorIncludeStatement includeStatement) {

		String[] includString = includeStatement.getRawSignature().split(" ");

		if ((includString[1].startsWith("\"") && includString[1].endsWith("\""))
				|| (includString[1].startsWith("<") && includString[1].endsWith(">"))) {
		}
		
		
		else {
			// [U] The '#include' preprocessing directive has not been followed by <h-char-sequence> or "s-char-sequence".
			String message1 = MessageFactory.getInstance().getMessage(809);
			violationMsgs.add(new ViolationMessage(this,
					getRuleID() + ":" + message1 + "--" + includeStatement.getRawSignature(), includeStatement));
			isViolated = true;
		}

		return super.visit(includeStatement);

	}
}
