#include "misra.h"
#include "m2cmex.h"

extern S16 test_1503( void )
{
   S16 x = 0;

   switch ( s16a )
   {
   case 0:
      x = 2;
      break;
   case 1:
      x = 1;
      break;
   }                            /* MISRA Violation */

   switch ( s16b )
   {
   default:                     /* MISRA Violation */
      x = 7;
      break;
   case 2:
      x = 5;
      break;
   case 3:
      ++x;
      break;
   }

   return x;
}
