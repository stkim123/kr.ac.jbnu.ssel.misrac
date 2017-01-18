package kr.ac.jbnu.ssel.misrac.rule;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 18.04: (Required) Unions shall not be used.
 * 
 * Rule prohibits the reuse of memory areas for unrelated purposes. However,
 * even when memory is being reused for related purposes, there is still a risk
 * that the data may be misinterpreted. Therefore, this rule prohibits the use
 * of unions for any purpose. It is recognised nonetheless that there are
 * situations in which the careful use of unions is desirable in constructing an
 * efficient implementation. In such situations, deviations to this rule are
 * considered acceptable provided that all relevant implementation-defined
 * behaviour is documented. This might be achieved in practice by referencing
 * the implementation section of the compiler manuals from the design
 * documentation. The kinds of implementation behaviour that might be relevant
 * are:
 * 
 * padding - how much padding is inserted at the end of the union
 * 
 * alignment - how are members of any structures within the union aligned
 * 
 * endianness - is the most significant byte of a word stored at the lowest or
 * highest memory address
 * 
 * bit-order - how are bits numbered within bytes and how are bits allocated to
 * bit fields
 * 
 * The use of deviations is acceptable for (a) packing and unpacking of data,
 * for example when sending and receiving messages, and (b) implementing variant
 * records provided that the variants are differentiated by a common field.
 * Variant records without a differentiator are not considered suitable for use
 * in any situation.
 *
 * 
 * DONE!! - Ruled이해 부족
 * 
 * @author kang
 */

public class Rule18_4_Req extends AbstractMisraCRule {

	private static final String UNION = "union";

	public Rule18_4_Req(IASTTranslationUnit ast) {
		super("Rule18_4_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitStatements = true;
	}

	protected int visit(IASTSimpleDeclaration declaration) {

		String[] splitArray = declaration.getRawSignature().split(" ");

				
		if(UNION.equals(splitArray[0])){
			isViolated = true;
		
			String message1 = MessageFactory.getInstance().getMessage(750);
			violationMsgs.add(new ViolationMessage(this,getRuleID() + ":" + message1 + "-- " +declaration, declaration));
			System.out.println("IASTSimpleDeclaration : " + splitArray[0]);	
			
			String message2 = MessageFactory.getInstance().getMessage(759);
			violationMsgs.add(new ViolationMessage(this,getRuleID() + ":" + message2 + "-- " +declaration, declaration));
			System.out.println("IASTSimpleDeclaration : " + splitArray[0]);	
			}
		
		
		
		return super.visit(declaration);
	}

}