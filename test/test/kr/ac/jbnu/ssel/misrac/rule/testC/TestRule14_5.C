/* PRQA S 2981 ++ */

#include "misra.h"
#include "m2cmex.h"


extern S16 test_1405( void )
{
   S16 r = 0;
   S16 n = 0;

   for ( n = 0; n < 5; n++ )
   {
      if ( n > 2 )
      {
         continue;           /* MISRA Violation */
      }
      ++r;
   }

   r = 1;

   return r;
}

