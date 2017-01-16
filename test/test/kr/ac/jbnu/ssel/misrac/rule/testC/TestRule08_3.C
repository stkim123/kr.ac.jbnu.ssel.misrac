/*
 * Rule8_3.C
 *
 *  Created on: 2017. 1. 14.
 *      Author: stkim
 */



/* PRQA S 2982,3203,3447,3408 ++ */

#include "misra.h"
#include "m2cmex.h"

typedef int SPEC16;

static S16 test_0803c();                   /* MISRA Violation - Rule 16.5 */
static SPEC16 test_0803e(void);

static S16 test_0803c( px )                /* MISRA Violation  - Rule 8.1 */
const S16 *px;
{
   return *px;
}

extern S16 test_0803( void )
{
   S16 r;

   r = test_0803a( 1 );                    /* MISRA Violation - Rule 8.1 */
   r = test_0803a( 1, 2 );                 /* MISRA Violation */
   r = test_0803b( 1, 2 );                 /* MISRA Violation - also Rule 8.1 */
   r = test_0803c( 2 );                    /* MISRA Violation */
   r = test_0803d( 1, 2 );                 /* MISRA Violation - also Rule 8.1 */
   r = test_0803e( );

   return r;
}

extern S16 test_0803b( void );

extern S16 test_0803d( S16 x )
{
   return x + 1;
}

static S16 test_0803e(void)                /* MISRA Violation */
{
    return 1;
}

