/*
 * Test.C
 *
 *  Created on: 2017. 1. 13.
 *      Author: stkim
 */



#include "misra.h"
#include "m2cmex.h"


extern S16 test_1406( void )
{
   S16 n;

   for ( n = 0; n < 5; n++ )
   {
      if ( n > s16a )
      {
         break;
      }

      switch (s16a)
      {
      case 1:
         ++s16b;
         break;
      case 2:
         if (s16b > s16a)
         {
            break;
         }
         ++s16b;
         break;
      default:
         break;
      }

      if ( n > (s16a - 2) )
      {
         break;               /* MISRA Violation (Real)*/
      }
      break;
      break;				// misra violation
   }

   return s16b;
}

