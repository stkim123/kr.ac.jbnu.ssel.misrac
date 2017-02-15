package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Functions shall be declared at file scope.
 * 
 * Declaring functions at block scope may be confusing, and can lead to
 * undefined behaviour.
 * 
 * DONE!!
 * 
 * @author sangjin
 *
 */
public class Rule08_6_Req extends AbstractMisraCRule {

	public Rule08_6_Req(IASTTranslationUnit ast) {
		super("Rule08_6_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitStatements = true;
	}

	@Override
	public int visit(IASTDeclarationStatement statement) {
		
		boolean isFunctionCall = false;
		
		for(IASTNode simple : statement.getChildren()){
			if(simple instanceof IASTSimpleDeclaration){
				for(IASTNode funcDec : simple.getChildren()){
					if(funcDec instanceof IASTStandardFunctionDeclarator){
						isFunctionCall = true;
					}
				}
			}
		}

		if (statement.getParent() instanceof IASTCompoundStatement) {
			if(isFunctionCall){
				// Function with external linkage declared at block scope.
				String message1 = MessageFactory.getInstance().getMessage(3221);
				violationMsgs.add(new ViolationMessage(this,
						getRuleID() + ":" + message1 + "--" + statement.getRawSignature(), statement));
				isViolated = true;
			}
		}
		return super.visit(statement);
	}

}