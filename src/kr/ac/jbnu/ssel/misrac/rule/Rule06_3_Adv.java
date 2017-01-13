package kr.ac.jbnu.ssel.misrac.rule;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import org.eclipse.cdt.core.dom.ast.IASTDeclarator;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTTranslationUnit;

import kr.ac.jbnu.ssel.misrac.rulesupport.AbstractMisraCRule;
import kr.ac.jbnu.ssel.misrac.rulesupport.MessageFactory;
import kr.ac.jbnu.ssel.misrac.rulesupport.ViolationMessage;

/**
 * MISRA-C:2004  Rule  6.3:  (Advisory)	
 * Typedefs that indicate size and signedness should be used in place of the basic numerical types.
 * 
 * The basic numerical types of signed and unsigned variants of char, int, short, long, and float, 
 * double should not be used, but specific-length typedefs should be used. Rule helps to clarify 
 * the size of the storage, but does not guarantee portability because of the asymmetric behaviour of integral promotion. 
 * 
 * See discussion of integral promotion - section 6.10. It is still important to understand the integer 
 * size of the implementation. Programmers should be aware of the actual implementation of the 
 * typedefs under these definitions.
 * 
 * For example, the ISO (POSIX) typedefs as shown below are recommended and are used for all basic 
 * numerical and character types in this standard. For a 32-bit integer machine, these are as follows:
 * 
typedef          char   char_t;
typedef signed   char   int8_t;
typedef signed   short  int16_t;
typedef signed   int    int32_t;
typedef signed   long   int64_t;
typedef unsigned char   uint8_t;
typedef unsigned short  uint16_t;
typedef unsigned int    uint32_t;
typedef unsigned long   uint64_t;
typedef          float  float32_t;
typedef          double float64_t;
typedef long     double float128_t;

typedef String 	String128_t;	// violation

 * 
 * @author stkim
 *
 */
public class Rule06_3_Adv extends AbstractMisraCRule {

	private HashSet<String> basicNumericalTypeSet = new HashSet<String>();
	private String[] basicNumericalTypes = {"signed", "unsigned","int","short","char","long","float","double"};
	
	public Rule06_3_Adv(IASTTranslationUnit ast) {
		super("Rule06_3_Adv", false, ast);
		shouldVisitDeclarations = true;
		shouldVisitDeclarators = true;
		basicNumericalTypeSet.addAll(Arrays.asList(basicNumericalTypes));
	}

	/**
	 * if identifiers has a digit in typedef, its data type should be basic numerical types. 
	 */
	@Override
	protected int visit(IASTSimpleDeclaration simpleDeclaration) {

		String rawSignature = simpleDeclaration.getDeclSpecifier().getRawSignature();
		String[] typeDefOrDataType = rawSignature.split(" ");
		
		if( typeDefOrDataType.length > 1)
		{
			if( typeDefOrDataType[0].trim().toLowerCase().equals("typedef"))	// check if typedef exists.
			{
				IASTDeclarator[] declarators = simpleDeclaration.getDeclarators();
				
				for (IASTDeclarator iastDeclarator : declarators) {
					if( iastDeclarator.getName().toString().matches(".*\\d+.*") )	// check if there is a digit in a declarator(e.g., int32)
					{
						for(int i = 1; i < typeDefOrDataType.length ; i++)
						{
							String dataType = typeDefOrDataType[i].trim();
							if(dataType.equals("")) continue;
							
							if( !basicNumericalTypeSet.contains(dataType)) // if the data type does not exist in basic numeric type. 
							{
								String message1 = MessageFactory.getInstance().getMessage(5013);
								String msg = MessageFormat.format(message1, typeDefOrDataType[i].trim());
								violationMsgs.add(
										new ViolationMessage(this, getRuleID() + ":" + msg , simpleDeclaration));
								isViolated = true;
							}
						}
					}
				}
				
			}
		}
		return super.visit(simpleDeclaration);
	}

}
