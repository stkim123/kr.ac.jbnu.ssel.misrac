package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTCastExpression;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.c.ICASTPointer;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * A cast should not be performed between a pointer to object type and a
 * different pointer to object type.
 * 
 * Conversions of this type may be invalid if the new pointer type requires a
 * stricter alignment.
 *
 * [STATUS: DONE]
 *
 * @author sangjin
 *
 */
public class Rule11_4_Adv extends AbstractMisraCRule {
	
	private static HashSet<String> pointerName = new HashSet<String>();

	public Rule11_4_Adv(IASTTranslationUnit ast) {
		super("Rule11_4_Adv", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitDeclarators = true;
		shouldVisitExpressions = true;
		
		pointerName.clear();
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		
		for(IASTNode node : simpleDeclaration.getChildren()){
			if(node instanceof IASTDeclarator){
				IASTDeclarator declarator = (IASTDeclarator)node;
				
				for(IASTNode childe : declarator.getChildren()){
					if(childe instanceof ICASTPointer){

						pointerName.add(declarator.getName().toString());
					}
				}
			}
		}

		return super.visit(simpleDeclaration);
	}
	
	@Override
	protected int visit(IASTCastExpression castExpression) {
		
		String idName = "";
		
		if(!pointerName.isEmpty()){
			
			for(IASTNode idNode : castExpression.getChildren()){
				if(idNode instanceof IASTIdExpression){
					IASTIdExpression id = (IASTIdExpression)idNode;
					idName = id.getName().toString();
				}
			}
			
			if(pointerName.contains(idName)){
				
				for(IASTNode node :castExpression.getChildren()){
					
					
					if(node instanceof IASTTypeId){
						
						String rawString = node.getRawSignature();
						if(rawString.contains("*")){
							//Casting to different object pointer type.
							String message1 = MessageFactory.getInstance().getMessage(310);
							violationMsgs.add(
									new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + castExpression.getRawSignature(), castExpression));
							//[I] Cast from a pointer to void to a pointer to object type.
							String message2 = MessageFactory.getInstance().getMessage(316);
							violationMsgs.add(
									new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + castExpression.getRawSignature(), castExpression));
							//[I] Implicit conversion from a pointer to void to a pointer to object type.
							String message3 = MessageFactory.getInstance().getMessage(317);
							violationMsgs.add(
									new ViolationMessage(this, getRuleID() + ":" + message3 + "--" + castExpression.getRawSignature(), castExpression));
							isViolated = true;
						}
						
					}
				}
			}
		}
		
		return super.visit(castExpression);
	}
}