package kr.ac.jbnu.ssel.misrac.rule;

import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.MisraCRuleGroup;

/**
 * MISRA-C:2004  Rule  1.1:  (Required)	[next]		
 * All code shall conform to ISO/IEC 9899:1990 C programming language, ISO 9899, 
 * amended and corrected by ISO/IEC 9899/COR1:1995, ISO/IEC 9899/AMD1:1995, and 
 * ISO/IEC 9899/COR2: 1996192
 * 
 * These guidelines are based on ISO/IEC 9899:1990 [2] amended and corrected by ISO/IEC 9899/COR1:1995 [4], 
 * ISO/IEC 9899/AMD1:1995 [5], and ISO/IEC 9899/COR2: 1996 [6]. No claim is made as to their suitability 
 * with respect to the ISO 9899:1999 [8] version of the standard. Any reference in this document to 
 * 'Standard C' refers to the older ISO/IEC 9899:1990 [2] standard.
 * 
 * It is recognised that it will be necessary to raise deviations (as described in section 4.3.2) 
 * to permit certain language extensions, for example to support hardware specific features.
 * 
 * Deviations are required if the environmental limits as specified in ISO/IEC 9899:1990 5.2.4 [2] 
 * are exceeded, other than as allowed by Rule .
 * 
 * PRL Commentary:
 * 
 * Enforcement of this rule is implemented as follows:
 * Messages which identify language constraint errors.
 * Messages which identify constructs which are supported by the ISO:C99 standard but not by the ISO:C90 standard
 * Messages which identify constructs which are extensions to the ISO C language.
 * Messages which identify constructs where the ISO:C90 environmental limits are exceeded.
 * 4 messages are specifically excluded from this enforcement:
 * 
 * Messages 776 and 778 are disabled in accordance with the concessions described in Rule 1.4.
 * 
 * Messages 815 and 816 are disabled because the conformance limits imposed by the ISO standard 
 * to attain total portability for include file names are excessively restrictive. See Rule 19.2.
 * 
 *
 <code>
 Example Code:
 #include "misra.h"
 #include "m2cmex.h"
 extern S16 test_0101( void )
 {
    return 0;
    }
 </code>
 *
 *Related Rules
 *Rule  2.2	Source code shall only use C-style comments.
 *Rule  5.4	A tag name shall be a unique identifier.
 *Rule  6.4	Bit fields shall only be defined to be of type unsigned int or signed int.
 *Rule  8.4	If objects or functions are declared more than once their types shall be compatible.
 *Rule  16.6	The number of arguments passed to a function shall match the number of parameters.
 *Rule  19.2	Non-standard characters should not occur in header file names in #include directives.
 * *Rule  19.8	A function-like macro shall not be invoked without all of its arguments.
 *
 *
 * [STATUS: Not Implemented Yet, Use JavaCC later]
 *  
 * @author STKim2
 *
 */
public class Rule01_1_Req extends MisraCRuleGroup
{
    public Rule01_1_Req(IASTTranslationUnit ast) {
	super("Rule01_1", false, ast);
	
	addMisraCRule(new Rule02_2_Req(ast));
	
    }

}
