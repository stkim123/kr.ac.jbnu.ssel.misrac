package kr.ac.jbnu.ssel.misrac.rulesupport;

import java.util.ArrayList;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.castparser.DetailedASTVisitor;

/**
 * 
 * @author "STKIM"
 *
 */
public abstract class AbstractMisraCRule extends DetailedASTVisitor {

    /**
     * rule id
     */
    private String ruleID;

    /**
     * add violation message objects;
     */
    protected ArrayList<ViolationMessage> violationMsgs = new ArrayList<ViolationMessage>();

    /**
     * set to be true when the rule is violated.
     */
    protected boolean isViolated = false;

    protected IASTTranslationUnit ast;

    public AbstractMisraCRule(String ruleID, boolean visitNodes, IASTTranslationUnit ast) {
	super(visitNodes, ast);
	this.ruleID = ruleID;

	if (visitNodes) {
	    shouldVisitPreprocessor = true;
	    shouldVisitComment = true;
	}
	this.ast = ast;
    }

    public void checkRule() throws MiaraCRuleException {
	try {
	    if (shouldVisitPreprocessor) {
		extractPreprocessorNodes();
	    }
	    if (shouldVisitComment) {
		extractComments();
	    }

	    ast.accept(this);
	} catch (Exception e) {
	    new MiaraCRuleException(e);
	}
    }
    
    public String getRuleID() {
	return ruleID;
    }

    public boolean isViolated() {
	return isViolated;
    }

    public ViolationMessage[] getViolationMessages() {
	return (ViolationMessage[]) violationMsgs.toArray(new ViolationMessage[0]);
    }

}
