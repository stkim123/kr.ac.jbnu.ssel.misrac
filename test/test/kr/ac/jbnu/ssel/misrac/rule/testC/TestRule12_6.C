/*
 * TestRule12_6.C
 *
 *  Created on: 2017. 1. 18.
 *      Author: stkim
 */


/* PRQA S 2982 ++ */

#include "misra.h"
#include "m2cmex.h"

typedef unsigned char   boolean;


extern S16 test_1206( void )
{
   s16r = ( s16a < s16b ) &  ( s16c > s16d );                /* MISRA Violation */
   s16r = ( s16a < s16b ) |  ( s16c > s16d );                /* MISRA Violation */
   s16r = ( s16c > s16d) * (s16a < s16b);                    /* MISRA Violation */
   s16r = ( s16c > s16d) + s16b;                             /* MISRA Violation */
   s16r = s16r + (s16c > s16d);                              /* MISRA Violation */
   bla  = ( s16a + s16b ) && ( s16c + s16d );                /* MISRA Violation */
   bla  = ( s16a + s16b ) || ( s16c + s16d );                /* MISRA Violation */
   bla  = ( s16a + s16b ) && ( s16c > s16d );                /* MISRA Violation */
   bla  = ( s16a > s16b ) && ( s16c + s16d );                /* MISRA Violation */
   bla  = ! ( s16a + s16b );                                 /* MISRA Violation */
   bla  = (s16a + s16b) > (s16c > s16d);                     /* MISRA Violation */
   bla  = (s16a > s16b) > (s16c + s16d);                     /* MISRA Violation */
   bla  = (s16a > s16b) > (s16c > s16d);                     /* MISRA Violation */
   s16r = ~(s16a > s16b);                                    /* MISRA Violation */
   bla  = s16a && ( s16c < s16d );                           /* MISRA Violation */
   bla  = !s16a;                                             /* MISRA Violation */


   boolean A,B;
   A = !b;			   //violation
   	   	   	   	   	   // Operand of logical ! operator is not an 'effectively Boolean' expression.
   	   	   	   	   	   // MISRA-C:2004 Rule 12.6; REFERENCE - ISO:C90-6.3.3.3 Unary Arithmetic Operators

   return s16r;
}

