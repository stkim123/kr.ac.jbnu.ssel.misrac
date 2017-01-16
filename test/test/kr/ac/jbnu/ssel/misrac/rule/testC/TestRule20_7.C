/*
 * TestRule20_7.C
 *
 *  Created on: 2017. 1. 16.
 *      Author: stkim
 */


#include        <setjmp.h>

#include "misra.h"
#include "m2cmex.h"


extern S16 test_2007( void )
{
   S16     istat;
   jmp_buf myenv;

   istat =setjmp( myenv );            /* MISRA Violation */
   longjmp( myenv, 9 );                /* MISRA Violation */

   return istat;
}
