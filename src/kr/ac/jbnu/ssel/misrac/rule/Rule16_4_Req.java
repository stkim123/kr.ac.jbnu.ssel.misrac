package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * The identifiers used in the declaration and definition of a function shall be
 * identical.
 * 
 * [STATUS: DONE]
 * 
 * @author sangjin
 *
 */
public class Rule16_4_Req extends AbstractMisraCRule {

	private static ArrayList<IASTStandardFunctionDeclarator> decl = new ArrayList<IASTStandardFunctionDeclarator>();
	
	public Rule16_4_Req(IASTTranslationUnit ast) {
		super("Rule16_4_Req", false, ast);
		shouldVisitDeclarators = true;
		shouldVisitDeclarations = true;
		decl.clear();
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {

		for (IASTNode declNode : simpleDeclaration.getChildren()) {
			if (declNode instanceof IASTStandardFunctionDeclarator) {
				decl.add((IASTStandardFunctionDeclarator) declNode);
			}
		}
		return super.visit(simpleDeclaration);
	}

	@Override
	protected int visit(IASTFunctionDeclarator FunctionDeclarator) {
		
		if(!decl.isEmpty()){
			String declName = FunctionDeclarator.getName().getRawSignature();

			for (int i = 0; i < decl.size(); i++) {
				if (decl.get(i).getName().getRawSignature().equals(declName)) {
										
					if (!decl.get(i).getRawSignature().equals(FunctionDeclarator.getRawSignature())) {
						// The parameter identifiers in this function declaration differ from those in a previous declaration.
						String message1 = MessageFactory.getInstance().getMessage(1330);
						violationMsgs.add(new ViolationMessage(this,
								getRuleID() + ":" + message1 + "--" + FunctionDeclarator.getRawSignature(), FunctionDeclarator));
						isViolated = true;
					}
				}
			}
		}
		

		return super.visit(FunctionDeclarator);
	}

}
