package kr.ac.jbnu.ssel.misrac.rule.testsupport;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;
import org.eclipse.cdt.core.dom.ast.gnu.cpp.GPPLanguage;
import org.eclipse.cdt.core.index.IIndex;
import org.eclipse.cdt.core.model.ILanguage;
import org.eclipse.cdt.core.parser.DefaultLogService;
import org.eclipse.cdt.core.parser.FileContent;
import org.eclipse.cdt.core.parser.IParserLogService;
import org.eclipse.cdt.core.parser.IScannerInfo;
import org.eclipse.cdt.core.parser.IncludeFileContentProvider;
import org.eclipse.cdt.core.parser.ScannerInfo;
import org.eclipse.core.runtime.FileLocator;

import kr.ac.jbnu.ssel.misrac.rule.Rule08_1_Req;
import kr.ac.jbnu.ssel.misrac.rule.testC.CCode;
import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

public class TestResourceMgr
{
	public static IASTTranslationUnit getTranslationUnit(String cfileName) throws Exception
	{
		String path = CCode.class.getPackage().getName().replace('.', '/') + "/" + cfileName;
		
		ClassLoader loader = CCode.class.getClassLoader();
		URL pathURL = loader.getResource(path);
		URL fileURL = FileLocator.toFileURL(pathURL);

		FileContent fc = FileContent.createForExternalFileLocation(fileURL.getPath());
		IASTTranslationUnit ast = creatIASTTranslationUnit(fc);
		return ast;
	}
	
	public static IASTTranslationUnit creatIASTTranslationUnit(FileContent input) throws Exception { 
		  Map<String, String> macroDefinitions = new HashMap<String, String>(); 
		  IScannerInfo si = new ScannerInfo(macroDefinitions, new String[]{TestEnv.MINGW_INCLUDE_PATH}); 
		  IncludeFileContentProvider ifcp = IncludeFileContentProvider.getEmptyFilesProvider(); 
		  IIndex idx = null; 
		  int options = ILanguage.OPTION_IS_SOURCE_UNIT; 
		  IParserLogService log = new DefaultLogService(); 
		  return GPPLanguage.getDefault().getASTTranslationUnit(input, si, ifcp, idx, options, log); 
		 } 
	
	public static void main(String[] args) throws Exception
	{
		IASTTranslationUnit astT = TestResourceMgr.getTranslationUnit("TestRule08_1.C");
		
		ArrayList<ViolationMessage> violationMessages = new ArrayList<ViolationMessage>();
		
		AbstractMisraCRule rule = new Rule08_1_Req(astT);
		rule.checkRule();
		if( rule.isViolated())
		{
			ViolationMessage[] violationMsgs = rule.getViolationMessages();
			violationMessages.addAll(Arrays.asList(violationMsgs));
		}
		
		System.out.println("violationMessages:"+ violationMessages);
		
	}
}
