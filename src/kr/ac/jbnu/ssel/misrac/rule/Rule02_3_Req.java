package kr.ac.jbnu.ssel.misrac.rule;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTComment;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * The character sequence /* shall not be used within a comment.
 * C does not support the nesting of comments even though some compilers support this as a language extension. 
 * 
 * TODO
 * 
 * @author Seunghyeon Kang
 *
 */
public class Rule02_3_Req extends AbstractMisraCRule{
	
	  private static final String COMMENT_Start = "/*";
	    private static final String COMMENT_End = "*/";

	    public Rule02_3_Req(IASTTranslationUnit ast) {
		super("Rule02_3_Req", false, ast);
		shouldVisitComment = true;
	    }

	 @Override
	    protected int visit(IASTComment comment) {

		 String commentString =comment.toString(); 
		if (commentString.startsWith(COMMENT_Start)&&(commentString.endsWith(COMMENT_End))) 
		{
			
			String commentContent = commentString.substring(commentString.indexOf(COMMENT_Start)+ 2, commentString.length()-2); 
			
			if( commentContent.indexOf(COMMENT_Start) != -1 || commentContent.indexOf(COMMENT_End) != -1)
			{
//				Nested comments are not recognized in the ISO standard.
			    String msg = MessageFactory.getInstance().getMessage(3108);

			    if (violationMsgs == null) {
				violationMsgs = new ArrayList<ViolationMessage>();
			    }

			    violationMsgs.add(new ViolationMessage(this, getRuleID() + ":"+ msg + "--" + comment.toString(), comment));
			    isViolated = true;
			}
		
		}
		return super.visit(comment);
	    }

}
