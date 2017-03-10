package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * A typedef name shall be a unique identifier.
 *
 * No typedef name shall be reused either as a typedef name or for any other purpose.
 * 
 * [STATUS: DONE]
 * 
 * @author sangjin
 *
 */
public class Rule05_3_Req extends AbstractMisraCRule {
	private static HashSet<String> declarations = new HashSet<String>();

	public Rule05_3_Req(IASTTranslationUnit ast) {
		super("Rule05_3_Req", false, ast);
		shouldVisitDeclarations = true;
		declarations.clear();
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		
		String rawString= simpleDeclaration.getRawSignature();
		String typedefString = rawString.split(" ")[0];
		
		
		
		for (IASTNode node : simpleDeclaration.getDeclarators()) {
			String identifier = node.getRawSignature();

			if (declarations.contains(identifier)) {
//				The identifier '%1s' is declared as a typedef and is used elsewhere for a different kind of declaration.
				String message1 = MessageFactory.getInstance().getMessage(1506);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message1 + "--" + simpleDeclaration.getRawSignature(), node));
//				'%1s' is used as a typedef for different types.
				String message2 = MessageFactory.getInstance().getMessage(1507);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message2 + "--" + simpleDeclaration.getRawSignature(), node));
//				The typedef '%1s' is declared in more than one location.
				String message3 = MessageFactory.getInstance().getMessage(1508);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message3 + "--" + simpleDeclaration.getRawSignature(), node));
//				Declaration of typedef '%s' is not in a header file although it is used in a definition or declaration with external linkage.
				String message4 = MessageFactory.getInstance().getMessage(3448);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message4 + "--" + simpleDeclaration.getRawSignature(), node));

				isViolated = true;
			} else {
				if(typedefString.equals("typedef")){
					declarations.add(identifier);
				}
				
			}
		}
		return super.visit(simpleDeclaration);
	}
}
