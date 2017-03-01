package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 2.2: (Required) [next]
 * 
 * Source code shall only use C-style comments.
 * 
 * This excludes the use of // C99 style comments and C++ style comments, since
 * these are not permitted in C90. Many compilers support the // style of
 * comments as an extension to c90. The use of // in preprocessor directives
 * (e.g. #define) can vary. Also the mixing of ............ is not consistent.
 * This is more than a style issue, since different (pre C99) compilers may
 * behave differently.
 * 
 * Example Code: TODO
 * 
 * [STATUS: DONE]
 * @author STKim2
 *
 */
public class Rule02_2_Req extends AbstractMisraCRule {

    private static final String C_STYLE_COMMENT_PREFIX = "/*";
    private static final String CPP_STYLE_COMMENT_PREFIX = "//";

    public Rule02_2_Req(IASTTranslationUnit ast) {
	super("Rule02_2_Req", false, ast);
	shouldVisitComment = true;
    }

    @Override
    protected int visit(IASTComment comment) {

	if (comment.toString().startsWith(CPP_STYLE_COMMENT_PREFIX)) {
	    
	    String msg = MessageFactory.getInstance().getMessage(1011);

	    if (violationMsgs == null) {
		violationMsgs = new ArrayList<ViolationMessage>();
	    }

	    violationMsgs.add(new ViolationMessage(this, getRuleID() + ":"+ msg + "--" + comment.toString(), comment));
	    isViolated = true;
	}
	return super.visit(comment);
    }

}
