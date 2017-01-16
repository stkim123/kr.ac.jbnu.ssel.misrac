package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * The input/output library <stdio.h> shall not be used in production code.
 *
 * This includes file and I/O functions fgetpos, fopen, ftell, gets, perror, remove, rename, and ungetc.
 * Streams and file I/O have a large number of unspecified, undefined and implementation-defined behaviours associated with them. It is assumed within this standard that they will not normally be needed in production code in embedded systems.
 *
 * If any of the features of stdio.h need to be used in production code, then the issues associated with the feature need to be understood.
 * 
 * 
 * 
 * DONE!!
 * 
 * @author stkim
 *
 */

public class Rule20_9_Req extends AbstractMisraCRule {

	private final static String STDIO = "#include <stdio.h>";

	public Rule20_9_Req(IASTTranslationUnit ast) {
		super("Rule20_9_Req", false, ast);
		shouldVisitPreprocessor = true;

	}

	@Override
	protected int visit(IASTPreprocessorIncludeStatement includeStatement) {

		if (includeStatement.getRawSignature().equals(STDIO)) {
			
//			The input/output library <stdio.h> shall not be used in production code.
			String message1 = MessageFactory.getInstance().getMessage(5124);
			violationMsgs
					.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + STDIO, includeStatement));

			isViolated = true;
		}
		return super.visit(includeStatement);
	}
}
