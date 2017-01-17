/*
 * TestRule12_5.C
 *
 *  Created on: 2017. 1. 17.
 *      Author: stkim
 */


int main()
{
if ( ( x == 0 ) && ishigh ){}   /* make x == 0 primary                  */
if ( x == 0  && ishigh ){}		// violation

if ( x || y || z ){}            /* exception allowed, if x, y and z are
                                 Boolean                              */

if ( x || ( y && z ) ){}        /* make y && z primary                  */

if ( x && ( !y ) ){}            /* make !y primary                      */

if ( x &&  !y  ){}				// violation

if ( ( is_odd ( y ) ) && x ){}  /* make call primary                    */
if ( is_odd ( y )  && x   ){}	// violation


if ( ( x > c1 ) && ( y > c2 ) && ( z > c3 ) ){}      /* Compliant       */
if ( ( x > c1 ) && ( y > c2 ) || ( z > c3 ) ){}      /* not compliant   */
if ( ( x > c1 ) && ( ( y > c2 ) || ( z > c3 ) ) ){}  /* Compliant
}														extra () used */
