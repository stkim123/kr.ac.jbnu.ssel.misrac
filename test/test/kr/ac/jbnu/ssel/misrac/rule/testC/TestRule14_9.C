/*
 * rule14.9_req.c
 *
 *  Created on: 2017. 1. 16.
 *      Author: User
 */
int main(){

	if ( test1 )
	{
	    x = 1;              /* Even a single statement must be in braces */
	}
	else if ( test2 )       /* No need for braces in else if */
	{
	    x = 0;              /* Single statement must be in braces */
	}
	else
	    x = 3; /* This was (incorrectly) not enclosed in braces */
	    y = 2; /* This line was added later but, despite the appearance
	              (from the indent) it is actually not part of the else, and
	               is executed unconditionally */

}
