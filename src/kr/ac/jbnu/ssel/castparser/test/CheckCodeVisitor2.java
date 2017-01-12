package kr.ac.jbnu.ssel.castparser.test;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.IASTArrayDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTArrayModifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.IASTEnumerator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTInitializer;
import org.eclipse.cdt.core.dom.ast.IASTName;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTPointerOperator;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorStatement;
import org.eclipse.cdt.core.dom.ast.IASTProblem;
import org.eclipse.cdt.core.dom.ast.IASTProblemHolder;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.c.ICASTDesignator;
import org.eclipse.cdt.core.parser.util.ArrayUtil;
import org.eclipse.cdt.internal.core.dom.parser.ASTNode;
import org.eclipse.core.runtime.IProgressMonitor;

import kr.ac.jbnu.ssel.castparser.CASTUtil;
import kr.ac.jbnu.ssel.castparser.DOMASTNodeLeaf;
import kr.ac.jbnu.ssel.castparser.DOMASTNodeParent;

public class CheckCodeVisitor2 extends ASTVisitor {
	private static final int INITIAL_PROBLEM_SIZE = 4;
	{
		shouldVisitNames = true;
		shouldVisitDeclarations = true;
		shouldVisitInitializers = true;
		shouldVisitParameterDeclarations = true;
		shouldVisitDeclarators = true;
		shouldVisitDeclSpecifiers = true;
		shouldVisitDesignators = true;
		shouldVisitExpressions = true;
		shouldVisitStatements = true;
		shouldVisitTypeIds = true;
		shouldVisitEnumerators = true;
	}

	DOMASTNodeParent root = null;
	IProgressMonitor monitor = null;
	IASTProblem[] astProblems = new IASTProblem[INITIAL_PROBLEM_SIZE];

	public CheckCodeVisitor2(IASTTranslationUnit tu, IProgressMonitor monitor) {
		root = new DOMASTNodeParent(tu);
		this.monitor = monitor;
	}

	private class DOMASTNodeLeafContinue extends DOMASTNodeLeaf {
		public DOMASTNodeLeafContinue(IASTNode node) {
			super(node);
		}
	}

	/**
	 * return null if the algorithm should stop (monitor was cancelled) return
	 * DOMASTNodeLeafContinue if the algorithm should continue but no valid
	 * DOMASTNodeLeaf was added (i.e. node was null return the DOMASTNodeLeaf
	 * added to the DOM AST View's model otherwise
	 * 
	 * @param node
	 * @return
	 */
	private DOMASTNodeLeaf addRoot(IASTNode node) {
		if (monitor != null && monitor.isCanceled())
			return null;
		if (node == null)
			return new DOMASTNodeLeafContinue(null);

		// only do length check for ASTNode (getNodeLocations on
		// PreprocessorStatements is very expensive)
		if (node instanceof ASTNode && ((ASTNode) node).getLength() <= 0)
			return new DOMASTNodeLeafContinue(null);

		DOMASTNodeParent parent = null;

		// if it's a preprocessor statement being merged then do a special
		// search for parent (no search)
		if (node instanceof IASTPreprocessorStatement) {
			parent = root;
		} else {
			IASTNode tempParent = node.getParent();
			if (tempParent instanceof IASTPreprocessorStatement) {
				parent = root.findTreeParentForMergedNode(node);
			} else {
				parent = root.findTreeParentForNode(node);
			}
		}

		if (parent == null)
			parent = root;

		return createNode(parent, node);
	}
	
