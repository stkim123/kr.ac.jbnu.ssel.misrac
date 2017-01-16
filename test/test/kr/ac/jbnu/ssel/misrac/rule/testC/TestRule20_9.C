/*	rule20.9_req
 *	Created on: 2017. 1. 4.
 *  Author: User
 */


/* PRQA S 2983,3203 ++ */

#include <stdio.h>                      /* MISRA Violation */
#include "misra.h"
#include "m2cmex.h"

extern S16 test_2009( void )
{
   FILE *fp;

   fp = fopen( "test", "r" );

   return 0;
}
