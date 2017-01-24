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
 * @author sangjin
 *
 */
public class TestRule16_2 extends AbstractTestRule {
	public TestRule16_2() {
		super("Rule16_2_Req", "TestRule16_2.C");
	}

	@Test
	public void test() {
		try {
			ArrayList<ViolationMessage> violationMsgs = checkRule();

			System.out.println("size:" + violationMsgs.size());
			for (ViolationMessage violationMessage : violationMsgs) {
				System.out.println(violationMessage);
			}

			assertTrue(violationMsgs.size() == 2);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
