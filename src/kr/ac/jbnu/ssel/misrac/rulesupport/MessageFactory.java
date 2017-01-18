package kr.ac.jbnu.ssel.misrac.rulesupport;

public class MessageFactory {

	private static MessageFactory instance;

	private MessageFactory() {
	}

	public static MessageFactory getInstance() {
		if (instance == null) {
			instance = new MessageFactory();
		}

		return instance;
	}

	public String getMessage(int messageNum) {
		String msg = null;

		switch (messageNum) {
		case 1011:
			msg = "[C99] Use of '//' comment.";
			break;
		case 5126:
			msg = "The library functions abort, exit, getenv and system from library <stdlib.h> shall not be used.";
			break;
		case 750:
			msg = "A union type specifier has been defined.";
			break;
		case 759:
			msg = "An object of union type has been defined.";
			break;
		case 777:
			msg = "[U] External identifier does not differ from other identifier(s) (e.g. '%s') within the specified number of significant characters.";
			break;
		case 779:
			msg = "[U] Identifier does not differ from other identifier(s) (e.g. '%s') within the specified number of significant characters.";
			break;
		case 1506:
			msg = "The identifier '%1s' is declared as a typedef and is used elsewhere for a different kind of declaration.";
			break;
		case 1507:
			msg = "	'%1s' is used as a typedef for different types.";
			break;
		case 1508:
			msg = "The typedef '%1s' is declared in more than one location.";
			break;
		case 3448:
			msg = "	Declaration of typedef '%s' is not in a header file although it is used in a definition or declaration with external linkage.";
			break;
		case 3417:
			msg = "	The comma operator has been used outside a 'for' statement.";
			break;
		case 3418:
			msg = "	The comma operator has been used in a 'for' statement.";
			break;
		case 5124:
			msg = "	The input/output library <stdio.h> shall not be used in production code.";
			break;
		case 2050:
			msg = "	The 'int' type specifier has been omitted from a function declaration.";
			break;
		case 2051:
			msg = " The 'int' type specifier has been omitted from an object declaration.";
			break;
		case 2001:
			msg = "	A 'goto' statement has been used.";
			break;
		case 2889:
			msg = "	This function has more than one 'return' path.";
			break;
		case 5119:
			msg = "	The error indicator errno shall not be used.";
			break;
		case 5125:
			msg = " The library functions atof, atoi and atol from library <stdlib.h> shall not be used.";
			break;
		case 5120:
			msg = " The macro offsetof, in library <stddef.h>, shall not be used. ";
			break;
		case 5123:
			msg = " The signal handling facilities of <signal.h> shall not be used. ";
			break;
		case 3659:
			msg = "	Unnamed zero-width bit-field declared with a signed type.";
			break;
		case 3660:
			msg = " Named bit-field consisting of a single bit declared with a signed type.";
			break;
		case 3665:
			msg = " Unnamed bit-field consisting of a single bit declared with a signed type.";
			break;
		case 0771:
			msg = "More than one 'break' statement has been used to terminate this iteration statement.";
			break;
		case 0770:
			msg = "A 'continue' statement has been used.";
			break;

		case 2212:
			msg = "Body of control statement is not enclosed within braces.";
			break;

		case 2214:
			msg = "Body of control statement is on the same line and is not enclosed within braces.";
			break;
			
		case 5013:
			msg = "Use of basic type ''{0}''.";
			break;

		case 3002:
			msg = "Defining '%s()' with an identifier list and separate parameter declarations is an obsolescent feature.";
			break;
		case 3334:
			msg = "	This declaration of '%s' hides a more global declaration.";
			break;
		case 3335:
			msg = "No function declaration. Implicit declaration inserted: 'extern int %s();'.";
			break;
		case 3450:
			msg = "Function '%s', with internal linkage, is being defined without a previous declaration.";
			break;				
		case 0624:
			msg = "Function '%s' is declared using typedefs which are different to those in a previous declaration.";
			break;				
		case 1331:
			msg = "Type or number of arguments doesn't match previous use of the function.";
			break;				
		case 1332:
			msg = "Type or number of arguments doesn't match prototype found later.";
			break;				
		case 1333:
			msg = "Type or number of arguments doesn't match function definition found later.";
			break;				
		case 3320:
			msg = "Type of argument no. %s differs from its type in definition of function.";
			break;				
		case 3675:
			msg = "Function parameter declared with type qualification which differs from previous declaration.";
			break;		
		case 2547:
			msg = "	This declaration of tag '%s' hides a more global declaration.";
			break;
		case 2004:
			msg = "	No concluding 'else' exists in this 'if'-'else'-'if' statement.";
			break;
		case 3402:
			msg = " Braces are needed to clarify the structure of this 'if'-'if'-'else' statement.";
			break;
		case 3601:
			msg = "Trigraphs (??x) are an ISO feature.";
			break;
		case 5118:
			msg = "Dynamic heap memory allocation shall not be used.";
			break;
		case 5127:
			msg = "The time handling functions of library <time.h> shall not be used.";
			break;
		case 5122:
			msg = "The setjmp macro and the longjmp function shall not be used.";
			break;
		case 841:
			msg = "Using '#undef'.";
			break;
		case 842:
			msg = "	Using #define or #undef inside a function.";
			break;
		case 813:
			msg = "	[U] Using any of the characters ' \" or /* in '#include <%s>' gives undefined behaviour.";
			break;
		case 814:
			msg = "	[U] Using the characters ' or /* in '#include \"%s\"' gives undefined behaviour.";
			break;
		case 831:
			msg = "	[E] Use of '\\' in this '#include' line is a PC extension - this usage is non-portable.";
			break;
		case 3415:
			msg = "Right hand operand of '&&' or '||' is an expression with possible side effects.";
			break;
		case 341:
			msg = "Using the stringify operator '#'.";
			break;
		case 342:
			msg = "Using the glue operator '##'.";
			break;
		}
		
		return msg;
	}

}
