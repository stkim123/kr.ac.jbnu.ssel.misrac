/*
 * rule16.3_req.c
 *
 *  Created on: 2017. 1. 25.
 *      Author: User
 */

/* PRQA S 2982 ++ */

#include "misra.h"
#include "m2cmex.h"

static S16 test_1603a( const S16 *x, const S16 *y );
static S16 test_1603b( const S16 *,  const S16 * );            /* MISRA Violation */
static S16 test_1603c( const S16 *u, const S16 * );            /* MISRA Violation */


extern S16 test_1603( void )
{
   S16 r;
   r = test_1603a( &s16a, &s16b );
   r = test_1603b( &s16a, &s16b );
   r = test_1603c( &s16a, &s16b );
   return r;
}

static S16 test_1603a( const S16 *x, const S16 *y )
{
   return *x + *y;
}

static S16 test_1603b( const S16 *a, const S16 *b )
{
   S16 (* test_1603p)(const S16 *, const S16 * );              /* MISRA Violation */
   S16 r;

   test_1603p = &test_1603a;
   r = test_1603p(a, b);

   return r;
}

static S16 test_1603c( const S16 *u, const S16 *v )
{
   return *u - *v;
}
