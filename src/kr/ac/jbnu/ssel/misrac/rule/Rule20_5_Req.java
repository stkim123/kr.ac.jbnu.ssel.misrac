package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

public class Rule20_5_Req extends AbstractMisraCRule {

	public Rule20_5_Req(IASTTranslationUnit ast) {
		super("Rule20_5_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitDeclarators = true;
		shouldVisitComment = false;
	}

	@Override
	protected int visit(IASTFunctionDefinition functionDefinition) {
		for (IASTNode child : functionDefinition.getChildren()) {
			if (child instanceof IASTCompoundStatement) {
				for (IASTNode grandChild : child.getChildren()) {
					if (grandChild instanceof IASTIfStatement) {
						for (IASTNode temp : grandChild.getChildren()) {
							if (temp instanceof IASTBinaryExpression) {
								for (IASTNode tempTwo : temp.getChildren()) {
									if(tempTwo instanceof IASTIdExpression){
										if(tempTwo.getRawSignature().equals("errno")){
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
		return super.visit(functionDefinition);
	}

}
