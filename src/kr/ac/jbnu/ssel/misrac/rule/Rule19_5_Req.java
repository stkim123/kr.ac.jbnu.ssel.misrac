package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Macros shall not be #define'd or #undef'd within a block.
 * 
 * While it is legal C to place #define or #undef directives anywhere in a code
 * file, placing them inside blocks is misleading as it implies a scope
 * restricted to that block, which is not the case. Normally, #define directives
 * will be placed near the start of a file, before the first function
 * definition. Normally, #undef directives will not be needed. (see Rule )
 * 
 * [STATUS: TODO]
 * "룰을 잘못 이해하고 작성한 듯...--수정 ::  Macro가 compound안에 정의되면 안된다."
 * 
 * @author sangjin
 *
 */
public class Rule19_5_Req extends AbstractMisraCRule {

	public Rule19_5_Req(IASTTranslationUnit ast) {
		super("Rule19_5_Req", false, ast);
		shouldVisitDeclarations = true;
	}

	@Override
	protected int visit(IASTFunctionDefinition functionDefinition) {
		
		String[] parseString = functionDefinition.getRawSignature().split("#");

		for (int i = 0; i < parseString.length; i++) {
			if (parseString[i].startsWith("undef")) {
				//	Using #define or #undef inside a function.
				String message1 = MessageFactory.getInstance().getMessage(842);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + parseString[i],
						functionDefinition));
				isViolated = true;
			}
		}

		return super.visit(functionDefinition);
	}

}