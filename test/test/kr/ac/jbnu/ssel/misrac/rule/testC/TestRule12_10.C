
/* PRQA S 2984 ++ */

#include "misra.h"
#include "m2cmex.h"

#define A (-5)
#define B 3

extern S16 test_1210( void )
{
   S16 x;
   S16 y;
   S16 i;

   y = ( x = A, x + B );              /* MISRA Violation */

   for ( i = 0, ++y; i < B; ++i )     /* MISRA Violation */
   {
      ++y;
   }

   return y;
}
