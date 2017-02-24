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
 * TODO : 실제 실행시 경고문구 개수와 다른 값이 출력됨.
 * 
 * 
 * @author sangjin
 *
 */
public class TestRule16_8 extends AbstractTestRule {
	public TestRule16_8() {
		super("Rule16_8_Req", "TestRule16_8.C");
	}

	@Test
	public void test() {
		try {
			ArrayList<ViolationMessage> violationMsgs = checkRule();

			System.out.println("size:" + violationMsgs.size());
			for (ViolationMessage violationMessage : violationMsgs) {
				System.out.println(violationMessage);
			}

			assertTrue(violationMsgs.size() == 20);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}