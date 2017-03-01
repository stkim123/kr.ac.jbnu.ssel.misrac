package kr.ac.jbnu.ssel.misrac.rulesupport;

import org.eclipse.cdt.core.dom.ast.IASTNode;

/**
 * @author "STKIM"
 */
public class ViolationMessage {
	private AbstractMisraCRule rule;

	private String message;
	private IASTNode node;

	private ViolationLevel violationLevel;

	public ViolationMessage(AbstractMisraCRule rule, String message, IASTNode node, ViolationLevel violationLevel) {
		this.rule = rule;
		this.message = message;
		this.node = node;
		this.violationLevel = violationLevel;
	}

	public ViolationMessage(AbstractMisraCRule rule, String message, IASTNode node) {
		this(rule,message,node,ViolationLevel.severe);
	}

	public AbstractMisraCRule getRule() {
		return rule;
	}

	public String getMessage() {
		return message;
	}

	public IASTNode getNode() {
		return node;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ViolationLevel getViolationLevel() {
		return violationLevel;
	}

	public void setViolationLevel(ViolationLevel violationLevel) {
		this.violationLevel = violationLevel;
	}

	@Override
	public String toString() {
		return rule.getRuleID() + " - violation - " + message + ", node:" + node.toString();
	}
}
