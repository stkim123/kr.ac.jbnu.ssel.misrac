package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.c.ICASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.c.ICASTElaboratedTypeSpecifier;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTName;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;

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
 * TODO : parameta�� ������ �� �ִ°� ex) 0�� int �ΰ� double�ΰ�
 * TODO : �������� �����ؼ� �̷��.
 * 
 * @author sangjin
 *
 */
public class Rule05_4_Req extends AbstractMisraCRule {

	private static HashSet<String> declarations = new HashSet<String>();

	public Rule05_4_Req(IASTTranslationUnit ast) {
		super("Rule05_4_Req", false, ast);
		shouldVisitDeclarations = true;
		declarations.clear();
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		for(IASTNode node : simpleDeclaration.getChildren()){
			if(node instanceof ICASTCompositeTypeSpecifier ){
				String specifier = node.getRawSignature().split(" ")[0]+node.getRawSignature().split(" ")[1];
				System.out.println("test :: "+specifier);
				if(declarations.contains(node.getRawSignature().split(" ")[0]+node.getRawSignature().split(" ")[1])){
					isViolated = true;
				}
				else{
					declarations.add(node.getRawSignature().split(" ")[0]+node.getRawSignature().split(" ")[1]);
				}
			}
		}
		return super.visit(simpleDeclaration);
	}

}
