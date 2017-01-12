package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

public class Rule20_9_Req extends AbstractMisraCRule {

	private final static String STDIO = "#include <stdio.h>";
	
	public Rule20_9_Req(IASTTranslationUnit ast) {
		super("Rule20_9_Req", false, ast);
		shouldVisitPreprocessor = true;
		// shouldVisitExpressions = true;
	}

	@Override
	protected int visit(IASTPreprocessorIncludeStatement includeStatement) {

//		System.out.println("test :: "+includeStatement.getRawSignature());
		if(includeStatement.getRawSignature().equals(STDIO)){
			String message1 = MessageFactory.getInstance().getMessage(5124);
			violationMsgs.add(
					new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + STDIO, includeStatement));
			
			isViolated = true;
		}
		return super.visit(includeStatement);
	}
}
