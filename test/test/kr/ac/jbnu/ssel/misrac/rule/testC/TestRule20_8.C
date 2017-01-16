#include <signal.h>             /* MISRA Violation */
#include "misra.h"
#include "m2cmex.h"

extern S16 test_2008( void )
{
   S16 i = SIGINT;

   return i;
}
