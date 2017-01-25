package kr.ac.jbnu.ssel.misrac.rule;

import java.util.Arrays;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 20.10: (Required) [next] The library functions atof, atoi
 * and atol from library <stdlib.h> shall not be used.
 * 
 * These functions have undefined behaviour associated with them when the string
 * cannot be converted.
 * 
 * DONE!!
 * 
 * @author kang
 */

public class Rule20_10_Req extends AbstractMisraCRule {

	private static final String STDLIB_H = "stdlib.h";
	private static final String[] VIOLATION_FUNCS = { "atof", "atoi", "atol" };

	private boolean isSTDLIB_H_included = false;

	private HashSet<String> violationFunctions = new HashSet<String>();

	public Rule20_10_Req(IASTTranslationUnit ast) {
		super("Rule20_10_Req", false, ast);
		shouldVisitPreprocessor = true;
		shouldVisitExpressions = true;
		violationFunctions.addAll(Arrays.asList(VIOLATION_FUNCS));
	}

	public Rule20_10_Req(String ruleID, boolean visitNodes, IASTTranslationUnit ast) {
		super(ruleID, visitNodes, ast);
	}

	@Override
	protected int visit(IASTPreprocessorIncludeStatement statement) {
		if (statement.toString().contains(STDLIB_H)) {
			isSTDLIB_H_included = true;
		}
		return super.visit(statement);
	}

	@Override
	protected int visit(IASTFunctionCallExpression expression) {
		IASTIdExpression functionNameExp = (IASTIdExpression) expression.getFunctionNameExpression();

		if (isSTDLIB_H_included == true && violationFunctions.contains(functionNameExp.getName().toString())) {
			isViolated = true;

			String message = MessageFactory.getInstance().getMessage(5125);
			violationMsgs.add(new ViolationMessage(this,
					getRuleID() + ":" + message + "-- " + functionNameExp.getName().toString(), expression));
		}

		return super.visit(expression);
	}
}
