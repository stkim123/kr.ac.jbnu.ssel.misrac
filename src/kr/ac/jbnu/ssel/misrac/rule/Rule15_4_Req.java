package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * A switch expression shall not represent a value that is effectively Boolean.
 * 
 * See 'Boolean expressions' in the glossary.
 * 
 * [STATUS: DONE]
 * @author sangjin
 *
 */
public class Rule15_4_Req extends AbstractMisraCRule {
	

	public Rule15_4_Req(IASTTranslationUnit ast) {
		super("Rule15_4_Req", false, ast);
		shouldVisitStatements = true;

	}

	@Override
	protected int visit(IASTSwitchStatement statement) {
		
		IASTNode targetNode = statement.getChildren()[0];
		
		if(targetNode instanceof IASTBinaryExpression){
			//Using relational or logical operators in a 'switch' expression is usually a programming error.
			String msg1 = MessageFactory.getInstance().getMessage(735);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + targetNode.getRawSignature(), targetNode));
			isViolated = true;
		}
		

		return super.visit(statement);
	}

}
