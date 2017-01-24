package kr.ac.jbnu.ssel.castparser.test;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTArrayModifier;
import org.eclipse.cdt.core.dom.ast.IASTArraySubscriptExpression;
import org.eclipse.cdt.core.dom.ast.IASTAttribute;
import org.eclipse.cdt.core.dom.ast.IASTAttributeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTBinaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTBinaryTypeIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTBreakStatement;
import org.eclipse.cdt.core.dom.ast.IASTCaseStatement;
import org.eclipse.cdt.core.dom.ast.IASTCastExpression;
import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTCompositeTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTCompoundStatement;
import org.eclipse.cdt.core.dom.ast.IASTConditionalExpression;
import org.eclipse.cdt.core.dom.ast.IASTContinueStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFieldDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFieldReference;
import org.eclipse.cdt.core.dom.ast.IASTForStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTGotoStatement;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTInitializerList;
import org.eclipse.cdt.core.dom.ast.IASTLabelStatement;
import org.eclipse.cdt.core.dom.ast.IASTLiteralExpression;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNamedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTNullStatement;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPointerOperator;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorElifStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorEndifStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorFunctionStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfdefStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIfndefStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTProblemExpression;
import org.eclipse.cdt.core.dom.ast.IASTProblemStatement;
import org.eclipse.cdt.core.dom.ast.IASTReturnStatement;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStandardFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTToken;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.IASTTypeIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTTypeIdInitializerExpression;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

import kr.ac.jbnu.ssel.castparser.DetailedASTVisitor;

public class MyDetailedCodeVisitor extends DetailedASTVisitor {
	
	public MyDetailedCodeVisitor() {
		super();
	}
	
	public MyDetailedCodeVisitor(boolean visitNodes, IASTTranslationUnit ast) {
		super(visitNodes,ast);
	}
	
	protected int visit(IASTComment comment ) {
		System.out.println("IASTComment:" + comment);
		System.out.println("IASTComment:" + comment.getClass().getName());
		return ASTVisitor.PROCESS_CONTINUE;
	}
	
