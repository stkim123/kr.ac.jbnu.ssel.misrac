
/* PRQA S 2983,3203 ++ */

#include <stddef.h>
#include "misra.h"
#include "m2cmex.h"


extern S16 test_2006( void )
{
   struct stag
   {
      S32  a;
      F64  b;
   } s;

   size_t p;

   s.a = 1;
   s.b = 0.0;

   p = offsetof( stag, b );      /* MISRA Violation */

   return 0;
}



