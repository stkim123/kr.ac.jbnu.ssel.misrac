package kr.ac.jbnu.ssel.misrac.ui.preference;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import kr.ac.jbnu.ssel.misrac.rule.RuleLocation;
import kr.ac.jbnu.ssel.misrac.ui.Constant;

/**
 * 
 * @author Taeyoung Kim
 */
public class MisraUIdataHandler implements Cloneable {

	private static MisraUIdataHandler instance;

	private List<Rule> ruleList;
	private HashMap<String, List<Rule>> rulesByCategory = new HashMap<String, List<Rule>>();

	private MisraUIdataHandler() {
		if (ruleList == null) {
			loadAllRules();
		}
	}

	public static MisraUIdataHandler getInstance() {
		if (instance == null) {
			instance = new MisraUIdataHandler();
		}

		return instance;
	}

	public HashSet<Rule> getShouldCheckRules() {
		HashSet<Rule> shouldCheckRules = new HashSet<Rule>();
		for (Rule rule : ruleList) {
			if (rule.isShouldCheck()) {
				shouldCheckRules.add(rule);
			}
		}
		return shouldCheckRules;
	}

	public List<Rule> getRules() {
		Collections.sort(ruleList, new java.util.Comparator<Rule>() {

			@Override
			public int compare(Rule rule1, Rule rule2) {
				// TODO Auto-generated method stub
				double rule1MinerNum = Double.valueOf(rule1.minerNum);
				double rule2MinerNum = Double.valueOf(rule2.minerNum);
				return Double.compare(rule1MinerNum, rule2MinerNum);
			}
		});
		return ruleList;
	}

	public List<Rule> getRules(String category) {
		return rulesByCategory.get(category);
	}

	public List<Rule> loadAllRules() {
		try {
			File file = new File(Constant.rule_description_path);
			Serializer serializer = new Persister();
			RuleList rules = serializer.read(RuleList.class, file);
			ruleList = rules.getRules();

			for (Rule rule : ruleList) {
				if (rule.getCategory() != null) {
					String category = rule.getCategory();
					List<Rule> ruleCate = rulesByCategory.get(category);

					if (ruleCate == null) {
						ruleCate = new ArrayList<Rule>();
						rulesByCategory.put(category, ruleCate);
					}

					ruleCate.add(rule);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ruleList;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void storeToXml() {
//		JAXBContext context;
//		try {
//			context = JAXBContext.newInstance(String.class, Rules.class);
//			Marshaller marshaller = context.createMarshaller();
//			File rulesFile = new File(Constant.rule_description_path);
//			Rules rules = new Rules();
//			setClassNames();
//			rules.setRule(ruleList);
//			marshaller.marshal(rules, rulesFile);
//		} catch (JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

//	private void setClassNames() {
//		for (Rule rule : ruleList) {
//			if (rule.className.contains(Constant.notImplement)) {
//				try {
//					String className = getClassName(rule.minerNum);
//					if (className == null) {
//						continue;
//					} else {
//						rule.setClassName(className);
//					}
//				} catch (URISyntaxException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}

	private String getClassName(String minerNum) throws URISyntaxException, IOException {
		String className = null;
		ClassLoader loader = RuleLocation.class.getClassLoader();
		URL ruleCodeClassDictory = loader.getResource(RuleLocation.class.getPackage().getName().replace('.', '/'));
		URL fileURL = FileLocator.toFileURL(ruleCodeClassDictory);

		File ruleDicFile = new File(fileURL.toURI());

		String[] ruleCodeFiles = ruleDicFile.list();
		for (String ruleCodeClass : ruleCodeFiles) {
			if (!ruleCodeClass.equals("RuleLocation.class")) {
				String classRuleNumber = getClassRuleNumber(ruleCodeClass);
				if (minerNum.equals(classRuleNumber)) {
					className = ruleCodeClass;
					break;
				}
			}
		}
		return className;
	}

	private String getClassRuleNumber(String ruleCodeClass) {
		StringBuffer wholeRuleNumber = new StringBuffer();
		String ruleNum = ruleCodeClass.substring(4);
		String[] removedClass = ruleNum.split(".class");
		String[] subStrings = removedClass[0].split("_");
		String startOfNum = null;
		if (subStrings[0].startsWith("0")) {
			startOfNum = subStrings[0].replace("0", "");
		} else {
			startOfNum = subStrings[0];
		}
		if (startOfNum != null) {
			String endOfNum = subStrings[1];
			wholeRuleNumber.append(startOfNum);
			wholeRuleNumber.append("." + endOfNum);
		}
		return wholeRuleNumber.toString();
	}

	public static void main(String[] args) {
		MisraUIdataHandler misraUIdataHandler = new MisraUIdataHandler();
		List<Rule> ruleList = misraUIdataHandler.loadAllRules();
		for (Rule rule : ruleList) {
			System.out.println(rule.getMinerNum());
		}
	}

}
