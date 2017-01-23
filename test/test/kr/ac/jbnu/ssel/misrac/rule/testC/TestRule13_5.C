#include "misra.h"
#include "m2cmex.h"

extern S16 test_1305( void )
{
   S16 n;

   n = 0;

   for ( s16a++; n < 10; n++ )                  /* MISRA Violation */
   {
      s16b = s16b + s16a;
   }

   for ( s16a = 0; ++s16a < 10; ++s16b )        /* MISRA Violation - also Rule 12.13 */
   {
      ++s16b;
   }

   return s16b;
}
