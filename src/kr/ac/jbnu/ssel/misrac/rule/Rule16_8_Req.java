package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.c.ICASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.c.ICASTTypedefNameSpecifier;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * All exit paths from a function with non-void return type shall have an
 * explicit return statement with an expression.
 * 
 * This expression gives the value that the function returns. The absence of a
 * return with an expression leads to undefined behaviour (and the compiler may
 * not give an error).
 * 
 * 
 * [STATUS: not statically checkable, partially support]
 * 
 * @author sangjin
 *
 */
public class Rule16_8_Req extends AbstractMisraCRule {

	public Rule16_8_Req(IASTTranslationUnit ast) {
		super("Rule16_8_Req", false, ast);
		shouldVisitStatements = true;
	}

	@Override
	protected int visit(IASTCompoundStatement statement) {
//		boolean isReturnDef = false;
		boolean isReturn=false;
		boolean isReturnId=false;
//		
//		IASTNode funcDef;
//		if(statement.getParent() instanceof IASTFunctionDefinition){
//			funcDef = statement.getParent();
//			IASTNode typeNode = funcDef.getChildren()[0];
//			
//			if(typeNode instanceof ICASTTypedefNameSpecifier){
//				isReturnDef=true;
//			}
//			else if(typeNode instanceof ICASTSimpleDeclSpecifier){
//			}
//			
//			for(IASTNode target: statement.getChildren()){
//				if(target instanceof IASTReturnStatement){
//					IASTNode node = target.getChildren()[0];
//					if(node instanceof IASTIdExpression){
//						isReturnId = true;
//					}
//					isReturn=true;
//				}
//			}
//			
//			if(!isReturn){
//				//
//				System.out.println("1");
//				isViolated = true;
//			}else if((isReturnDef)&&(!isReturnId)){
//				//
//				System.out.println("2");
//				isViolated = true;
//			}
//		}
		
		for(IASTNode node : statement.getChildren()){
			if(node instanceof IASTReturnStatement){
				isReturn = true;
				
				for(IASTNode id: node.getChildren()){
					if(id instanceof IASTIdExpression){
						isReturnId = true;
					}
				}
			}
		}
		
		if((!isReturnId)||(!isReturn)){
			//'return;' found in '%s()', which has been defined with a non-'void' return type.
			String message1 = MessageFactory.getInstance().getMessage(745);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" 
					+ message1 + "--"+statement, statement.getParent()));
			//Function 'main' ends with an implicit 'return' statement.
			String message2 = MessageFactory.getInstance().getMessage(2887);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" 
					+ message2 + "--" +statement, statement.getParent()));
			//This function has been declared with a non-void 'return' type but ends with an implicit 'return ;' statement.
			String message3 = MessageFactory.getInstance().getMessage(2888);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" 
					+ message3 + "--"+statement, statement.getParent()));
			//'return' statement includes no expression but function '%s()' is implicitly of type 'int'.
			String message4 = MessageFactory.getInstance().getMessage(3113);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" 
					+ message4 + "--"+statement, statement.getParent()));
			//Function '%s()' is implicitly of type 'int' but ends without returning a value.
			String message5 = MessageFactory.getInstance().getMessage(3114);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" 
					+ message5 + "--"+statement, statement.getParent()));
			isViolated = true;
		}
		
		return super.visit(statement);
	}
}