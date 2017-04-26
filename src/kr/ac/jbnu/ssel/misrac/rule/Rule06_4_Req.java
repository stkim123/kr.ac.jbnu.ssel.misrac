package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.c.ICASTCompositeTypeSpecifier;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Bit fields shall only be defined to be of type unsigned int or signed int.
 * 
 * Using int is implementation-defined because bitfields of type int can be either signed or unsigned. The use of enum,
 *  short or char types for bit fields is not allowed because the behaviour is undefined.
 * 
 * [STATUS: DONE]
 * 
 * @author sangjin
 *
 */
public class Rule06_4_Req extends AbstractMisraCRule {

	public Rule06_4_Req(IASTTranslationUnit ast) {
		super("Rule06_4_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitDeclarators = true;
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		
		for(IASTNode structSpec : simpleDeclaration.getChildren()){
			if(structSpec instanceof ICASTCompositeTypeSpecifier){
				for(IASTNode node : structSpec.getChildren()){
					if(node instanceof IASTSimpleDeclaration){
						IASTNode target = node.getChildren()[0];
						String targetString = target.getRawSignature();
						
						if((targetString.contains("signed int"))||(targetString.contains("unsigned int"))){
						}
						else{
							//Bit-fields in this struct/union have not been declared explicitly as unsigned or signed.
							String message = MessageFactory.getInstance().getMessage(634);
							violationMsgs.add(new ViolationMessage(this,
									getRuleID() + ":" + message + "-- " + targetString, target));
							//Bit-fields in this struct/union have been declared with types other than int, signed int or unsigned int.
							String message2 = MessageFactory.getInstance().getMessage(635);
							violationMsgs.add(new ViolationMessage(this,
									getRuleID() + ":" + message2 + "-- " + targetString, target));
							isViolated = true;
						}
					}
				}
			}
		}
		
		return super.visit(simpleDeclaration);
	}

}