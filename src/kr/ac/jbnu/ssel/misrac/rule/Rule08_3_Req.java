package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.IASTNode;
import org.eclipse.cdt.core.dom.ast.IASTParameterDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 8.3: (Required) For each function parameter the type given
 * in the declaration and definition shall be identical, and the return types
 * shall also be identical.
 * 
 * The types of the parameters and return values in the prototype and the
 * definition must match. This requires identical types including typedef names
 * and qualifiers, and not just identical base types.
 * 
 * �̰� ������ Rule8.1�� ������ �� �ۿ� ����. �̰��� ���� �Ϸ�� Rule8.1�� ���� ��������
 * 
 * @author stkim
 *
 */
public class Rule08_3_Req extends AbstractMisraCRule
{

	private static HashSet<IASTSimpleDeclaration> prototypes = new HashSet<IASTSimpleDeclaration>();

	public Rule08_3_Req(IASTTranslationUnit ast)
	{
		super("Rule08_3_req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitPreprocessor = true;
		shouldVisitExpressions = true;
		shouldVisitStatements = true;
	}

	/**
	 * Collect Prototype Functions
	 */
	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration)
	{

		IASTDeclarator[] declarators = simpleDeclaration.getDeclarators();

		boolean isProtoFuncDeclaration = false;
		for (IASTDeclarator declarator : declarators)
		{
			if (declarator instanceof IASTFunctionDeclarator)
			{
				isProtoFuncDeclaration = true;
			}
		}

		if (isProtoFuncDeclaration)
		{
			prototypes.add(simpleDeclaration);
		}

		return super.visit(simpleDeclaration);
	}

	protected int visit(IASTFunctionDefinition functionDefinition)
	{
		IASTDeclSpecifier specifier = functionDefinition.getDeclSpecifier();
		IASTFunctionDeclarator funcDeclarator = functionDefinition.getDeclarator();

		if (!checkSameStructureWithPrototype(functionDefinition))
		{
			String functionName = funcDeclarator.getName().toString();

			// "Function '%s' is declared using typedefs which are different to those in a previous declaration."
			String msg = MessageFactory.getInstance().getMessage(0624);
			String msgWithFuncName = String.format(msg, functionName);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msgWithFuncName, functionDefinition));
			
			// Type or number of arguments doesn't match previous use of the function.
			String msg2 = MessageFactory.getInstance().getMessage(1331);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msg2, functionDefinition));

			// Type or number of arguments doesn't match prototype found later.
			String msg3 = MessageFactory.getInstance().getMessage(1332);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msg3, functionDefinition));
	
			// Type or number of arguments doesn't match function definition found later.
			String msg4 = MessageFactory.getInstance().getMessage(1333);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msg4, functionDefinition));
			
			// Type of argument no. %s differs from its type in definition of function.
			String msg5 = MessageFactory.getInstance().getMessage(3320);
			ArrayList<IASTParameterDeclaration> protoParams = getParams(funcDeclarator.getChildren());
			String msgWithNumOfParams = String.format(msg5, ""+ protoParams.size());
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msgWithNumOfParams, functionDefinition));
				
			// Function parameter declared with type qualification which differs from previous declaration.	
			String msg6 = MessageFactory.getInstance().getMessage(3675);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msg6, functionDefinition));
			
			isViolated = true;
		}

		return super.visit(functionDefinition);
	}

	/**
	 * function definition�� prototype�� �������� �Ǵ�
	 * 
	 * @param functionDefinition
	 * @return
	 */
	private boolean checkSameStructureWithPrototype(IASTFunctionDefinition functionDefinition)
	{

		for (IASTSimpleDeclaration prototypeFunc : prototypes)
		{
			// return true(consider 'conform'), if the function conform with one
			// of the prototype functions.
			if (checkConformanceOfPrototypeFunction(functionDefinition, prototypeFunc))
			{
				return true;
			}
		}
		return false;
	}

	private boolean checkConformanceOfPrototypeFunction(IASTFunctionDefinition functionDefinition,
			IASTSimpleDeclaration prototypeFunc)
	{

		// get return type, parameters and function name of the given function.
		IASTDeclSpecifier specifier = functionDefinition.getDeclSpecifier();
		IASTFunctionDeclarator funcDeclarator = functionDefinition.getDeclarator();
		ArrayList<IASTParameterDeclaration> params = new ArrayList<IASTParameterDeclaration>();

		IASTNode[] children = funcDeclarator.getChildren();
		for (IASTNode iastNode : children)
		{
			if (iastNode instanceof IASTParameterDeclaration)
			{
				params.add((IASTParameterDeclaration) iastNode);
			}
		}

		String functionName = funcDeclarator.getName().toString();

		///////////////////////////////
		boolean isConform = false;
		IASTDeclSpecifier protoSpecifier = prototypeFunc.getDeclSpecifier();
		IASTDeclarator[] prototypeDeclarators = prototypeFunc.getDeclarators();
		for (IASTDeclarator iastDeclarator : prototypeDeclarators)
		{
			if (iastDeclarator instanceof IASTFunctionDeclarator)
			{
				boolean sameFuncName = false, notSameParamType = false, sameReturnType = false;

				// 1. check if the function name is same.
				IASTFunctionDeclarator protoFunctionDecl = (IASTFunctionDeclarator) iastDeclarator;
				if (functionName.equals(protoFunctionDecl.getName().toString()))
				{
					sameFuncName = true;
				}

				// 2. check if the parameter type is same.
				ArrayList<IASTParameterDeclaration> protoParams = getParams(protoFunctionDecl.getChildren());
				if (protoParams.size() == params.size())
				{
					for (int i = 0; i < protoParams.size(); i++)
					{
						IASTParameterDeclaration param = protoParams.get(i);
						String prototypeParamType = param.getDeclSpecifier().getRawSignature();

						IASTParameterDeclaration protoParam = params.get(i);
						String paramType = protoParam.getDeclSpecifier().getRawSignature();

						if (!prototypeParamType.equals(paramType))
						{
							notSameParamType = true; // if one of the param type
														// is different,
														// notSameParamType is
														// false.
							break;
						}
					}
				}

				// 3. check if the return type is the same.
				String[] protoReturns = protoSpecifier.getRawSignature().split(" ");
				String[] returns = specifier.getRawSignature().split(" ");
				if (compareTwoStringArrays(protoReturns, returns))
				{
					sameReturnType = true;
				}

				// if aforementioned three are the same, we consider it
				// conforms.
				if (sameFuncName && !notSameParamType && sameReturnType)
				{
					isConform = true;
				}
			}
		}
		return isConform;
	}

	/**
	 * extract only parameters
	 * 
	 * @param children
	 * @return
	 */
	private ArrayList<IASTParameterDeclaration> getParams(IASTNode[] children)
	{
		ArrayList<IASTParameterDeclaration> params = new ArrayList<IASTParameterDeclaration>();
		for (IASTNode iastNode : children)
		{
			if (iastNode instanceof IASTParameterDeclaration)
			{
				params.add((IASTParameterDeclaration) iastNode);
			}
		}
		return params;
	}

	private boolean compareTwoStringArrays(String[] protoReturns, String[] returns)
	{
		if (protoReturns.length != returns.length)
		{
			return false;
		}

		for (int i = 0; i < protoReturns.length; i++)
		{
			if (!protoReturns[i].equals(returns[i]))
			{
				return false;
			}
		}
		return true;
	}
}
