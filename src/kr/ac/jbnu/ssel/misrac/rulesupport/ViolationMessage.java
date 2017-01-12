package kr.ac.jbnu.ssel.misrac.rulesupport;

import org.eclipse.cdt.core.dom.ast.IASTNode;

public class ViolationMessage {
    private AbstractMisraCRule rule;

    private String message;
    private IASTNode node;

    public ViolationMessage(AbstractMisraCRule rule, String message, IASTNode node) {
	this.rule = rule;
	this.message = message;
	this.node = node;
	// this.violationPart = violationPart;
    }
    
    public AbstractMisraCRule getRule()
    {
	return rule;
    }

    public String getMessage() {
	return message;
    }
    
    public IASTNode getNode()
    {
	return node;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    @Override
    public String toString() {
	return rule.getRuleID() + " - violation - " + message + ", node:" + node.toString();
    }
}
