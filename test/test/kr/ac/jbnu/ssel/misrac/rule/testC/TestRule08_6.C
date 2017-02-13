/*
 * rule8.6_req.c
 *
 *  Created on: 2017. 2. 13.
 *      Author: User
 */
/* PRQA S 3408,3447 ++ */

#include "misra.h"
#include "m2cmex.h"

extern S16 test_0806( void )
{
   extern S16 test_0806a( void );        /* MISRA Violation */
   S16 r;

   r = test_0806a();

   return r;
}

extern S16 test_0806a( void )            /* MISRA Violation - Rule 8.8 */
{
   return 1;
}
