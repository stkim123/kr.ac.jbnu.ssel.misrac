package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTName;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;

/**
 * A tag name shall be a unique identifier.
 *
 * No tag name shall be reused either to define a different tag or for any other purpose within the program. 
 * ISO/IEC 9899:1990 [2] does not define the behaviour when an aggregate declaration uses a tag in different forms of type specifier (struct or union). 
 * Either all uses of the tag should be in structure type specifiers, or all uses should be in union type specifiers, For example:
 * 
 * TODO
 * 
 * @author sangjin
 *
 */
public class Rule05_4_Req extends AbstractMisraCRule {

	private static HashSet<String> declarations = new HashSet<String>();

	public Rule05_4_Req(IASTTranslationUnit ast) {
		super("Rule05_4_Req", false, ast);
		shouldVisitDeclarations = true;
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		IASTDeclSpecifier specifier = simpleDeclaration.getDeclSpecifier();
//		String test = simpleDeclaration.getDeclarators()[0].getRawSignature();
//		System.out.println(simpleDeclaration.getRawSignature().toString());
//		System.out.println("test :: "+test);
		if (specifier != null)
			for (IASTNode node : specifier.getChildren()) {
				if (node instanceof CASTName) {
					CASTName tagName = (CASTName) node;
//					System.out.println(node.toString());
				}

			 }
			return super.visit(simpleDeclaration);
	}

}
