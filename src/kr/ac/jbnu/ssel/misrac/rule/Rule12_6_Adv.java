package kr.ac.jbnu.ssel.misrac.rule;
/**
 * MISRA-C:2004  Rule  12.6:  (Advisory)	[next]		
 * 
 * The operands of logical operators (&&, || and !) should be effectively Boolean. 
 * Expressions that are effectively Boolean should not be used as operands to operators other than 
 * (&&, ||, !, =, ==, != and ?:).
 * 
 * The logical operators &&, || and ! can be easily confused with the bitwise 
 * operators &, | and ~. See 'Boolean expressions' in the glossary.
 * 
 */
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;

public class Rule12_6_Adv extends AbstractMisraCRule {

    public Rule12_6_Adv(IASTTranslationUnit ast) {
	super("Rule12_6_Adv", false, ast);
	shouldVisitExpressions = true;
    }
    
    //TODO

}
