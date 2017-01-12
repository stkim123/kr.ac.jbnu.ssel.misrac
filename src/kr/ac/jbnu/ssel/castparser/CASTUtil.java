package kr.ac.jbnu.ssel.castparser;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTypeId;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTConstructorInitializer;
import org.eclipse.cdt.internal.core.model.ASTStringUtil;
/**
 * 
 * @author STKim2
 *
 */
public class CASTUtil {
	public static String getNodeSignature(IASTNode node) {
		if (node instanceof IASTDeclarator)
			return ASTStringUtil.getSignatureString(null, (IASTDeclarator) node);
		if (node instanceof IASTDeclSpecifier)
			return ASTStringUtil.getSignatureString((IASTDeclSpecifier) node, null);
		if (node instanceof IASTTypeId) {
			final IASTTypeId typeId = (IASTTypeId) node;
			return ASTStringUtil.getSignatureString(typeId.getDeclSpecifier(), typeId.getAbstractDeclarator());
		}
		if (node instanceof IASTSimpleDeclaration) {
			IASTSimpleDeclaration decl = (IASTSimpleDeclaration) node;
			StringBuffer buffer = new StringBuffer();
			buffer.append(getNodeSignature(decl.getDeclSpecifier()));
			IASTDeclarator[] declarators = decl.getDeclarators();
			for (int i = 0; i < declarators.length; ++i) {
				buffer.append(" ");
				buffer.append(getNodeSignature(declarators[i]));
				if (declarators[i].getInitializer() != null
						&& declarators[i].getInitializer() instanceof ICPPASTConstructorInitializer) {
					buffer.append(ASTStringUtil.getInitializerString(declarators[i].getInitializer()));
				}
			}
			buffer.append(";"); //$NON-NLS-1$
			return buffer.toString();
		}
		if (node instanceof IASTExpression) {
			return ASTStringUtil.getExpressionString((IASTExpression) node);
		}
		return "";
	}
}
