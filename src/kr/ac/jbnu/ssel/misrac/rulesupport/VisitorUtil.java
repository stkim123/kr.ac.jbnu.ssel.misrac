package kr.ac.jbnu.ssel.misrac.rulesupport;

import org.eclipse.cdt.core.dom.ast.IASTNode;

/**
 * 
 * @author "STKIM"
 */
public class VisitorUtil
{
	public static boolean containsASTNodein(IASTNode parent, Class<?> target)
	{
		IASTNode[] children = parent.getChildren();
		for (IASTNode iastNode : children)
		{
			if (target.isInstance(iastNode))
			{
				return true;
			}
			else
			{
				return containsASTNodein(iastNode, target);	
			}
		}
		
		return false;
	}
}
