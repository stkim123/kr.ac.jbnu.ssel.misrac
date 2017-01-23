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
 * DONE!!
 * 
 * @author stkim
 *
 */
public class TestRule02_2 extends AbstractTestRule {
	public TestRule02_2() {
		super("Rule02_2_Req", "TestRule02_2.C");
	}

	@Test
	public void test() {
		try {
			ArrayList<ViolationMessage> violationMsgs = checkRule();
			System.out.println(violationMsgs);
			System.out.println("size:" + violationMsgs.size());

			assertTrue(violationMsgs.size() == 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
