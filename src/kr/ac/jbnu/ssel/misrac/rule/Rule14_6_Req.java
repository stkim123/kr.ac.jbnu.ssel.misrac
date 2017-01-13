package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTBreakStatement;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 14.6: (Required) For any iteration statement there shall be
 * at most one break statement used for loop termination.
 * 
 * These rules are in the interests of good structured programming. One break
 * statement is allowed in a loop since this allows, for example, for dual
 * outcome loops or for optimal coding.
 * 
 * TODO: 현재, 구현은 loop내에 break 구문이 두개 이상 있는지 확인하는 코드로 구현됨,
 * 그런데, rule설명은 loop내의 여러 statement에 있는 것까지 확인하라고 하는것 같음. 
 * 
 * @author stkim
 *
 */
public class Rule14_6_Req extends AbstractMisraCRule {

	public Rule14_6_Req(IASTTranslationUnit ast) {
		super("Rule14_6_Req", false, ast);
		shouldVisitStatements = true;
	}

	/**
	 * For each compound statement(body of the loop, if, switch-case etc), 
	 * the zero or only one break statement should be existed. 
	 */
	protected int visit(IASTCompoundStatement statement) {
		
		IASTNode parent = statement.getParent();
		// only apply this rule into loop.
		if(!(parent instanceof IASTForStatement || parent instanceof IASTWhileStatement ||
				parent instanceof IASTDoStatement))
		{
			return super.visit(statement);
		}
		
		IASTNode[] internalStatements = statement.getChildren();
			
			
		boolean breakStmtUsed = false;
		boolean violated = false;
		
		for (IASTNode iastNode : internalStatements) 
		{
//			if( CASTUtil.containsASTNode(iastNode, IASTBreakStatement.class))
//			{
//				if( ! breakStmtUsed)		// if the breakstatement is used first, then save it first.
//				{
//					breakStmtUsed = true; 	
//				}
//				else						// if the breakstatement is used before. 	
//				{
//					violated = true;
//				}
//			}
			
			if( iastNode instanceof IASTBreakStatement)
			{
				if( ! breakStmtUsed)		// if the breakstatement is used first, then save it first.
				{
					breakStmtUsed = true; 	
				}
				else						// if the breakstatement is used before. 	
				{
					violated = true;
				}
			}
		}
		
		if( violated )
		{
			String msg = MessageFactory.getInstance().getMessage(0771);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msg, statement));
			isViolated = true;
		}
		
		return super.visit(statement);
	}
	
}
