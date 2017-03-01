package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 2.2: (Required) [next]
 * 
 * Source code shall only use C-style comments.
 * 
 * This excludes the use of // C99 style comments and C++ style comments, since
 * these are not permitted in C90. Many compilers support the // style of
 * comments as an extension to c90. The use of // in preprocessor directives
 * (e.g. #define) can vary. Also the mixing of ............ is not consistent.
 * This is more than a style issue, since different (pre C99) compilers may
 * behave differently.
 * 
 * 
 * @author Suntae Kim
 *
 */
public class Rule05_1_Req extends AbstractMisraCRule {

	private static final int ID_RECOGNIZATION_LIMIT = 31;
	private HashSet<String> idSet = new HashSet<String>();

	public Rule05_1_Req(IASTTranslationUnit ast) {
		super("Rule05_1_Req", false, ast);
		shouldVisitPreprocessor = true;
		shouldVisitExpressions = true;
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		String identifier = simpleDeclaration.getDeclarators()[0].getName().toString();

		if (identifier.length() > ID_RECOGNIZATION_LIMIT) {
			String frontPart = identifier.substring(0, ID_RECOGNIZATION_LIMIT);
			if (idSet.contains(frontPart)) {
				String message1 = MessageFactory.getInstance().getMessage(777);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + identifier,
						simpleDeclaration));

				String message2 = MessageFactory.getInstance().getMessage(779);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + identifier,
						simpleDeclaration));

				isViolated = true;
			} else {
				idSet.add(frontPart);
			}
		}
		return super.visit(simpleDeclaration);
	}

}
