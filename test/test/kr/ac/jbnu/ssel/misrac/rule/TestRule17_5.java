package test.kr.ac.jbnu.ssel.misrac.rule;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;
import test.kr.ac.jbnu.ssel.misrac.rule.testsupport.AbstractTestRule;

/**
 * Sample Implementation for testing misra-c rule that has been implemented
 * without executing Eclipse CDT.
 * 
 * 
 * DONE!!
 * 
 * 
 * @author sangjin
 *
 */
public class TestRule17_5 extends AbstractTestRule {
	public TestRule17_5() {
		super("Rule17_5_Adv", "TestRule17_5.C");
	}

	@Test
	public void test() {
		try {
			ArrayList<ViolationMessage> violationMsgs = checkRule();

			System.out.println("size:" + violationMsgs.size());
			for (ViolationMessage violationMessage : violationMsgs) {
				System.out.println(violationMessage);
			}

			assertTrue(violationMsgs.size() == 15);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}