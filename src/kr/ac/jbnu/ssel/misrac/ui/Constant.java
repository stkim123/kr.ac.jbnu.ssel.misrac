package kr.ac.jbnu.ssel.misrac.ui;

import kr.ac.jbnu.ssel.resources.Resources;

/**
 * 
 * @author Taeyoung Kim
 */
public class Constant {

	//MISRA-C Table View Column Name
	public static final String ruleNum = "Rules Num";
	public static final String line = "Line";
	public static final String errorCode = "Error Code";
	public static final String errorMessage = "Error Message";
	
	//Data Path
	public static final String rule_description_path = EclipseUtil.getEclipsePackageDirOfClass(Resources.class)+ "\\rule_description.xml"; 
	
	//
	public static final String notImplement = "(notImplement)";
}
