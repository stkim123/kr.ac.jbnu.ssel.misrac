package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
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
 * MISRA-C:2004  Rule  8.1:  (Required)	
 * Functions shall have prototype declarations and the prototype shall 
 * be visible at both the function definition and call.
 * 
 * The use of prototypes enables the compiler to check the integrity of function definitions and calls. 
 * Without prototypes the compiler is not obliged to pick up certain errors in function calls 
 * (e.g. different number of arguments from the function body, mismatch in types of arguments 
 * between call and definition). Function interfaces have been shown to be a cause of considerable problems, 
 * and therefore this rule is considered very important.
 * 
 * The recommended method of implementing function prototypes for external functions is to declare the function 
 * (i.e. give the function prototype) in a header file, and then include the header file in all those code 
 * files that need the prototype (see Rule ).
 * 
 * The provision of a prototype for a function with internal linkage is a good programming practice.
 * 
 * TODO-1: functionDeclarations에 '#include'안에 있는 함수 정의도 가져와서 이 내용을 담아야함.  
 * TODO-2: CallFunction할때, 함수만 가지고 어떤 함수를 호출하는지 알아내야함. 이게 가능한가?
 * 
 * @author stkim
 *
 */
public class Rule08_1_Req extends AbstractMisraCRule {
	private static HashSet<IASTSimpleDeclaration> prototypes = new HashSet<IASTSimpleDeclaration>();
	
