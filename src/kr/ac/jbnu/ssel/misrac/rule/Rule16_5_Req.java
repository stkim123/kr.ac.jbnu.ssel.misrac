package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Functions with no parameters shall be declared and defined with the parameter
 * list void.
 * 
 * Functions shall be declared with a return type (see Rule ), that type being
 * void if the function does not return any data. Similarly, if the function has
 * no parameters, the parameter list shall be declared as void. Thus for
 * example, a function, myfunc, which neither takes parameters nor returns a
 * value would be declared as
 * 
 * DONE!!
 * 
 * @author sangjin
 *
 */
public class Rule16_5_Req extends AbstractMisraCRule {

	public Rule16_5_Req(IASTTranslationUnit ast) {
		super("Rule16_5_Req", false, ast);
		shouldVisitDeclarators = true;
		shouldVisitDeclarations = true;
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {

		for (IASTNode simpleChilde : simpleDeclaration.getChildren()) {
			if (simpleChilde instanceof IASTStandardFunctionDeclarator) {
				IASTStandardFunctionDeclarator standardfunc = (IASTStandardFunctionDeclarator) simpleChilde;
				if (standardfunc.getParameters().length == 0 ) {
					// Function has been declared with an empty parameter list.
					String message1 = MessageFactory.getInstance().getMessage(3001);
					violationMsgs.add(new ViolationMessage(this,
							getRuleID() + ":" + message1 + "--" + standardfunc.getRawSignature(),
							standardfunc));
					isViolated = true;
				}
			}
		}
		return super.visit(simpleDeclaration);
	}

	@Override
	protected int visit(IASTFunctionDefinition functionDefinition) {
		for (IASTNode simpleChilde : functionDefinition.getChildren()) {
			if (simpleChilde instanceof IASTStandardFunctionDeclarator) {
				IASTStandardFunctionDeclarator standardfunc = (IASTStandardFunctionDeclarator) simpleChilde;
				if (standardfunc.getParameters().length == 0 ) {
					// Function has been declared with an empty parameter list.
					String message1 = MessageFactory.getInstance().getMessage(3007);
					violationMsgs.add(new ViolationMessage(this,
							getRuleID() + ":" + message1 + "--" + standardfunc.getRawSignature(),
							standardfunc));
					isViolated = true;
				}
			}
		}
		return super.visit(functionDefinition);
	}

}
