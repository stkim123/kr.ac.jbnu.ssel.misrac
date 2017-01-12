package kr.ac.jbnu.ssel.castparser;

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
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTDefaultStatement;
import org.eclipse.cdt.core.dom.ast.IASTDoStatement;
import org.eclipse.cdt.core.dom.ast.IASTElaboratedTypeSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.IASTEqualsInitializer;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
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
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
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
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTSwitchStatement;
import org.eclipse.cdt.core.dom.ast.IASTToken;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.IASTTypeIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTTypeIdInitializerExpression;
import org.eclipse.cdt.core.dom.ast.IASTUnaryExpression;
import org.eclipse.cdt.core.dom.ast.IASTWhileStatement;

public class DetailedASTVisitor extends ASTVisitor {

    protected boolean shouldVisitPreprocessor = false;
    protected boolean shouldVisitComment = false;
    
    private IASTTranslationUnit ast;

    public DetailedASTVisitor() {
    }

    public DetailedASTVisitor(boolean visitNodes, IASTTranslationUnit ast) {
	super(visitNodes);
	this.ast = ast;
	// the following two methods should be called manually from the outside(e.g., AbstractMisraCRule).
	// extractPreprocessorNodes(ast);
	// extractComments(ast);
    }

    protected void extractComments() {
//	if( !shouldVisitComment) return;
	
	IASTComment[] comments = ast.getComments();
	if (comments != null && comments.length != 0) {
	    for (IASTComment comment : comments) {
		visit(comment);
	    }
	}

    }

