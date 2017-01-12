package kr.ac.jbnu.ssel.misrac.rule;

import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTName;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;

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
