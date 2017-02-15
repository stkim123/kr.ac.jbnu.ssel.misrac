package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTPreprocessorObjectStyleMacroDefinition;
import org.eclipse.cdt.core.dom.ast.IASTPreprocessorPragmaStatement;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004 Rule 02.1: (Required) Assembly language shall be encapsulated
 * and isolated.
 * 
 * 
 * Where assembly language instructions are required it is recommended that they
 * be encapsulated and isolated in either (a) assembler functions, (b) C
 * functions or (c) macros. For reasons of efficiency it is sometimes necessary
 * to embed simple assembly language instructions in-line, for example to enable
 * and disable interrupts. If it is necessary to do this for any reason, then it
 * is recommended that it be achieved by using macros. Note that the use of
 * in-line assembly language is an extension to standard C, and therefore also
 * requires a deviation against Rule .
 * 
 * TODO
 * 
 * @author kang
 */

public class Rule02_1_Req extends AbstractMisraCRule {
	private static final String define = "#define";
	private static final String Assembly = "asm";
	// private static final String[] Assembly_lang = { "setjmp", "_setjmp", "longjmp" };

	public Rule02_1_Req(IASTTranslationUnit ast) {
		super("Rule02_1_Req", false, ast);
		shouldVisitPreprocessor = true;
	}

//	@Override
//	protected int visit(IASTPreprocessorObjectStyleMacroDefinition preMacroDef) {
//		IASTPreprocessorPragmaStatement
//
//		// Parser Preprocessor
//		String rawSignature = preMacroDef.getRawSignature();
//		String[] preMacroSt = rawSignature.split(" ");
//
//		if (preMacroSt[2].equals(Assembly)) {
//			if (preMacroSt[0] != define) {
//				isViolated = true;
//
//				String message = MessageFactory.getInstance().getMessage(3006);
//				violationMsgs.add(new ViolationMessage(this, getRuleID() + ":" + message + "--" + preMacroDef , preMacroDef));
//
//			}
//		}
//		return super.visit(preMacroDef);
//	}

}
