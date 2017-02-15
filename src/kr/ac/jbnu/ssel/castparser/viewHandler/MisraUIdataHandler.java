package kr.ac.jbnu.ssel.castparser.viewHandler;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import kr.ac.jbnu.ssel.castparser.preference.Rule;
import kr.ac.jbnu.ssel.castparser.preference.Rules;
import kr.ac.jbnu.ssel.castparser.view.Constant;
import kr.ac.jbnu.ssel.misrac.rule.R;
import test.kr.ac.jbnu.ssel.misrac.rule.testC.CCode;

public class MisraUIdataHandler {

	public Rules allDataLoad() throws JAXBException {
		File file = new File(Constant.dataPath);
		// IWorkspace workspace= ResourcesPlugin.getWorkspace();
		// IPath location= Path.fromOSString(file.getAbsolutePath());
		// IFile ifile= workspace.getRoot().getFileForLocation(location);
		// file = ifile.getRawLocation().makeAbsolute().toFile();

		JAXBContext jc = JAXBContext.newInstance(Rules.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Rules rules = (Rules) unmarshaller.unmarshal(file);
		return rules;
	}

	public String getCode(String ruleNumber) {
		String code = null;
		String[] ruleNumArry = ruleNumber.split("Rule");
		String minerNum = ruleNumArry[1];
		String ruleClassWithPackage = null;
		// 1. Compare between ruleNumber of index in the table and ruleNumber in
		// the C code
		// 2. get code if the number match number of index of table
		String[] cCodeFiles = getCodeList();
		

		return code;
	}

	private String[] getCodeList() {
		String[] cCodeFiles= null;
		ClassLoader loader = CCode.class.getClassLoader();
		URL ruleCodeClassDictory = loader.getResource(CCode.class.getPackage().getName().replace('.', '/'));
		try {
			URL fileURL = FileLocator.toFileURL(ruleCodeClassDictory);
			File codeDicFile = new File(fileURL.toURI());
			cCodeFiles = codeDicFile.list();
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cCodeFiles;
	}

	private String getcClassRuleNumber(String cCodeClass) {
		StringBuffer wholeRuleNumber = new StringBuffer();
		String ruleNum = cCodeClass.substring(6);
		String[] removedClass = ruleNum.split(".C");
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

	public static void main(String[] args) throws JAXBException {
		MisraUIdataHandler misraUIdataHandler = new MisraUIdataHandler();
		Rules rules = misraUIdataHandler.allDataLoad();
		List<Rule> ruleList = rules.getRule();
		for (Rule rule : ruleList) {
			System.out.println(rule.getMinerNum());
		}
	}

	public List<Rule> DataLoad(String category) throws JAXBException {
		List<Rule> subRules = new ArrayList<Rule>();
		File file = new File(Constant.dataPath);
		JAXBContext jc = JAXBContext.newInstance(Rules.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Rules rules = (Rules) unmarshaller.unmarshal(file);
		for (Rule rule : rules.getRule()) {
			if (rule.getCategory() != null) {
				if (rule.getCategory().equals(category)) {
					subRules.add(rule);
				}
			}
		}
		return subRules;
	}
}
