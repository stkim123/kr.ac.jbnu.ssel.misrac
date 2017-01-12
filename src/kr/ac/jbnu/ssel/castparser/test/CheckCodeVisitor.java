package kr.ac.jbnu.ssel.castparser.test;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.internal.core.dom.parser.ASTAmbiguousNode;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTConditionalExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionCallExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDeclarator;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTFunctionDefinition;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTIdExpression;
import org.eclipse.cdt.internal.core.dom.parser.c.CASTSimpleDeclSpecifier;

public class CheckCodeVisitor extends ASTVisitor {
	CheckCodeVisitor() {

		super(true);	// for visiting all of the followings. If don't want to visit all, just decomment the followings. 
		
//		shouldVisitDeclarations= true;
//		shouldVisitInitializers= true;
//		shouldVisitParameterDeclarations= true;
//		shouldVisitDeclarators= true;
//		shouldVisitDeclSpecifiers= true;
//		shouldVisitArrayModifiers= true;
//		shouldVisitPointerOperators= true;
//		shouldVisitAttributes= true;
//		shouldVisitTokens= true;
//		shouldVisitExpressions= true;
//		shouldVisitStatements= true;
//		shouldVisitTypeIds= true;
//		shouldVisitEnumerators= true;
//		shouldVisitTranslationUnit= true;
//		shouldVisitProblems= true;
//		shouldVisitDesignators= true;
//		shouldVisitBaseSpecifiers= true;
//		shouldVisitNamespaces= true;
//		shouldVisitTemplateParameters= true;
//		shouldVisitCaptures= true;
//		shouldVisitVirtSpecifiers= true;
//		shouldVisitDecltypeSpecifiers= true;
//		includeInactiveNodes= true;
//		shouldVisitAmbiguousNodes= true;               
//		shouldVisitImplicitNames= true;               
//		shouldVisitImplicitNameAlternates= true;               
//		shouldVisitImplicitDestructorNames= true;		
	}
	
	@Override
	public int visit(ASTAmbiguousNode astAmbiguousNode) {
		
		System.out.println("ASTAmbiguousNode:"+ astAmbiguousNode.toString());
		
		return PROCESS_CONTINUE;
	}
	
	
	@Override
	public int visit(IASTDeclaration declaration) {
		System.out.println("IASTDeclaration;"+ declaration.toString());
		if( declaration instanceof CASTFunctionDefinition)
		{
			CASTFunctionDefinition funcDeclaration = ((CASTFunctionDefinition)declaration);
			CASTFunctionDeclarator declarator = (CASTFunctionDeclarator)funcDeclaration.getDeclarator();
			System.out.println("function name:"+ declarator.getName().toString());
			IASTParameterDeclaration[] params = declarator.getParameters();
			if( params != null && params.length != 0)
			{
				for (IASTParameterDeclaration iastParameterDeclaration : params) {
					CASTDeclarator paramName = (CASTDeclarator)iastParameterDeclaration.getDeclarator();
					CASTSimpleDeclSpecifier paramType = (CASTSimpleDeclSpecifier)iastParameterDeclaration.getDeclSpecifier();

					System.out.println("IASTParameterDeclaration: name - "+ paramName.getName().toString() + ", type:"+ paramType.toString());
					System.out.println();
				}
			}
			funcDeclaration.getBody();
			
		}
		return super.visit(declaration);
	}
	
	@Override
	public int visit(IASTStatement statement) {
		System.out.println("IASTStatement;"+ statement.toString());
		return PROCESS_CONTINUE;
	}
	
	public int visit(IASTExpression expression) {
		System.out.println("IASTExpression;"+ expression.toString());
		if( expression instanceof CASTFunctionCallExpression)
		{
			CASTFunctionCallExpression cfce = (CASTFunctionCallExpression)expression;
			System.out.println("CASTFunctionCallExpression;"+ cfce.toString());
		}
		else if( expression instanceof CASTIdExpression)
		{
			CASTIdExpression cie = (CASTIdExpression)expression;
			System.out.println("CASTIdExpression;"+ cie.toString());
		}
		else if( expression instanceof CASTConditionalExpression)
		{
			CASTConditionalExpression cce = (CASTConditionalExpression)expression;
			System.out.println("CASTConditionalExpression;"+ cce.toString());
		}
			
		return PROCESS_CONTINUE;
	}
}
