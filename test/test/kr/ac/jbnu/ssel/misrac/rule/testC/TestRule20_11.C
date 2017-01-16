
/* PRQA S 2983,3203 ++ */

#include <stdlib.h>
#include "misra.h"
#include "m2cmex.h"

extern S16 test_2011( void )
{
   S16         i;
   PC         *v;

   i = system( "test" );       /* MISRA Violation */

   if ( i < -99 )
   {
      abort();                 /* MISRA Violation */
   }

   if ( i == 40 )
   {
      exit( 1 );               /* MISRA Violation */
   }

   v = getenv( "test" );       /* MISRA Violation */

   return 0;                   /* MISRA Violation */
}

