package kr.ac.jbnu.ssel.misrac.ui.preference;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root
public class RuleList {

	@ElementList
	protected List<Rule> Rules;
	
	@Attribute
	private String name;

	public List<Rule> getRules() {
		if (Rules == null) {
			Rules = new ArrayList<Rule>();
		}
		return this.Rules;
	}

	public void setRules(List<Rule> ruleList) {
		this.Rules = ruleList;
	}

}