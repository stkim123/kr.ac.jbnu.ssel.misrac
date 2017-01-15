package kr.ac.jbnu.ssel.misrac.rule.testsupport;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rule.R;
import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

public class AbstractTestRule
{
	private String ruleClsName;
	private String cCodeFile;

	public AbstractTestRule(String ruleClsName, String cCodeFile)
	{
		this.ruleClsName = ruleClsName;
		this.cCodeFile = cCodeFile;
	}

	public ArrayList<ViolationMessage> checkRule() throws Exception
	{
		IASTTranslationUnit astT = TestResourceMgr.getTranslationUnit(cCodeFile);
		ArrayList<ViolationMessage> violationMessages = new ArrayList<ViolationMessage>();

		AbstractMisraCRule rule = createMisraCRule(ruleClsName, astT);
		rule.checkRule();
		if (rule.isViolated())
		{
			ViolationMessage[] violationMsgs = rule.getViolationMessages();
			violationMessages.addAll(Arrays.asList(violationMsgs));
		}

		return violationMessages;
	}
	
	private AbstractMisraCRule createMisraCRule(String ruleClsName, IASTTranslationUnit astT)
	{
		String ruleClassWithPackage = R.class.getPackage().getName() + "." + ruleClsName;

		System.out.println("ruleClassWithPackage:" + ruleClassWithPackage);
		
		AbstractMisraCRule rule = null;
		try
		{
			rule = (AbstractMisraCRule) Class.forName(ruleClassWithPackage)
					.getConstructor(IASTTranslationUnit.class).newInstance(astT);
		} catch (InstantiationException e)
		{
			e.printStackTrace();
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
		} catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		} catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		} catch (SecurityException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return rule;
	}
}