	public Rule08_1_Req(IASTTranslationUnit ast) {
		super("Rule08_1_Req", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitPreprocessor = true;
		shouldVisitExpressions = true;
		shouldVisitStatements = true;
	}

	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {
		
		IASTDeclarator[] declarators = simpleDeclaration.getDeclarators();
		
		boolean isProtoFuncDeclaration = false;
		for (IASTDeclarator declarator: declarators) {
			if( declarator instanceof IASTFunctionDeclarator)
			{
				isProtoFuncDeclaration = true;
			}
		}
		
		if(isProtoFuncDeclaration)
		{
			prototypes.add(simpleDeclaration);
		}

		return super.visit(simpleDeclaration);
	}
	
	/**
	 * Rule: function선언부가 prototype으로 선언되어있어야함. 즉, declspecifier와 declarator의 내용이 동일해야함. 
	 * 			declspecifier는 완전 동일해야하고, declarator부분은 파라미터의 이름은 제외하고 다른것은 동일해야함.  
	 * 
	 */
	@Override
	protected int visit(IASTFunctionDefinition functionDefinition) {
		
		IASTDeclSpecifier specifier = functionDefinition.getDeclSpecifier();
		IASTFunctionDeclarator funcDeclarator = functionDefinition.getDeclarator();
		
		if( !checkConformanceOfPrototype(functionDefinition))
		{
			String functionName = funcDeclarator.getName().toString();
			
			// "Defining '%s()' with an identifier list and separate parameter declarations is an obsolescent feature."
			String msg = MessageFactory.getInstance().getMessage(3002);
			String msgWithFuncName = String.format(msg, functionName);
			violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msgWithFuncName, functionDefinition));

			if( specifier.getRawSignature().startsWith("extern"))
			{
				// "No function declaration. Implicit declaration inserted: 'extern int %s();'.";
				String msg2 = MessageFactory.getInstance().getMessage(3335);
				String msgWithFuncName2 = String.format(msg2, functionName);
				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + msgWithFuncName2, functionDefinition));
			}

			isViolated = true;
		}
		
		return super.visit(functionDefinition);
	}
	
	/**
	 * function definition과 prototype이 동일한지 판단
	 * 
	 * @param functionDefinition
	 * @return
	 */
	private boolean checkConformanceOfPrototype(IASTFunctionDefinition functionDefinition) {
		
		for (IASTSimpleDeclaration prototypeFunc : prototypes) 
		{
			// return true(consider 'conform'), if the function conform with one of the prototype functions. 
			if( checkConformanceOfPrototypeFunction(functionDefinition, prototypeFunc))
			{
				return true;
			}
		}
		return false;
	}

	private boolean checkConformanceOfPrototypeFunction(IASTFunctionDefinition functionDefinition,
			IASTSimpleDeclaration prototypeFunc) {
		
		// get return type, parameters and function name of the given function.
		IASTDeclSpecifier specifier = functionDefinition.getDeclSpecifier();
		IASTFunctionDeclarator funcDeclarator = functionDefinition.getDeclarator();
		ArrayList<IASTParameterDeclaration> params = new ArrayList<IASTParameterDeclaration>();
		
		IASTNode[] children = funcDeclarator.getChildren();
		for (IASTNode iastNode : children) {
			if( iastNode instanceof IASTParameterDeclaration)
			{
				params.add((IASTParameterDeclaration)iastNode);
			}
		}
		
		String functionName = funcDeclarator.getName().toString();
		
		///////////////////////////////
		boolean isConform = false;
		IASTDeclSpecifier protoSpecifier = prototypeFunc.getDeclSpecifier();
		IASTDeclarator[] prototypeDeclarators = prototypeFunc.getDeclarators();
		for (IASTDeclarator iastDeclarator : prototypeDeclarators) 
		{
			if( iastDeclarator instanceof IASTFunctionDeclarator)
			{
				boolean sameFuncName = false, notSameParamType = false, sameReturnType = false;
				
				// 1. check if the function name is same.
				IASTFunctionDeclarator protoFunctionDecl = (IASTFunctionDeclarator)iastDeclarator;
				if( functionName.equals(protoFunctionDecl.getName().toString()))	 
				{
					sameFuncName = true;
				}
				
				// 2. check if the parameter type is same. 
				ArrayList<IASTParameterDeclaration> protoParams = getParams( protoFunctionDecl.getChildren());
				if( protoParams.size() == params.size())
				{
					for(int i = 0; i < protoParams.size(); i++)
					{
						IASTParameterDeclaration param = protoParams.get(i);
						String prototypeParamType = param.getDeclSpecifier().getRawSignature();
						
						IASTParameterDeclaration protoParam = params.get(i);
						String paramType = protoParam.getDeclSpecifier().getRawSignature();
							
						if(!prototypeParamType.equals(paramType))
						{
							notSameParamType = true;	// if one of the param type is different, notSameParamType is false.
							break;
						}
					}	
				}

				// 3. check if the return type is the same.
				String[] protoReturns = protoSpecifier.getRawSignature().split(" ");
				String[] returns = specifier.getRawSignature().split(" ");
				if( compareTwoStringArrays(protoReturns, returns))
				{
					sameReturnType = true;
				}
				
				// if aforementioned three are the same, we consider it conforms. 
				if( sameFuncName && !notSameParamType && sameReturnType)
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
	private ArrayList<IASTParameterDeclaration> getParams(IASTNode[] children) {
		ArrayList<IASTParameterDeclaration> params = new ArrayList<IASTParameterDeclaration>();
		for (IASTNode iastNode: children) {
			if( iastNode instanceof IASTParameterDeclaration)
			{
				params.add((IASTParameterDeclaration)iastNode);
			}
		}
		return params;
	}

	private boolean compareTwoStringArrays(String[] protoReturns, String[] returns) {
		if( protoReturns.length != returns.length)
		{
			return false;
		}
		
		for(int i= 0; i < protoReturns.length ; i++)
		{
			if( !protoReturns[i].equals(returns[i]))
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Rule: function호출부분이 prototype으로 선언되어 있어야함.
	 * TODO: 이것 이슈임
	 */
	@Override
	protected int visit(IASTFunctionCallExpression expression) {
		String msg = MessageFactory.getInstance().getMessage(3450);
		return super.visit(expression);
	}

}
