package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * The error indicator errno shall not be used.
 *
 * errno is a facility of C, which in theory should be useful, but which in practice is poorly defined by the standard. 
 * A non zero value may or may not indicate that a problem has occurred; as a result it shall not be used. 
 * Even for those functions for which the behaviour of errno is well defined, 
 * it is preferable to check the values of inputs before calling the function rather than rely on using errno to trap errors.
 * 
 * [STATUS: DONE]
 * 
 * @author Sangjin
 *
 */

public class Rule20_5_Req extends AbstractMisraCRule {

	public Rule20_5_Req(IASTTranslationUnit ast) {
		super("Rule20_5_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitDeclarators = true;
		shouldVisitTranslationUnit = true;
	}

	@Override
	public int visit(IASTTranslationUnit unit) {
		for(IASTNode functionDefinition : unit.getChildren()){
			if(functionDefinition instanceof IASTFunctionDefinition){
				for (IASTNode child : functionDefinition.getChildren()) {
					if (child instanceof IASTCompoundStatement) {
						for (IASTNode grandChild : child.getChildren()) {
							if (grandChild instanceof IASTIfStatement) {
								for (IASTNode temp : grandChild.getChildren()) {
									if (temp instanceof IASTBinaryExpression) {
										for (IASTNode tempTwo : temp.getChildren()) {
											if(tempTwo instanceof IASTUnaryExpression){
												if(tempTwo.getRawSignature().equals("errno")){
													
													//The error indicator errno shall not be used.
													String message1 = MessageFactory.getInstance().getMessage(5119);
													violationMsgs.add(new ViolationMessage(this,
															getRuleID() + ":" + message1 + "--" + tempTwo.getRawSignature(), tempTwo));
													isViolated = true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		
		return super.visit(unit);
	}

}
