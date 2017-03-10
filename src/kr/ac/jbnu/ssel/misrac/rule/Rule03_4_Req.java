//package kr.ac.jbnu.ssel.misrac.rule;
//
//import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
//import org.eclipse.cdt.core.dom.ast.IASTPreprocessorPragmaStatement;
//import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
//
//import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
//import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
//import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;
//
package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTPreprocessorPragmaStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationLevel;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 03.4: (Required) All uses of the #pragma directive shall be
 * documented and explained.
 * 
 * This rule places a requirement on the user of this standard to produce a list
 * of any pragmas they choose to use in an application. The meaning of each
 * pragma shall be documented. There shall be sufficient supporting description
 * to demonstrate that the behaviour of the pragma, and its implications for the
 * application, have been fully understood. 
 * 
 * Any use of pragmas should be minimised, localised and encapsulated within dedicated functions wherever
 * possible.
 * 
 * [STATUS: not statically checkable, partially support]
 * 
 * @author Seunghyeon Kang
 */

public class Rule03_4_Req extends AbstractMisraCRule {

	public Rule03_4_Req(IASTTranslationUnit ast) {
		super("Rule03_4_Req", false, ast);
		shouldVisitPreprocessor = true;
	}

	@Override
	protected int visit(IASTPreprocessorPragmaStatement pragmaStatement) {

		String rawSignature = pragmaStatement.getRawSignature();
		String[] proPragSt = rawSignature.split(" ");

		isViolated = true;

		String message = MessageFactory.getInstance().getMessage(3116);
		violationMsgs.add(new ViolationMessage(this,
				getRuleID() + ":" + message + "--" + proPragSt[0] + "-" + pragmaStatement.hashCode(), pragmaStatement, ViolationLevel.warning));

		return super.visit(pragmaStatement);
	}

}
