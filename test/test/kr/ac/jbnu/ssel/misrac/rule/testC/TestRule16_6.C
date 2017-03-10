/*
 * rule16.6_req.c
 *
 *  Created on: 2017. 2. 21.
 *      Author: User
 */

/* PRQA S 3206 ++ */

#include "misra.h"
#include "m2cmex.h"


static S16 test_1606a( S16 i, S16 j );
static S16 test_1606b( S16 k );
static S16 test_1606c();

static S16 test_1606c( a )
S16 a;
{
   return a;
}

extern S16 test_1606( void )
{
   S16 r;

   r = test_1606a( 1 );             /* MISRA Violation */
   r += test_1606b( 1, 1 );         /* MISRA Violation */
   r += test_1606c( 1, 1 );         /* MISRA Violation */

   return r;
}

static S16 test_1606a( S16 i, S16 j )
{
   return i + j;
}

static S16 test_1606b( S16 k )
{
   return k;
}
