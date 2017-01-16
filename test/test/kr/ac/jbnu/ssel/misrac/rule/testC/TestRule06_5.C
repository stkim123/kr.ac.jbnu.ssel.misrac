/* PRQA S 3205 ++ */

#include "misra.h"
#include "m2cmex.h"

extern S16 test_0605( void )
{
   struct  bitest
   {
      signed int   is_aaa: 1;           /* MISRA Violation */
      signed int         : 1;           /* MISRA Violation */
      unsigned int       : 1;
      signed int   is_bbb: 3;
      signed int         : 0;           /* MISRA Violation */
      signed int   is_ccc: 3;
      unsigned int       : 0;
      signed int   is_ddd: 3;
   } obj_0605;

   return 0;
}

