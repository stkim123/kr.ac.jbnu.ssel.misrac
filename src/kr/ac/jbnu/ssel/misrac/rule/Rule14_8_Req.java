package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTBreakStatement;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 14.8: (Required) The statement forming the body of a
 * switch, while, do ... while or for statement shall be a compound statement.
 * 
 * The statement that forms the body of a switch statement or a while, do ...
 * while or for loop, shall be a compound statement (enclosed within braces),
 * even if that compound statement contains a single statement.
 * 
 * 
 * @author stkim
 *
 */
public class Rule14_8_Req extends AbstractMisraCRule {

	public Rule14_8_Req(IASTTranslationUnit ast) {
		super("Rule14_8_Req", false, ast);
		shouldVisitStatements = true;
	}

	@Override
	protected int visit(IASTIfStatement statement) {
		checkViolation(statement);
		return super.visit(statement);
	}

	@Override
	protected int visit(IASTForStatement statement) {
		checkViolation(statement);
		return super.visit(statement);
	}

	@Override
	protected int visit(IASTWhileStatement statement) {
		checkViolation(statement);
		return super.visit(statement);
	}

	@Override
	protected int visit(IASTDoStatement statement) {
		checkViolation(statement);
		return super.visit(statement);
	}
	
	private void checkViolation(IASTNode statement)
	{
		if( !existCompoundStmt(statement))
		{
			String msg1 = MessageFactory.getInstance().getMessage(2212);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msg1, statement));
			
			String msg2 = MessageFactory.getInstance().getMessage(2214);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msg2, statement));
			
			isViolated = true;
		}
	}

	/**
	 * Check if the statement contains the compound statment. 
	 * 
	 * @param statement
	 * @return
	 */
	private boolean existCompoundStmt(IASTNode statement) {
		IASTNode[] internalStatements = statement.getChildren();
		
		boolean existCompoundStmt = false;
		for (IASTNode iastNode : internalStatements) {
			if (iastNode instanceof IASTCompoundStatement) {
				existCompoundStmt = true;
			}
		}
		
		return existCompoundStmt;
	}

}
