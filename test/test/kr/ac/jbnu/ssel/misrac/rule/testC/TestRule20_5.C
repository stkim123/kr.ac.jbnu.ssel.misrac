#include <errno.h>
#include "misra.h"
#include "m2cmex.h"

extern S16 test_2005( void )
{
   S16 r = 0;

   if ( errno != 0 )        /*  MISRA violation  */
   {
      r = 1;
   }

   return r;
}
