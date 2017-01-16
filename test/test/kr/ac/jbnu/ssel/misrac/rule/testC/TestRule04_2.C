/*
 * Rule4_2.C
 *
 *  Created on: 2017. 1. 16.
 *      Author: stkim
 */


int main()
{
	char* str = "(Date should be in the form ??-??-??)";	// Violation
	char * str2 = "??="; 	// Violation
	char * str21 = "#";		// Not-violation but can't capture

	char * str3 = "??>";	// Violation
	char * str31 = "}";		// Not-violation but can't capture

	char * str4 = "??!";	// Violation
	char * str41 = "|";		// Not-violation but can't capture

	char * str5 = "??/";	// Violation
	char * str51 = "\\";	// Not-violation but can't capture

	char * str6 = "??";
}