	protected int visit(IASTPreprocessorIncludeStatement statement) {
		System.out.println("IASTPreprocessorIncludeStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTPreprocessorFunctionStyleMacroDefinition statement) {
		System.out.println("IASTPreprocessorFunctionStyleMacroDefinition:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	private int visit(IASTPreprocessorMacroDefinition pstatement) {
		System.out.println("IASTPreprocessorMacroDefinition:" + pstatement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	private int visit(IASTPreprocessorEndifStatement pstatement) {
		System.out.println("IASTPreprocessorEndifStatement:" + pstatement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	private int visit(IASTPreprocessorElifStatement pstatement) {
		System.out.println("IASTPreprocessorElifStatement:" + pstatement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	private int visit(IASTPreprocessorIfStatement pstatement) {
		System.out.println("IASTPreprocessorIfStatement:" + pstatement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	private int visit(IASTPreprocessorIfndefStatement pstatement) {
		System.out.println("IASTPreprocessorIfndefStatement:" + pstatement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	private int visit(IASTPreprocessorIfdefStatement pstatement) {
		System.out.println("IASTPreprocessorIfdefStatement:" + pstatement);
		return ASTVisitor.PROCESS_CONTINUE;
	}
	
	@Override
	public int visit(IASTName name) {
		System.out.println("MyDetailedCodeVisitor:IASTParameterDeclaration:" + name);
		return super.visit(name);
		
	}

	public int visit(IASTParameterDeclaration paramDeclaration) {
		System.out.println("MyDetailedCodeVisitor:IASTParameterDeclaration:" + paramDeclaration);
		return super.visit(paramDeclaration);
	}

	public int visit(IASTArrayModifier arrayModifier) {
		System.out.println("MyDetailedCodeVisitor:IASTArrayModifier:" + arrayModifier);
		return super.visit(arrayModifier);
	}

	public int visit(IASTPointerOperator pointOperator) {
		System.out.println("MyDetailedCodeVisitor:IASTPointerOperator:" + pointOperator);
		return super.visit(pointOperator);
	}

	public int visit(IASTAttribute attribute) {
		System.out.println("MyDetailedCodeVisitor:IASTAttribute:" + attribute);
		return super.visit(attribute);
	}

	public int visit(IASTAttributeSpecifier attributeSpecifier) {
		System.out.println("MyDetailedCodeVisitor:IASTAttributeSpecifier:" + attributeSpecifier);
		return super.visit(attributeSpecifier);
	}

	public int visit(IASTToken token) {
		System.out.println("MyDetailedCodeVisitor:IASTToken:" + token);
		return super.visit(token);
	}

	public int visit(IASTTypeId typeId) {
		System.out.println("MyDetailedCodeVisitor:IASTTypeId:" + typeId);
		return super.visit(typeId);
	}

	public int visit(IASTEnumerator enumerator) {
		System.out.println("MyDetailedCodeVisitor:IASTEnumerator:" + enumerator);
		return super.visit(enumerator);
	}

	public int visit(IASTProblem problem) {
		System.out.println("MyDetailedCodeVisitor:IASTProblem:" + problem);
		return super.visit(problem);
	}
	
	
	protected int visit(IASTFunctionDefinition functionDefinition) {
		String functionName = functionDefinition.getDeclarator().getName().toString(); 
		
		System.out.println("IASTFunctionDefinition:" + functionDefinition  +":"+ functionName);
		
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		System.out.println("IASTSimpleDeclaration:" + simpleDeclaration);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTEqualsInitializer initializer) {
		System.out.println("IASTEqualsInitializer:" + initializer);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTInitializerList initializer) {
		System.out.println("IASTInitializerList:" + initializer);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTArrayDeclarator declarator) {
		System.out.println("IASTArrayDeclarator:" + declarator);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTFieldDeclarator declarator) {
		System.out.println("IASTFieldDeclarator:" + declarator);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTFunctionDeclarator declarator) {
		System.out.println("IASTFunctionDeclarator:" + declarator);
		return ASTVisitor.PROCESS_CONTINUE;
	}
	
	protected int visit(IASTStandardFunctionDeclarator declarator) {
		System.out.println("IASTStandardFunctionDeclarator:" + declarator);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTCompositeTypeSpecifier declSpec) {
		System.out.println("IASTCompositeTypeSpecifier:" + declSpec);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTElaboratedTypeSpecifier declSpec) {
		System.out.println("IASTElaboratedTypeSpecifier:" + declSpec);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTEnumerationSpecifier declSpec) {
		System.out.println("IASTEnumerationSpecifier:" + declSpec);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTNamedTypeSpecifier declSpec) {
		System.out.println("IASTNamedTypeSpecifier:" + declSpec);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTSimpleDeclSpecifier declSpec) {
		System.out.println("IASTSimpleDeclSpecifier:" + declSpec);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	//////////////////////////////////////////////////////////////////
	// for IASTExpression
	protected int visit(IASTArraySubscriptExpression expression) {
		System.out.println("IASTArraySubscriptExpression:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTBinaryExpression expression) {
		System.out.println("IASTBinaryExpression:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTBinaryTypeIdExpression expression) {
		System.out.println("IASTBinaryTypeIdExpression:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTCastExpression expression) {
		System.out.println("IASTCastExpression:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTConditionalExpression expression) {
		System.out.println("IASTConditionalExpression:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTFieldReference expression) {
		System.out.println("IASTFieldReference:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTFunctionCallExpression expression) {
		System.out.println("IASTFunctionCallExpression:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTIdExpression expression) {
		System.out.println("IASTIdExpression:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTLiteralExpression expression) {
		System.out.println("IASTLiteralExpression:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTProblemExpression expression) {
		System.out.println("IASTProblemExpression:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTTypeIdExpression expression) {
		System.out.println("IASTTypeIdExpression:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTTypeIdInitializerExpression expression) {
		System.out.println("IASTTypeIdInitializerExpression:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTUnaryExpression expression) {
		System.out.println("IASTUnaryExpression:" + expression);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	//////////////////////////////////////////////////////////////////
	// for IASTStatement
	protected int visit(IASTBreakStatement statement) {
		System.out.println("IASTBreakStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTCaseStatement statement) {
		System.out.println("IASTCaseStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTCompoundStatement statement) {
		System.out.println("IASTCompoundStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTContinueStatement statement) {
		System.out.println("IASTContinueStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTDeclarationStatement statement) {
		System.out.println("IASTDeclarationStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTDefaultStatement statement) {
		System.out.println("IASTDefaultStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTDoStatement statement) {
		System.out.println("IASTDoStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTExpressionStatement statement) {
		System.out.println("IASTExpressionStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTForStatement statement) {
		System.out.println("IASTForStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTGotoStatement statement) {
		System.out.println("IASTGotoStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTLabelStatement statement) {
		System.out.println("IASTLabelStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTIfStatement statement) {
		System.out.println("IASTIfStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTNullStatement statement) {
		System.out.println("IASTNullStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTProblemStatement statement) {
		System.out.println("IASTProblemStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTReturnStatement statement) {
		System.out.println("IASTReturnStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTSwitchStatement statement) {
		System.out.println("IASTSwitchStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}

	protected int visit(IASTWhileStatement statement) {
		System.out.println("IASTWhileStatement:" + statement);
		return ASTVisitor.PROCESS_CONTINUE;
	}
}
