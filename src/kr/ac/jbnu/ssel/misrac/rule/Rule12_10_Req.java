package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * The comma operator shall not be used.
 *
 * Use of the comma operator is generally detrimental to the readability of code, and the same effect can be achieved by other means.
 *
 * [STATUS: DONE]
 * @author sangjin
 *
 */
public class Rule12_10_Req extends AbstractMisraCRule {
	private static HashSet<String> charList = new HashSet<String>();
	private final static char COMMA = ',';

	public Rule12_10_Req(IASTTranslationUnit ast) {
		super("Rule12_10_Req", false, ast);
		shouldVisitDeclarations = true;
	}

	@Override
	protected int visit(IASTFunctionDefinition functionDefinition) {

		for(IASTNode node: functionDefinition.getBody().getChildren()){
			char[] charArray = node.getRawSignature().toCharArray();
			for(int i=0; i<charArray.length; i++){
				if(charArray[i]==COMMA){
					
//					The comma operator has been used outside a 'for' statement.
					String message1 = MessageFactory.getInstance().getMessage(3417);
					violationMsgs.add(
							new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + node.getRawSignature(), node));
//					The comma operator has been used in a 'for' statement.
					String message2 = MessageFactory.getInstance().getMessage(3418);
					violationMsgs.add(
							new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + node.getRawSignature(), node));
					isViolated = true;
				}
			}
		}

		return super.visit(functionDefinition);
	}
}