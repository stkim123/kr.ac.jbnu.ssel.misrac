/*
 * rule16.8_req.c
 *
 *  Created on: 2017. 2. 21.
 *      Author: User
 */
/* PRQA S 2983,3203 ++ */

#include "misra.h"
#include "m2cmex.h"

static S16 test_1608a( S16 j );


static S16 test_1608a( S16 j )
{
   S16 r;

   r = j + 2;

   return;                      /* MISRA Violation */
}

static test_1608b( void )       /* MISRA Violation - Rule 8.1 */
{                               /* MISRA Violation - Rule 8.2 */
   return;                      /* MISRA Violation */
}

static test_1608c( void )       /* MISRA Violation - Rule 8.1 */
{								/* MISRA Violation - Rule 8.2 */
								/* MISRA Violation */
}

extern S16 test_1608( void )    /* MISRA Violation */
{
   S16 r;

   r = test_1608a( 1 )         +
       test_1608b()            +
       test_1608c();
}
