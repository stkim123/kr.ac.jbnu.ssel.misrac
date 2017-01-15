package kr.ac.jbnu.ssel.misrac.rule;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import kr.ac.jbnu.ssel.misrac.rule.testsupport.AbstractTestRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * Sample Implementation for testing misra-c rule that has been implemented without executing Eclipse CDT.
 * 
 * @author stkim
 *
 */
public class TestRule08_1 extends AbstractTestRule
{
	public TestRule08_1()
	{
		super("Rule08_1_Req", "TestRule08_1.C");
	}
	
	@Test
	public void test() 
	{
		try
		{
			ArrayList<ViolationMessage> violationMsgs = checkRule();
			System.out.println(violationMsgs);
			System.out.println("size:"+ violationMsgs.size());
			
			assertTrue(violationMsgs.size() == 4);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
