#include "misra.h"
#include "m2cmex.h"

extern S16 test_1407( void )            /* MISRA Violation */
{
   if ( s16a > 0 )
   {
      return 0;
   }
   else
   {
      return 1;
   }
}
