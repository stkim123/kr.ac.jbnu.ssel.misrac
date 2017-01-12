package kr.ac.jbnu.ssel.misrac.rulesupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

public class MisraCRuleGroup extends AbstractMisraCRule {

    private List<AbstractMisraCRule> rules = Collections.synchronizedList(new ArrayList<AbstractMisraCRule>());

    public MisraCRuleGroup(String ruleID, boolean visitNodes, IASTTranslationUnit ast) {
	super(ruleID, visitNodes, ast);
    }

    public synchronized void addMisraCRule(AbstractMisraCRule rule) {
	if (!rules.contains(rule)) {
	    rules.add(rule);
	}
    }

    public synchronized void removeMisraCRule(AbstractMisraCRule rule) {
	if (!rules.contains(rule)) {
	    rules.remove(rule);
	}
    }

    @Override
    public void checkRule() throws MiaraCRuleException {
	synchronized (rules) {
	    for (AbstractMisraCRule misraCRule : rules) {
		misraCRule.checkRule();
		if (misraCRule.isViolated()) {
		    violationMsgs.addAll(Arrays.asList(misraCRule.getViolationMessages()));
		    isViolated = true;
		}
	    }
	}
    }
}
