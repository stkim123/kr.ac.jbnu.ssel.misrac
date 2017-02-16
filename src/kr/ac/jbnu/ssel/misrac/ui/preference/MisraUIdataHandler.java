package kr.ac.jbnu.ssel.misrac.ui.preference;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.runtime.FileLocator;

import kr.ac.jbnu.ssel.misrac.ui.view.Constant;
import test.kr.ac.jbnu.ssel.misrac.rule.testC.CCode;

public class MisraUIdataHandler {

	private static MisraUIdataHandler instance;

	private List<Rule> ruleList;
	private HashMap<String, List<Rule>> rulesByCategory = new HashMap<String, List<Rule>>();

	private MisraUIdataHandler() {
		try {
			if (ruleList == null) {
				loadAllRules();
			}
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public static MisraUIdataHandler getInstance() {
		if (instance == null) {
			instance = new MisraUIdataHandler();
		}

		return instance;
	}
	
	public HashSet<Rule> getShouldCheckRules()
	{
		HashSet<Rule> shouldCheckRules = new HashSet<Rule>();
		for (Rule rule : ruleList) {
			if( rule.isShouldCheck())
			{
				shouldCheckRules.add(rule);
			}
		}
		return shouldCheckRules;
	}

	public List<Rule> getRules() {
		return ruleList;
	}

	public List<Rule> getRules(String category) throws JAXBException {
		return rulesByCategory.get(category);
	}

	public List<Rule> loadAllRules() throws JAXBException {
		File file = new File(Constant.dataPath);
		// IWorkspace workspace= ResourcesPlugin.getWorkspace();
		// IPath location= Path.fromOSString(file.getAbsolutePath());
		// IFile ifile= workspace.getRoot().getFileForLocation(location);
		// file = ifile.getRawLocation().makeAbsolute().toFile();

		JAXBContext jc = JAXBContext.newInstance(Rules.class);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		Rules rules = (Rules) unmarshaller.unmarshal(file);
		ruleList = rules.getRule();

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

		return ruleList;
	}


	public static void main(String[] args) throws JAXBException {
		MisraUIdataHandler misraUIdataHandler = new MisraUIdataHandler();
		List<Rule> ruleList = misraUIdataHandler.loadAllRules();
		for (Rule rule : ruleList) {
			System.out.println(rule.getMinerNum());
		}
	}

}
