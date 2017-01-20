/* PRQA S 3205 ++ */

#include "misra.h"
#include "m2cmex.h"

union test
{
   S8 *c;
   S32 i;
};

extern S16 test_1804( void )
{
   union test mytest;                  /* MISRA Violation */

   return 0;
}