    protected void extractPreprocessorNodes() {
//	if( !shouldVisitPreprocessor) return;
	
	IASTPreprocessorStatement[] statements = ast.getAllPreprocessorStatements();
	if (statements != null && statements.length != 0) {
	    for (IASTPreprocessorStatement pstatement : statements) {
		if (pstatement instanceof IASTPreprocessorIncludeStatement) {
		    visit((IASTPreprocessorIncludeStatement) pstatement);
		} else if (pstatement instanceof IASTPreprocessorFunctionStyleMacroDefinition) {
		    visit((IASTPreprocessorFunctionStyleMacroDefinition) pstatement);
		} else if (pstatement instanceof IASTPreprocessorIfdefStatement) {
		    visit((IASTPreprocessorIfdefStatement) pstatement);
		} else if (pstatement instanceof IASTPreprocessorIfndefStatement) {
		    visit((IASTPreprocessorIfndefStatement) pstatement);
		} else if (pstatement instanceof IASTPreprocessorIfStatement) {
		    visit((IASTPreprocessorIfStatement) pstatement);
		} else if (pstatement instanceof IASTPreprocessorElifStatement) {
		    visit((IASTPreprocessorElifStatement) pstatement);
		} else if (pstatement instanceof IASTPreprocessorEndifStatement) {
		    visit((IASTPreprocessorEndifStatement) pstatement);
		} else if (pstatement instanceof IASTPreprocessorMacroDefinition) {
		    visit((IASTPreprocessorMacroDefinition) pstatement);
		}
	    }
	}
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // Methods for Comments
    protected int visit(IASTComment comment) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // Methods for Preprocessors
    protected int visit(IASTPreprocessorIncludeStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTPreprocessorFunctionStyleMacroDefinition statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    private int visit(IASTPreprocessorMacroDefinition pstatement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    private int visit(IASTPreprocessorEndifStatement pstatement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    private int visit(IASTPreprocessorElifStatement pstatement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    private int visit(IASTPreprocessorIfStatement pstatement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    private int visit(IASTPreprocessorIfndefStatement pstatement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    private int visit(IASTPreprocessorIfdefStatement pstatement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // AST Visitor's Method
    public int visit(IASTName name) {
	return super.visit(name);
    }

    public final int visit(IASTDeclaration declaration) {
	if (declaration instanceof IASTFunctionDefinition) {
	    return visit((IASTFunctionDefinition) declaration);
	} else if (declaration instanceof IASTSimpleDeclaration) {
	    return visit((IASTSimpleDeclaration) declaration);
	}

	return super.visit(declaration);
    }

    public final int visit(IASTInitializer initializer) {
	if (initializer instanceof IASTEqualsInitializer) {
	    return visit((IASTEqualsInitializer) initializer);
	} else if (initializer instanceof IASTInitializerList) {
	    return visit((IASTInitializerList) initializer);
	}

	return super.visit(initializer);
    }

    public int visit(IASTParameterDeclaration paramDeclaration) {
	return super.visit(paramDeclaration);
    }

    public final int visit(IASTDeclarator declarator) {
	if (declarator instanceof IASTArrayDeclarator) {
	    return visit((IASTArrayDeclarator) declarator);
	} else if (declarator instanceof IASTFieldDeclarator) {
	    return visit((IASTFieldDeclarator) declarator);
	} else if (declarator instanceof IASTFunctionDeclarator) {
	    return visit((IASTFunctionDeclarator) declarator);
	}

	return super.visit(declarator);
    }

    public final int visit(IASTDeclSpecifier declSpec) {

	if (declSpec instanceof IASTCompositeTypeSpecifier) {
	    return visit((IASTCompositeTypeSpecifier) declSpec);
	} else if (declSpec instanceof IASTElaboratedTypeSpecifier) {
	    return visit((IASTElaboratedTypeSpecifier) declSpec);
	} else if (declSpec instanceof IASTEnumerationSpecifier) {
	    return visit((IASTEnumerationSpecifier) declSpec);
	} else if (declSpec instanceof IASTNamedTypeSpecifier) {
	    return visit((IASTNamedTypeSpecifier) declSpec);
	} else if (declSpec instanceof IASTSimpleDeclSpecifier) {
	    return visit((IASTSimpleDeclSpecifier) declSpec);
	}

	return super.visit(declSpec);
    }

    public int visit(IASTArrayModifier arrayModifier) {
	return super.visit(arrayModifier);
    }

    public int visit(IASTPointerOperator pointOperator) {
	return super.visit(pointOperator);
    }

    public int visit(IASTAttribute attribute) {
	return super.visit(attribute);
    }

    public int visit(IASTAttributeSpecifier attributeSpecifier) {
	return super.visit(attributeSpecifier);
    }

    public int visit(IASTToken token) {
	return super.visit(token);
    }

    public final int visit(IASTExpression expression) {

	if (expression instanceof IASTArraySubscriptExpression) {
	    return visit((IASTArraySubscriptExpression) expression);
	} else if (expression instanceof IASTBinaryExpression) {
	    return visit((IASTBinaryExpression) expression);
	} else if (expression instanceof IASTBinaryTypeIdExpression) {
	    return visit((IASTBinaryTypeIdExpression) expression);
	} else if (expression instanceof IASTCastExpression) {
	    return visit((IASTCastExpression) expression);
	} else if (expression instanceof IASTConditionalExpression) {
	    return visit((IASTConditionalExpression) expression);
	} else if (expression instanceof IASTFieldReference) {
	    return visit((IASTFieldReference) expression);
	} else if (expression instanceof IASTFunctionCallExpression) {
	    return visit((IASTFunctionCallExpression) expression);
	} else if (expression instanceof IASTIdExpression) {
	    return visit((IASTIdExpression) expression);
	} else if (expression instanceof IASTLiteralExpression) {
	    return visit((IASTLiteralExpression) expression);
	} else if (expression instanceof IASTProblemExpression) {
	    return visit((IASTProblemExpression) expression);
	} else if (expression instanceof IASTTypeIdExpression) {
	    return visit((IASTTypeIdExpression) expression);
	} else if (expression instanceof IASTTypeIdInitializerExpression) {
	    return visit((IASTTypeIdInitializerExpression) expression);
	} else if (expression instanceof IASTUnaryExpression) {
	    return visit((IASTUnaryExpression) expression);
	}

	return super.visit(expression);
    }

    public final int visit(IASTStatement statement) {
	if (statement instanceof IASTBreakStatement) {
	    return visit((IASTBreakStatement) statement);
	} else if (statement instanceof IASTCaseStatement) {
	    return visit((IASTCaseStatement) statement);
	} else if (statement instanceof IASTCompoundStatement) {
	    return visit((IASTCompoundStatement) statement);
	} else if (statement instanceof IASTContinueStatement) {
	    return visit((IASTContinueStatement) statement);
	} else if (statement instanceof IASTDeclarationStatement) {
	    return visit((IASTDeclarationStatement) statement);
	} else if (statement instanceof IASTDefaultStatement) {
	    return visit((IASTDefaultStatement) statement);
	} else if (statement instanceof IASTDoStatement) {
	    return visit((IASTDoStatement) statement);
	} else if (statement instanceof IASTExpressionStatement) {
	    return visit((IASTExpressionStatement) statement);
	} else if (statement instanceof IASTForStatement) {
	    return visit((IASTForStatement) statement);
	} else if (statement instanceof IASTGotoStatement) {
	    return visit((IASTGotoStatement) statement);
	} else if (statement instanceof IASTIfStatement) {
	    return visit((IASTIfStatement) statement);
	} else if (statement instanceof IASTLabelStatement) {
	    return visit((IASTLabelStatement) statement);
	} else if (statement instanceof IASTNullStatement) {
	    return visit((IASTNullStatement) statement);
	} else if (statement instanceof IASTProblemStatement) {
	    return visit((IASTProblemStatement) statement);
	} else if (statement instanceof IASTReturnStatement) {
	    return visit((IASTReturnStatement) statement);
	} else if (statement instanceof IASTSwitchStatement) {
	    return visit((IASTSwitchStatement) statement);
	} else if (statement instanceof IASTWhileStatement) {
	    return visit((IASTWhileStatement) statement);
	}

	return super.visit(statement);
    }

    public int visit(IASTTypeId typeId) {
	return super.visit(typeId);
    }

    public int visit(IASTEnumerator enumerator) {
	return super.visit(enumerator);
    }

    public int visit(IASTProblem problem) {
	return super.visit(problem);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // methods for overriding
    protected int visit(IASTFunctionDefinition functionDefinition) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTSimpleDeclaration simpleDeclaration) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTEqualsInitializer initializer) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTInitializerList initializer) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTArrayDeclarator declarator) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTFieldDeclarator declarator) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTFunctionDeclarator declarator) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTCompositeTypeSpecifier declSpec) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTElaboratedTypeSpecifier declSpec) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTEnumerationSpecifier declSpec) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTNamedTypeSpecifier declSpec) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTSimpleDeclSpecifier declSpec) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    //////////////////////////////////////////////////////////////////
    // for IASTExpression
    protected int visit(IASTArraySubscriptExpression expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTBinaryExpression expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTBinaryTypeIdExpression expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTCastExpression expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTConditionalExpression expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTFieldReference expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTFunctionCallExpression expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTIdExpression expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTLiteralExpression expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTProblemExpression expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTTypeIdExpression expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTTypeIdInitializerExpression expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTUnaryExpression expression) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    //////////////////////////////////////////////////////////////////
    // for IASTStatement
    protected int visit(IASTBreakStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTCaseStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTCompoundStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTContinueStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTDeclarationStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTDefaultStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTDoStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTExpressionStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTForStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTGotoStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTLabelStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTIfStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTNullStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTProblemStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTReturnStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTSwitchStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }

    protected int visit(IASTWhileStatement statement) {
	return ASTVisitor.PROCESS_CONTINUE;
    }
}
