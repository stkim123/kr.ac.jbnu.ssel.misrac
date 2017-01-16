/* PRQA S 2983,3203 ++ */

#include <stdlib.h>
#include "misra.h"
#include "m2cmex.h"

extern S16 test_2010( void )
{
   F64 d;
   S16 i;
   S32 l;

   d = atof( "12.34" );          /* MISRA Violation */
   i = atoi( "3456" );           /* MISRA Violation */
   l = atol( "12345678" );       /* MISRA Violation */

   return 0;
}
