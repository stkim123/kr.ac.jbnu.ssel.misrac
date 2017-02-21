package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.c.ICASTCompositeTypeSpecifier;

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
 * DONE!!
 * 
 * @author sangjin
 *
 */

public class Rule05_6_Adv extends AbstractMisraCRule {
	
	private static HashSet<String> typeSet = new HashSet<String>();

	public Rule05_6_Adv(IASTTranslationUnit ast) {
		super("Rule05_6_Adv", false, ast);
		shouldVisitDeclarations = true;	
		shouldVisitStatements = true;
		typeSet.clear();
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		
		for (IASTNode specifier : simpleDeclaration.getChildren()) {
			if (specifier instanceof ICASTCompositeTypeSpecifier) {
				String typeString = specifier.getRawSignature();
				if (typeString.startsWith("struct") || typeString.startsWith("union")) {
					for (IASTNode param : specifier.getChildren()) {
						for(IASTNode target: param.getChildren()){
							if(target instanceof IASTDeclarator){
								typeSet.add(target.getRawSignature());
							}
						}
					}
				}
			}
		}

		return super.visit(simpleDeclaration);
	}
	
	@Override
	protected int visit(IASTDeclarationStatement statement) {
		
		for(IASTNode simple: statement.getChildren()){
			if(simple instanceof IASTSimpleDeclaration){
				for(IASTNode decl: simple.getChildren()){
					if(decl instanceof IASTDeclarator){
						String target = decl.getRawSignature();
						if(typeSet.contains(target)){
							//Another identifier '%s' is already in scope in a different namespace.
							String message1 = MessageFactory.getInstance().getMessage(780);
							violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + target, simple));
							//'%s' is being used as a structure/union member as well as being a label, tag or ordinary identifier.
							String message2 = MessageFactory.getInstance().getMessage(781);
							violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + target, simple));
							isViolated = true;
						}
					}
				}
			}
		}
		
		return super.visit(statement);
	}

}