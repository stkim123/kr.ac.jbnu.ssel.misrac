package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
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
public class Rule08_7_Req extends AbstractMisraCRule {

	private static ArrayList<String> list = new ArrayList<String>();

	public Rule08_7_Req(IASTTranslationUnit ast) {
		super("Rule08_7_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitExpressions = true;
		shouldVisitStatements = true;
		shouldVisitTranslationUnit = true;
		list.clear();
	}

	@Override
	public int visit(IASTTranslationUnit unit) {

		for (IASTNode func : unit.getChildren()) {
			if (func instanceof IASTFunctionDefinition) {
				for (IASTNode comp : func.getChildren()) {
					if (comp instanceof IASTCompoundStatement) {
						for (IASTNode ret : comp.getChildren()) {
							if (ret instanceof IASTReturnStatement) {
								for (IASTNode target : ret.getChildren()) {
									if (target instanceof IASTIdExpression) {

									} else if (target instanceof IASTBinaryExpression) {
										for (int i = 0; i < target.getChildren().length; i++) {

										}
									}
								}
							} else {
								for (IASTNode expression : ret.getChildren()) {
									if (expression instanceof IASTBinaryExpression) {
										for (IASTNode id : expression.getChildren()) {
											list.add(id.getRawSignature());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < list.size(); i++) {
			System.out.println("list data" + i + " is  :: " + list.get(i));
		}

		for (IASTNode simple : unit.getChildren()) {
			if (simple instanceof IASTSimpleDeclaration) {
				int count = 0;
				for (IASTNode dec : simple.getChildren()) {
					if (dec instanceof IASTDeclarator) {
						for (IASTNode name : dec.getChildren()) {
							if (name instanceof IASTName) {
								for (int i = 0; i < list.size(); i++) {
									if (list.get(i).equals(name.toString())) {
										count++;
										System.out.println("data String is :: " + list.get(i) + "  simpleDecl is ::"
												+ name.toString());
									}
								}
							}
						}
					}
				}
				if (count < 2) {
					// The object '%1s' is only referenced by function '%2s', in
					// the translation unit where it is definedd
					String msg = MessageFactory.getInstance().getMessage(1514);
					violationMsgs.add(new ViolationMessage(this,
							getRuleID() + ":" + msg + "(" + simple.hashCode() + ")", simple));
					// File scope static, '%s', is only accessed in one
					// function.
					String msg2 = MessageFactory.getInstance().getMessage(3218);
					violationMsgs.add(new ViolationMessage(this,
							getRuleID() + ":" + msg2 + "(" + simple.hashCode() + ")", simple));
					isViolated = true;
				}
			}
		}

		return super.visit(unit);
	}

}