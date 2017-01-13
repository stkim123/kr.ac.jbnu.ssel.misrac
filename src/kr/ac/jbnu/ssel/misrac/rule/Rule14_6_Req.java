package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTBreakStatement;
import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
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
 * @author stkim
 *
 */
public class Rule14_6_Req extends AbstractMisraCRule {

	public Rule14_6_Req(IASTTranslationUnit ast) {
		super("Rule14_6_Req", false, ast);
		shouldVisitStatements = true;
	}

	/**
	 * For each compound statement(body of the loop, if, switch-case etc), the zero or only one break statement should be existed. 
	 * Exceptionally, the switch-case statement can have multiple break statement. 
	 */
	protected int visit(IASTCompoundStatement statement) {
		IASTNode[] internalStatements = statement.getChildren();
		
		boolean breakStmtUsed = false;
		boolean violated = false;
		
		for (IASTNode iastNode : internalStatements) {
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
		}
		
		return super.visit(statement);
	}
	
}
