
#include "misra.h"
#include "m2cmex.h"

extern S16 test_1500( void )
{
   switch ( s16a )
   {
      static S16 s16x1500;              /* MISRA Violation */
      case 1:
         s16x1500 = s16a;
         break;
      case 2:
         s16b = s16x1500;
         break;
      default:
         break;
   }
   return 1;
}

