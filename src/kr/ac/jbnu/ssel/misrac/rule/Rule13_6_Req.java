package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Numeric variables being used within a for loop for iteration counting shall
 * not be modified in the body of the loop.
 * 
 * Loop counters shall not be modified in the body of the loop. However other
 * loop control variables representing logical values may be modified in the
 * loop, for example a flag to indicate that something has been completed, which
 * is then tested in the for statement.
 * 
 * 
 * 
 * @author sangjin
 */

public class Rule13_6_Req extends AbstractMisraCRule {

	private static IASTIdExpression expressionForLoop;

	public Rule13_6_Req(IASTTranslationUnit ast) {
		super("Rule13_6_Req", false, ast);
		shouldVisitStatements = true;
	}

	@Override
	protected int visit(IASTForStatement statement) {
		
		for(IASTNode expressionNode : statement.getChildren()){
			if(expressionNode instanceof IASTUnaryExpression){
				IASTUnaryExpression unary = (IASTUnaryExpression)expressionNode;
				for(IASTNode idExpression : unary.getChildren()){
					if(idExpression instanceof IASTIdExpression){
						IASTIdExpression saveTarget = (IASTIdExpression)idExpression;
						expressionForLoop = saveTarget;
					}
				}
			}
			
			if(expressionNode instanceof IASTCompoundStatement){
				IASTCompoundStatement compound = (IASTCompoundStatement)expressionNode;
				for(IASTNode target : compound.getChildren()){
					if(target instanceof IASTExpressionStatement){
						IASTExpressionStatement expressionStatement = (IASTExpressionStatement)target;
						for(IASTNode binary : expressionStatement.getChildren()){
							if(binary instanceof IASTBinaryExpression){
								IASTBinaryExpression binaryExpression = (IASTBinaryExpression)binary;
								
								for(IASTNode idNode : binaryExpression.getChildren()){
									if(idNode instanceof IASTIdExpression){
										IASTIdExpression id = (IASTIdExpression)idNode;
										
										if(id.getRawSignature().equals(expressionForLoop.getRawSignature())){
											//Loop control variable in this 'for' statement, %s, is modified in the body of the loop.
											String message = MessageFactory.getInstance().getMessage(2469);
											violationMsgs.add(new ViolationMessage(this,
													getRuleID() + ":" + message + "--" + target.getRawSignature(), target));
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

		return super.visit(statement);
	}
}