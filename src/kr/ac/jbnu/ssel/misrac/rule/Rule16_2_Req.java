package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Functions shall not call themselves, either directly or indirectly.
 * 
 * This means that recursive function calls cannot be used in safety-related
 * systems. Recursion carries with it the danger of exceeding available stack
 * space, which can be a serious error. Unless recursion is very tightly
 * controlled, it is not possible to determine before execution what the
 * worst-case stack usage could be.
 * 
 * [STATUS: DONE]
 * 
 * @author sangjin
 *
 */
public class Rule16_2_Req extends AbstractMisraCRule {

	private static String functionName;

	public Rule16_2_Req(IASTTranslationUnit ast) {
		super("Rule16_2_Req", false, ast);
		shouldVisitDeclarations = true;
	}

	@Override
	protected int visit(IASTFunctionDefinition functionDefinition) {

		functionName = functionDefinition.getDeclarator().getRawSignature().split(" ")[0];

		String[] parseString = functionDefinition.getBody().getRawSignature().split(" ");
		

		for(int i=0; i<parseString.length; i++){
			if(parseString[i].equals(functionName)){
				
				//	Functions are indirectly recursive.
				String message1 = MessageFactory.getInstance().getMessage(1520);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + parseString[i],
						functionDefinition));
				//	Recursive call to function containing this call.
				String message2 = MessageFactory.getInstance().getMessage(3670);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + parseString[i],
						functionDefinition));
				isViolated = true;
			}
		}

		return super.visit(functionDefinition);
	}

}
