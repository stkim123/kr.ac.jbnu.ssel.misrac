package test.kr.ac.jbnu.ssel.misrac.rule;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;
import test.kr.ac.jbnu.ssel.misrac.rule.testsupport.AbstractTestRule;

/**
 * Sample Implementation for testing misra-c rule that has been implemented without executing Eclipse CDT.
 * 
 * @author stkim
 *
 */
public class TestRule14_8 extends AbstractTestRule
{
	public TestRule14_8()
	{
		super("Rule14_8_Req", "TestRule14_8.C");
	}
	
	@Test
	public void test() 
	{
		try
		{
			ArrayList<ViolationMessage> violationMsgs = checkRule();
			
			System.out.println("size:"+ violationMsgs.size());
			for (ViolationMessage violationMessage : violationMsgs) {
				System.out.println(violationMessage);		
			}
			
			assertTrue(violationMsgs.size() == 2);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
