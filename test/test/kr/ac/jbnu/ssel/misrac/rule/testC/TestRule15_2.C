#include "misra.h"
#include "m2cmex.h"


extern S16 test_1502( void )
{
   switch ( s16a )
   {
   case 0:

   case 1:
      ++s16b;
      break;

   case 2:		 /* MISRA Violation */
      ++s16c;

   case 3:		 /* MISRA Violation */
      ++s16b;

   default:		 /* MISRA Violation */
      ++s16c;
   }

   return 1;
}
