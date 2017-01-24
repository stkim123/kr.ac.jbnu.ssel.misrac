package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Identifiers shall be given for all of the parameters in a function prototype
 * declaration.
 * 
 * Names shall be given for all the parameters in the function declaration for
 * reasons of compatibility, clarity and maintainability.
 * 
 * DONE!!
 * 
 * @author sangjin
 *
 */
public class Rule16_3_Req extends AbstractMisraCRule {

	public Rule16_3_Req(IASTTranslationUnit ast) {
		super("Rule16_3_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitParameterDeclarations = true;
	}

	@Override
	public int visit(IASTParameterDeclaration parameterDeclaration) {
		if(!parameterDeclaration.getRawSignature().startsWith("void")){
			if(parameterDeclaration.getDeclarator().getName().getRawSignature().equals("")){
				//Parameter identifiers missing in function prototype declaration.
				String message1 = MessageFactory.getInstance().getMessage(1335);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 +"--"+ parameterDeclaration, parameterDeclaration));
				//Parameter identifiers missing in declaration of a function type.
				String message2 = MessageFactory.getInstance().getMessage(1336);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 +"--"+ parameterDeclaration, parameterDeclaration));
				isViolated = true;
			}
		}
		return super.visit(parameterDeclaration);
	}

}
