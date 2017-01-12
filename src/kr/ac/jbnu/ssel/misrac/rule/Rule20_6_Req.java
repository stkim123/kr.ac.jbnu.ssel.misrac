package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorIncludeStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

public class Rule20_6_Req extends AbstractMisraCRule {
	  private static final String STDDEF_H_ = "stddef.h";
	  private static final String OFFSETOF = "offsetof";
	  
	  private boolean isSTDDEF_H_included = false;
	  
	  public Rule20_6_Req(IASTTranslationUnit ast) {
			super("Rule20_6_Req", false, ast);
			shouldVisitPreprocessor = true;
			shouldVisitExpressions = true;
			
	  }
	  
	  public Rule20_6_Req(String ruleID, boolean visitNodes, IASTTranslationUnit ast) {
		  super(ruleID, visitNodes, ast);
		  }
	  
	  @Override 
	  protected int visit(IASTPreprocessorIncludeStatement statement) {
		  if (statement.toString().contains(STDDEF_H_)) {
			  isSTDDEF_H_included = true;
			  }
		  return super.visit(statement);
		  }
	  
	  @Override
	  protected int visit(IASTFunctionCallExpression expression) {
		  IASTIdExpression functionNameExp = (IASTIdExpression) expression.getFunctionNameExpression();
		  
		  System.out.println("function name:" + functionNameExp.getName().toString());
		  if (isSTDDEF_H_included == true && OFFSETOF.contains(functionNameExp.getName().toString())) {
			  isViolated = true;
			  String message = MessageFactory.getInstance().getMessage(5120);
			  violationMsgs.add(new ViolationMessage(this, message + "-- " + functionNameExp.getName().toString(), expression));
			  }
		  return super.visit(expression);
		  }
}
