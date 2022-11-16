package test.kr.ac.jbnu.ssel.misrac.ui.preference;

import java.io.File;
import java.util.List;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import kr.ac.jbnu.ssel.misrac.ui.Constant;
import kr.ac.jbnu.ssel.misrac.ui.preference.Rule;
import kr.ac.jbnu.ssel.misrac.ui.preference.RuleList;

public class SimpleXmlTest {
	public static void main(String[] args) throws Exception {
		File file = new File(Constant.rule_description_path);
		Serializer serializer = new Persister();
		RuleList ruleList = serializer.read(RuleList.class, file);
		List<Rule> rules = ruleList.getRules();
		for (Rule rule : rules) {
			System.out.println(rule.getDescription());
		}

	}
}
