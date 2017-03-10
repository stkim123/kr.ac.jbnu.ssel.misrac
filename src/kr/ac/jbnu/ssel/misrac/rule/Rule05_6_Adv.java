package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * No identifier in one name space should have the same spelling as an
 * identifier in another name space, with the exception of structure member and
 * union member names.
 * 
 * Name space and scope are different. This rule is not concerned with scope.
 * For example, ISO C allows the same identifier (vector) for both a tag and a
 * typedef at the same scope.
 * 
 * 
 * [STATUS: DONE]
 * 
 * @author sangjin
 *
 */

public class Rule05_6_Adv extends AbstractMisraCRule {

	private static HashSet<String> declarationInComposite= new HashSet<String>();

	public Rule05_6_Adv(IASTTranslationUnit ast) {
		super("Rule05_6_Adv", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitStatements = true;
		declarationInComposite.clear();
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {

		String typeString = simpleDeclaration.getRawSignature();
		if( simpleDeclaration.getParent() instanceof IASTCompositeTypeSpecifier)
		{
			String valueName = simpleDeclaration.getDeclarators()[0].getName().toString();
			declarationInComposite.add(valueName);
		}
		else{
			String valueName = simpleDeclaration.getDeclarators()[0].getName().toString();
			if(declarationInComposite.contains(valueName)){
				//Another identifier '%s' is already in scope in a different namespace.
				String message1 = MessageFactory.getInstance().getMessage(780);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + simpleDeclaration.getRawSignature(), simpleDeclaration));
				//'%s' is being used as a structure/union member as well as being a label, tag or ordinary identifier.
				String message2 = MessageFactory.getInstance().getMessage(781);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + simpleDeclaration.getRawSignature(), simpleDeclaration));
				isViolated = true;
			}
		}
		

		return super.visit(simpleDeclaration);
	}
}
