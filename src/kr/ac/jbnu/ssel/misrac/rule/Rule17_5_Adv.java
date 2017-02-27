package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * The declaration of objects should contain no more than 2 levels of pointer
 * indirection.
 * 
 * Use of more than 2 levels of indirection can seriously impair the ability to
 * understand the behaviour of the code, and should therefore be avoided.
 * 
 * 
 * DONE!!
 * 
 * @author sangjin
 *
 */
public class Rule17_5_Adv extends AbstractMisraCRule {

	public Rule17_5_Adv(IASTTranslationUnit ast) {
		super("Rule17_5_Adv", false, ast);
		shouldVisitStatements = true;
		shouldVisitDeclarations = true;
		shouldVisitDeclarators = true;
	}

	@Override
	protected int visit(IASTSimpleDeclaration declaration) {
		int count = 0;
		for (IASTNode decl : declaration.getChildren()) {
			if (decl instanceof IASTDeclarator) {

				String target;
				target = decl.getRawSignature();

				if (target.contains("(")) {
					char[] arr = target.toCharArray();
					for (int i = 0; i < arr.length; i++) {
						if (arr[i] == '*') {
							count++;
						}
						if (arr[i] == '(') {
							if (count < 3) {
								count = 0;
							}
						}
					}

					if (count > 2) {
						//3261 : Member of struct/union defined with more than 2 levels of indirection.
						String message1 = MessageFactory.getInstance().getMessage(3261);
						violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "-"+ decl.getRawSignature(), decl));
						//3262 : Object defined or declared with more than 2 levels of indirection.
						String message2 = MessageFactory.getInstance().getMessage(3262);
						violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "-"+ decl.getRawSignature(), decl));
						isViolated = true;
					}

				} else {
					if (target.contains("const")) {
						String[] splitString = target.split(" ");
						for (int i = 0; i < splitString.length; i++) {
							if (splitString[i].equals("const")) {
								count++;
							}
						}
					}

					for (int i = 0; i < target.length(); i++) {
						char data = target.toCharArray()[i];
						if (data == '*') {
							count++;
						}
					}

					if (count > 2) {
						//3261 : Member of struct/union defined with more than 2 levels of indirection.
						String message1 = MessageFactory.getInstance().getMessage(3261);
						violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "-"+ decl.getRawSignature(), decl));
						//3262 : Object defined or declared with more than 2 levels of indirection.
						String message2 = MessageFactory.getInstance().getMessage(3262);
						violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "-"+ decl.getRawSignature(), decl));
						isViolated = true;
					}
					
					if ((count > 2)&&(target.startsWith("typedef"))) {
						//3260 : Typedef defined with more than 2 levels of indirection.
						String message1 = MessageFactory.getInstance().getMessage(3260);
						violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "-"+ decl.getRawSignature(), decl));
						isViolated = true;
					}
				}

			}

		}

		return super.visit(declaration);
	}
	
	@Override
	protected int visit(IASTCompoundStatement statement) {
		int count;
		IASTNode func = statement.getParent();
		
		for(IASTNode node : func.getChildren()){
			if(node instanceof IASTStandardFunctionDeclarator){
				for(IASTNode param : node.getChildren()){
					if(param instanceof IASTParameterDeclaration){
						count=0;
						String target = param.getRawSignature();
						char[] targetArray = target.toCharArray();
						
						for(int i=0; i<targetArray.length; i++){
							if(targetArray[i] == '*'){
								count++;
							}
						}
						
						if(target.contains("const")){
							String[] targetSplit = target.split(" ");
							
							for(int i=0; i<targetSplit.length; i++){
								if(targetSplit[i].equals("const")){
									count++;
								}
							}
						}
						
						if(target.contains("[]")){
							count++;
						}
						
						if (count > 2) {
							// 3263 : Function defined or declared with a return type which has more than 2 levels of indirection.
							String message1 = MessageFactory.getInstance().getMessage(3263);
							violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "-"+ param.getRawSignature(), param));
							
							isViolated = true;
						}
						
					}
				}
			}
		}
		
		return super.visit(statement);
	}
}