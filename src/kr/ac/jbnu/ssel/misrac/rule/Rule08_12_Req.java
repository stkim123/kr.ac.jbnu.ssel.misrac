package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.c.ICASTArrayModifier;
import org.eclipse.cdt.core.dom.ast.c.ICASTSimpleDeclSpecifier;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * When an array is declared with external linkage, its size shall be stated explicitly or defined implicitly by initialisation.
 * 
 * DONE!!
 * 
 * @author sangjin
 *
 */
public class Rule08_12_Req extends AbstractMisraCRule {

	public Rule08_12_Req(IASTTranslationUnit ast) {
		super("Rule08_12_Req", false, ast);
		shouldVisitDeclarations = true;
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		boolean isLiteralExpression = false;
		boolean isInit = false;
		
		IASTNode externCheck = simpleDeclaration.getChildren()[0];
		if(externCheck instanceof ICASTSimpleDeclSpecifier){
			String checkString = externCheck.getRawSignature();
			if(checkString.contains("extern")){
				IASTNode arrDecl = simpleDeclaration.getChildren()[1];
				if(arrDecl instanceof IASTArrayDeclarator){
					for(IASTNode modi : arrDecl.getChildren()){
						if(modi instanceof ICASTArrayModifier){
							for(IASTNode liteExp : modi.getChildren()){
								if(liteExp instanceof IASTLiteralExpression){
									isLiteralExpression = true;
								}
							}
						}
						else if(modi instanceof IASTEqualsInitializer){
							isInit = true;
						}
					}
				}
				if((!isLiteralExpression)&&(!isInit)){
					//Array declared with unknown size.
					String message1 = MessageFactory.getInstance().getMessage(3684);
					violationMsgs.add(new ViolationMessage
							(this, getRuleID() + ":" + message1 + "--" + arrDecl.getRawSignature(), arrDecl));
					isViolated = true;
				}
			}
		}
		
		return super.visit(simpleDeclaration);
	}
}