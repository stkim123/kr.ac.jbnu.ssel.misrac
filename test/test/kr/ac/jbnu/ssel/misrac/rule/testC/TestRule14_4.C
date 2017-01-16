#include "misra.h"
#include "m2cmex.h"

extern S16 test_1404( void )
{
   goto mylabel;               /* MISRA Violation */

mylabel:

   return 1;
}
