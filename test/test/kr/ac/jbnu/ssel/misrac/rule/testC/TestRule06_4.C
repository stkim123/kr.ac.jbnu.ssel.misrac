/*
 * rule06.4_req.c
 *
 *  Created on: 2017. 2. 27.
 *      Author: User
 */

/* PRQA S 3205 ++ */

#include "misra.h"
#include "m2cmex.h"

extern S16 test_0604( void )
{
   typedef enum { A, B, C } e_t;

   struct  bitest
   {
      signed char     is_ctr : 3;     /* MISRA Violation */
      unsigned char   is_nul : 3;     /* MISRA Violation */
      int             is_set : 5;     /* MISRA Violation */
      signed int      is_key : 3;
      unsigned int    is_big : 3;
      unsigned long   is_dep : 3;     /* MISRA Violation */
      signed long     is_bad : 2;     /* MISRA Violation */
      e_t             is_enu : 2;     /* MISRA Violation */
      short           is_sh1 : 2;     /* MISRA Violation */
      signed short    is_sh2 : 2;     /* MISRA Violation */
      unsigned short  is_sh3 : 2;     /* MISRA Violation */
   } obj_0604;

   return 0;
}

