package test.kr.ac.jbnu.ssel.misrac.xmlData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.eclipse.core.runtime.FileLocator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import kr.ac.jbnu.ssel.misrac.rule.R;
import kr.ac.jbnu.ssel.misrac.ui.preference.ObjectFactory;
import kr.ac.jbnu.ssel.misrac.ui.preference.Rule;
import kr.ac.jbnu.ssel.misrac.ui.preference.Rules;
import test.kr.ac.jbnu.ssel.misrac.rule.testC.CCode;

public class GeneratingRuleintoXML {

	private String ruleResourcePath = "RuleResources";
	private String ruleCategoryPath = "Resource//RuleCategory.Properties";
	private FileInputStream input;

	public void generateRuletoXML() throws IOException, URISyntaxException, JAXBException {

		ObjectFactory factory = new ObjectFactory();
		Rules rules = factory.createRules();
		List<Rule> ruleList = rules.getRule();
		File file = new File(ruleResourcePath);
		File[] ruleResourceList = file.listFiles();
		for (File ruleResource : ruleResourceList) {
			Rule rule = factory.createRule();
			setRuleData(ruleResource, rule);
			ruleList.add(rule);
		}
		JAXBContext context = JAXBContext.newInstance(String.class, Rules.class);
		Marshaller marshaller = context.createMarshaller();
		File rulesFile = new File( "Resource/rules.xml" );
		marshaller.marshal(rules, rulesFile);
		System.out.println("done");
	}

	private void setRuleData(File ruleResource, Rule rule) throws URISyntaxException {
		try {
			Document document = Jsoup.parse(ruleResource, "UTF-8");
			document.text().replaceAll("\\s+", " ");
			Elements ruleNumAndType = document.getElementsByTag("span");
			setNumAndTypeAndClassName(ruleNumAndType, rule);
			Elements ruleName = document.getElementsByAttributeValue("class", "heading_message");
			String description = document.body().ownText();
			//setting sub rule data
			rule.setRuleName(ruleName.text());
			rule.setDescription(description);
			rule.setShouldCheck(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setNumAndTypeAndClassName(Elements ruleNumAndType, Rule rule) throws URISyntaxException, IOException {
		//manipulate String in the elements
		String fullString = ruleNumAndType.text();
		String[] subString = fullString.split("Rule");
		String[] numAndTypeString = subString[1].split(":");
		String minerNum = numAndTypeString[0].substring(2, numAndTypeString[0].length());
		String majorNum = minerNum.replace(".", " ").split(" ")[0];
		String ruleType = numAndTypeString[1].substring(2, numAndTypeString[1].length());
		ruleType = ruleType.substring(1, ruleType.length()-1);
		String category = getRuleCategory(minerNum);
		String className = getClassName(minerNum);
		if(className!=null)
			rule.setClassName(className);
		else
			rule.setClassName("Rule"+minerNum+"(notImplement).class");
		if(category!=null)
			rule.setCategory(category);
		// set Values in the rule object
		rule.setMajorNum(majorNum);
		rule.setMinerNum(minerNum);
		rule.setType(ruleType);
		
	}

	private String getClassName(String minerNum) throws URISyntaxException, IOException {
		String className = null;
		ClassLoader loader = R.class.getClassLoader();
		URL ruleCodeClassDictory = loader.getResource( R.class.getPackage().getName().replace('.', '/'));
		URL fileURL = FileLocator.toFileURL(ruleCodeClassDictory);

		File ruleDicFile = new File(fileURL.toURI());

		String[] ruleCodeFiles = ruleDicFile.list();
		for (String ruleCodeClass : ruleCodeFiles) {
			if(!ruleCodeClass.equals("R.class"))
			{
				String classRuleNumber = getClassRuleNumber(ruleCodeClass);
				if(minerNum.equals(classRuleNumber))
				{
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
	 	String startOfNum= null;
	 	if(subStrings[0].startsWith("0")){
	 		startOfNum = subStrings[0].replace("0", "");
	 	}else{
	 		startOfNum = subStrings[0];
	 	}
	 	if(startOfNum!=null){
	 		String endOfNum = subStrings[1];
	 		wholeRuleNumber.append(startOfNum);
	 		wholeRuleNumber.append("."+endOfNum);
	 	}
		return wholeRuleNumber.toString();
	}

	private String getRuleCategory(String minerNum) {
		Properties properties = new Properties();
		String Category=null;
		try {
			input = new FileInputStream(new File(ruleCategoryPath));
			properties.load(input);
			Iterator key = properties.keySet().iterator();
			Object  categoryObj;
			while(key.hasNext())
			{
				categoryObj = key.next();
				String keyValue = properties.getProperty((String) categoryObj);
			 	String[] keyValues = keyValue.split(",");
			 	for (int i = 0; i < keyValues.length; i++) {
					if(keyValues[i].equals(minerNum)){
						Category = categoryObj.toString();
						return Category;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Category;
	}

	public static void main(String[] args) throws IOException, URISyntaxException, JAXBException {
		GeneratingRuleintoXML generatingRuleintoXML = new GeneratingRuleintoXML();
		generatingRuleintoXML.generateRuletoXML();
		
	}
}
