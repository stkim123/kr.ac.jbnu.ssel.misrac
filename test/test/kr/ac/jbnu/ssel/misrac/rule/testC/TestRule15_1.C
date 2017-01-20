#include "misra.h"
#include "m2cmex.h"

extern S16 test_1501( void )
{
   switch ( s16a )
   {
   case 0:

      if ( s16b == 1 )
      {
         case 1:                /* MISRA Violation */
            break;
      }

      while ( s16b < 10 )
      {
         ++s16b;
         case 2:
            break;
      }
   default:
      break;
   }

   return 1;
}
