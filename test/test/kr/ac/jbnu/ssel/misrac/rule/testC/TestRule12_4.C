/*
 * TestRule12_4.C
 *
 *  Created on: 2017. 1. 17.
 *      Author: stkim
 */

void main()
{
	if ( ishigh && ( x == i++ ) )		/* Not compliant                 */
										// violation
	{

	}

	if ( ishigh && ( x == f( x ) ) )  /* Only acceptable if f( x ) is
                                     known to have no side effects */
	{

	}

	for( i = 0; i < 100; i++)
	{

	}

	i = 5 || j++;			// violation

}
