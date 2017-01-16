package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Identifiers in an inner scope shall not use the same name as an identifier in an outer scope, and therefore hide that identifier.
 *
 * The terms outer and inner scope are defined as follows. 
 * Identifiers that have file scope can be considered as having the outermost scope. 
 * Identifiers that have block scope have a more inner scope. Successive, nested blocks, introduce more inner scopes. 
 * This rule disallows the case where a second inner definition hides an outer definition. 
 * If the second definition does not hide the first definition, then this rule is not violated.
 * Hiding identifiers with an identifier of the same name in a nested scope leads to code that is very confusing.
 * 
 * DONE!!
 * 
 * @author sangjin
 *
 */
public class Rule05_2_Req extends AbstractMisraCRule {

	private HashSet<String> innerDeclaration = new HashSet<String>();
	private HashSet<String> outerDeclaration = new HashSet<String>();

	public Rule05_2_Req(IASTTranslationUnit ast) {
		super("Rule05_2_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitDeclarators = true;
		shouldVisitPreprocessor = true;
		shouldVisitExpressions = true;
		
		innerDeclaration.clear();
		outerDeclaration.clear();
	}
	
	private void init(IASTSimpleDeclaration simpleDeclaration){
		
		if(simpleDeclaration.getParent() instanceof IASTDeclarationStatement){
			if(!innerDeclaration.contains(simpleDeclaration.getRawSignature())){
				innerDeclaration.add(simpleDeclaration.getRawSignature());
			}
		}
		else{
			if(!outerDeclaration.contains(simpleDeclaration.getRawSignature())){
				outerDeclaration.add(simpleDeclaration.getRawSignature());
			}
		}
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		
		init(simpleDeclaration);
		
		if(simpleDeclaration.getParent() instanceof IASTDeclarationStatement){
			if(outerDeclaration.contains(simpleDeclaration.getRawSignature())){
				
				//This declaration of tag '%s' hides a more global declaration.
				String message1 = MessageFactory.getInstance().getMessage(2547);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + simpleDeclaration, simpleDeclaration));
				//This declaration of '%s' hides a more global declaration.
				String message2 = MessageFactory.getInstance().getMessage(3334);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + simpleDeclaration, simpleDeclaration));
				
				isViolated = true;
			}
		}
		else{
			if(innerDeclaration.contains(simpleDeclaration.getRawSignature())){
				
				//This declaration of tag '%s' hides a more global declaration.
				String message1 = MessageFactory.getInstance().getMessage(2547);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + simpleDeclaration, simpleDeclaration));
				//This declaration of '%s' hides a more global declaration.
				String message2 = MessageFactory.getInstance().getMessage(3334);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + simpleDeclaration, simpleDeclaration));
				
				isViolated = true;
			}
		}
		
		return super.visit(simpleDeclaration);
	}

}
