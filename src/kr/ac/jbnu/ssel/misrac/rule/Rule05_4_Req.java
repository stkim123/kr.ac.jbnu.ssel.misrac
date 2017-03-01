package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.c.ICASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.c.ICASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTName;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTName;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationLevel;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * A tag name shall be a unique identifier.
 *
 * No tag name shall be reused either to define a different tag or for any other
 * purpose within the program. ISO/IEC 9899:1990 [2] does not define the
 * behaviour when an aggregate declaration uses a tag in different forms of type
 * specifier (struct or union). Either all uses of the tag should be in
 * structure type specifiers, or all uses should be in union type specifiers,
 * For example:
 * 
 * [STATUS: DONE]
 * 
 * @author sangjin
 *
 */
public class Rule05_4_Req extends AbstractMisraCRule {

	private static HashMap<String, String> declarations = new HashMap<>();

	public Rule05_4_Req(IASTTranslationUnit ast) {
		super("Rule05_4_Req", false, ast);
		shouldVisitDeclarations = true;
		declarations.clear();
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		for (IASTNode node : simpleDeclaration.getChildren()) {
			if (node instanceof ICPPASTCompositeTypeSpecifier) {
				String[] splitedStrIdentifier = node.getRawSignature().split(" ");
				String compositeType = splitedStrIdentifier[0];
				String tagName = splitedStrIdentifier[1];
				if (declarations.containsKey(tagName)) {
					//0547 [C] This declaration of tag '%s' conflicts with a previous declaration.  
					String message = MessageFactory.getInstance().getMessage(547);
					violationMsgs.add(new ViolationMessage(this,
							getRuleID() + ":" + message + "--" + node.getRawSignature(), node, ViolationLevel.warning));

					isViolated = true;
					
				} else {
					declarations.put(tagName, compositeType);
				}

			}else if(node instanceof ICPPASTElaboratedTypeSpecifier){
				String[] splitedStrIdentifier = node.getRawSignature().split(" ");
				String compositeType = splitedStrIdentifier[0];
				String tagName = splitedStrIdentifier[1];
				if(declarations.containsKey(tagName)){
					String target = declarations.get(tagName);
					if(!compositeType.equals(target)){
						//0547 [C] This declaration of tag '%s' conflicts with a previous declaration.  
						String message = MessageFactory.getInstance().getMessage(547);
						violationMsgs.add(new ViolationMessage(this,
								getRuleID() + ":" + message + "--" + node.getRawSignature(), node, ViolationLevel.warning));
						isViolated = true;
					}
				}
				
			}
		}
		return super.visit(simpleDeclaration);
	}

}
