package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Whenever an object or function is declared or defined, its type shall be explicitly stated.
 * 
 * TODO
 * 
 * @author sangjin
 *
 */
public class Rule08_2_req extends AbstractMisraCRule {

	private static ArrayList<String> list = new ArrayList<String>();
	
	public Rule08_2_req(IASTTranslationUnit ast) {
		super("Rule08_2_req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitDeclarators = true;
		shouldVisitComment = false;
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		
		//TODO
		
		/* messages are 
		 * The 'int' type specifier has been omitted from a function declaration.
		 * he 'int' type specifier has been omitted from an object declaration.
		 * */
		return super.visit(simpleDeclaration);
	}
}