	private DOMASTNodeLeaf createNode(DOMASTNodeParent parent, IASTNode node) {
		DOMASTNodeParent tree = new DOMASTNodeParent(node);
		parent.addChild(tree);

		// set filter flags
		if (node instanceof IASTProblemHolder || node instanceof IASTProblem) {
			tree.setFiltersFlag(DOMASTNodeLeaf.FLAG_PROBLEM);

			if (node instanceof IASTProblemHolder)
				astProblems = (IASTProblem[]) ArrayUtil.append(IASTProblem.class, astProblems,
						((IASTProblemHolder) node).getProblem());
			else {
				System.out.println("node:" + node.toString());
				// astProblems =
				// (IASTProblem[])ArrayUtil.append(IASTProblem.class,
				// astProblems, node);
			}

		}
		if (node instanceof IASTPreprocessorStatement)
			tree.setFiltersFlag(DOMASTNodeLeaf.FLAG_PREPROCESSOR);
		if (node instanceof IASTPreprocessorIncludeStatement)
			tree.setFiltersFlag(DOMASTNodeLeaf.FLAG_INCLUDE_STATEMENTS);

		return tree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.internal.core.dom.parser.cpp.CPPVisitor.
	 * CPPBaseVisitorAction#processDeclaration(org.eclipse.cdt.core.dom.ast.
	 * IASTDeclaration)
	 */
	public int visit(IASTDeclaration declaration) {
		System.out.println("IASTDeclaration :" + declaration.toString());
		System.out.println("IASTDeclaration :" + CASTUtil.getNodeSignature(declaration));

		DOMASTNodeLeaf temp = addRoot(declaration);
		if (temp == null)
			return PROCESS_ABORT;
		else if (temp instanceof DOMASTNodeLeafContinue)
			return PROCESS_CONTINUE;
		else
			return PROCESS_CONTINUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.cdt.internal.core.dom.parser.cpp.CPPVisitor.
	 * CPPBaseVisitorAction#processDeclarator(org.eclipse.cdt.core.dom.ast.
	 * IASTDeclarator)
	 */
	public int visit(IASTDeclarator declarator) {
		System.out.println("IASTDeclarator :" + declarator.toString());
		System.out.println("IASTDeclarator :" + CASTUtil.getNodeSignature(declarator));
		DOMASTNodeLeaf temp = addRoot(declarator);

		IASTPointerOperator[] ops = declarator.getPointerOperators();
		for (IASTPointerOperator op : ops)
			addRoot(op);

		if (declarator instanceof IASTArrayDeclarator) {
			IASTArrayModifier[] mods = ((IASTArrayDeclarator) declarator).getArrayModifiers();
			for (IASTArrayModifier mod : mods)
				addRoot(mod);
		}

		if (temp == null)
			return PROCESS_ABORT;
		else if (temp instanceof DOMASTNodeLeafContinue)
			return PROCESS_CONTINUE;
		else
			return PROCESS_CONTINUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.internal.core.dom.parser.c.CVisitor.CBaseVisitorAction#
	 * processDesignator(org.eclipse.cdt.core.dom.ast.c.ICASTDesignator)
	 */
	public int visit(ICASTDesignator designator) {
		System.out.println("ICASTDesignator :" + designator.toString());
		System.out.println("ICASTDesignator :" + CASTUtil.getNodeSignature(designator));
		DOMASTNodeLeaf temp = addRoot(designator);
		if (temp == null)
			return PROCESS_ABORT;
		else if (temp instanceof DOMASTNodeLeafContinue)
			return PROCESS_CONTINUE;
		else
			return PROCESS_CONTINUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.internal.core.dom.parser.c.CVisitor.CBaseVisitorAction#
	 * processDeclSpecifier(org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier)
	 */
	public int visit(IASTDeclSpecifier declSpec) {
		System.out.println("IASTDeclSpecifier :" + declSpec.toString());
		System.out.println("IASTDeclSpecifier :" + CASTUtil.getNodeSignature(declSpec));
		DOMASTNodeLeaf temp = addRoot(declSpec);
		if (temp == null)
			return PROCESS_ABORT;
		else if (temp instanceof DOMASTNodeLeafContinue)
			return PROCESS_CONTINUE;
		else
			return PROCESS_CONTINUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.internal.core.dom.parser.c.CVisitor.CBaseVisitorAction#
	 * processEnumerator(org.eclipse.cdt.core.dom.ast.IASTEnumerationSpecifier.
	 * IASTEnumerator)
	 */
	public int visit(IASTEnumerator enumerator) {
		System.out.println("IASTEnumerator :" + enumerator.toString());
		System.out.println("IASTEnumerator :" + CASTUtil.getNodeSignature(enumerator));
		DOMASTNodeLeaf temp = addRoot(enumerator);
		if (temp == null)
			return PROCESS_ABORT;
		else if (temp instanceof DOMASTNodeLeafContinue)
			return PROCESS_CONTINUE;
		else
			return PROCESS_CONTINUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.internal.core.dom.parser.c.CVisitor.CBaseVisitorAction#
	 * processExpression(org.eclipse.cdt.core.dom.ast.IASTExpression)
	 */
	public int visit(IASTExpression expression) {
		System.out.println("IASTExpression :" + expression.toString());
		System.out.println("IASTExpression :" + CASTUtil.getNodeSignature(expression));
		DOMASTNodeLeaf temp = addRoot(expression);
		if (temp == null)
			return PROCESS_ABORT;
		else if (temp instanceof DOMASTNodeLeafContinue)
			return PROCESS_CONTINUE;
		else
			return PROCESS_CONTINUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.internal.core.dom.parser.c.CVisitor.CBaseVisitorAction#
	 * processInitializer(org.eclipse.cdt.core.dom.ast.IASTInitializer)
	 */
	public int visit(IASTInitializer initializer) {
		System.out.println("IASTInitializer :" + initializer.toString());
		System.out.println("IASTInitializer :" + CASTUtil.getNodeSignature(initializer));
		DOMASTNodeLeaf temp = addRoot(initializer);
		if (temp == null)
			return PROCESS_ABORT;
		else if (temp instanceof DOMASTNodeLeafContinue)
			return PROCESS_CONTINUE;
		else
			return PROCESS_CONTINUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.internal.core.dom.parser.c.CVisitor.CBaseVisitorAction#
	 * processName(org.eclipse.cdt.core.dom.ast.IASTName)
	 */
	public int visit(IASTName name) {
		System.out.println("IASTName :" + name.toString());
		System.out.println("IASTName :" + CASTUtil.getNodeSignature(name));
		DOMASTNodeLeaf temp = null;
		if (name.toString() != null)
			temp = addRoot(name);
		else
			return PROCESS_CONTINUE;
		if (temp == null)
			return PROCESS_ABORT;
		else if (temp instanceof DOMASTNodeLeafContinue)
			return PROCESS_CONTINUE;
		else
			return PROCESS_CONTINUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.internal.core.dom.parser.c.CVisitor.CBaseVisitorAction#
	 * processParameterDeclaration(org.eclipse.cdt.core.dom.ast.
	 * IASTParameterDeclaration)
	 */
	public int visit(IASTParameterDeclaration parameterDeclaration) {
		System.out.println("IASTParameterDeclaration :" + CASTUtil.getNodeSignature(parameterDeclaration));
		System.out.println("IASTParameterDeclaration :" + parameterDeclaration.toString());
		DOMASTNodeLeaf temp = addRoot(parameterDeclaration);
		if (temp == null)
			return PROCESS_ABORT;
		else if (temp instanceof DOMASTNodeLeafContinue)
			return PROCESS_CONTINUE;
		else
			return PROCESS_CONTINUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.internal.core.dom.parser.c.CVisitor.CBaseVisitorAction#
	 * processStatement(org.eclipse.cdt.core.dom.ast.IASTStatement)
	 */
	public int visit(IASTStatement statement) {
		System.out.println("IASTStatement :" + statement.toString());
		System.out.println("IASTStatement :" + CASTUtil.getNodeSignature(statement));
		DOMASTNodeLeaf temp = addRoot(statement);
		if (temp == null)
			return PROCESS_ABORT;
		else if (temp instanceof DOMASTNodeLeafContinue)
			return PROCESS_CONTINUE;
		else
			return PROCESS_CONTINUE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.cdt.internal.core.dom.parser.c.CVisitor.CBaseVisitorAction#
	 * processTypeId(org.eclipse.cdt.core.dom.ast.IASTTypeId)
	 */
	public int visit(IASTTypeId typeId) {
		System.out.println("IASTTypeId :" + typeId.toString());
		System.out.println("IASTTypeId :" + CASTUtil.getNodeSignature(typeId));
		DOMASTNodeLeaf temp = addRoot(typeId);
		if (temp == null)
			return PROCESS_ABORT;
		else if (temp instanceof DOMASTNodeLeafContinue)
			return PROCESS_CONTINUE;
		else
			return PROCESS_CONTINUE;
	}

	private DOMASTNodeLeaf mergeNode(ASTNode node) {
		DOMASTNodeLeaf temp = addRoot(node);

		if (node instanceof IASTPreprocessorMacroDefinition)
			addRoot(((IASTPreprocessorMacroDefinition) node).getName());

		return temp;
	}

	public DOMASTNodeLeaf[] mergePreprocessorStatements(IASTPreprocessorStatement[] statements) {
		DOMASTNodeLeaf[] leaves = new DOMASTNodeLeaf[statements.length];
		for (int i = 0; i < statements.length; i++) {
			if (monitor != null && monitor.isCanceled())
				return leaves;

			if (statements[i] instanceof ASTNode)
				leaves[i] = mergeNode((ASTNode) statements[i]);
		}

		return leaves;
	}

	public void mergePreprocessorProblems(IASTProblem[] problems) {
		for (IASTProblem problem : problems) {
			if (monitor != null && monitor.isCanceled())
				return;

			if (problem instanceof ASTNode)
				mergeNode((ASTNode) problem);
		}
	}

	public void groupIncludes(DOMASTNodeLeaf[] treeIncludes) {
		// loop through the includes and make sure that all of the nodes
		// that are children of the TU are in the proper include (based on
		// offset)
		for (int i = treeIncludes.length - 1; i >= 0; i--) {
			final DOMASTNodeLeaf nodeLeaf = treeIncludes[i];
			if (nodeLeaf == null || !(nodeLeaf.getNode() instanceof IASTPreprocessorIncludeStatement))
				continue;

			final String path = ((IASTPreprocessorIncludeStatement) nodeLeaf.getNode()).getPath();
			final DOMASTNodeLeaf[] children = root.getChildren(false);
			for (final DOMASTNodeLeaf child : children) {
				if (child != null && child != nodeLeaf && child.getNode().getContainingFilename().equals(path)) {
					root.removeChild(child);
					((DOMASTNodeParent) nodeLeaf).addChild(child);
				}
			}
		}
	}

	public IASTProblem[] getASTProblems() {
		return astProblems;
	}
}
