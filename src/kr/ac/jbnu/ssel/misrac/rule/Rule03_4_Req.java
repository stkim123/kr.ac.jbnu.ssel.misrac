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

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorPragmaStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 03.4: (Required) All uses of the #pragma directive shall be
 * documented and explained.
 * 
 * This rule places a requirement on the user of this standard to produce a list
 * of any pragmas they choose to use in an application. The meaning of each
 * pragma shall be documented. There shall be sufficient supporting description
 * to demonstrate that the behaviour of the pragma, and its implications for the
 * application, have been fully understood. Any use of pragmas should be
 * minimised, localised and encapsulated within dedicated functions wherever
 * possible.
 * 
 * TODO
 * 
 * @author kang
 */

public class Rule03_4_Req extends AbstractMisraCRule {

	private final static String _PRAGMA = "#pragma";

	public Rule03_4_Req(IASTTranslationUnit ast) {
		super("Rule03_4_Req", false, ast);
		shouldVisitPreprocessor = true;
	}

	
	
	
	@Override
	protected int visit(IASTPreprocessorPragmaStatement pragmaStatement) {
		// Parser Preprocessor
		String rawSignature = pragmaStatement.getRawSignature();
		String[] proPragSt = rawSignature.split(" ");

		if (rawSignature.contains(_PRAGMA)) {
			isViolated = true;
			
			String message = MessageFactory.getInstance().getMessage(5123);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message + "--" + _PRAGMA, pragmaStatement));

			
		}
		return super.visit(pragmaStatement);
	}

}
