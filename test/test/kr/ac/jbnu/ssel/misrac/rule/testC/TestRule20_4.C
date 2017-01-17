/* PRQA S 2983 ++ */

#include <stdlib.h>
#include "misra.h"
#include "m2cmex.h"

#define L 4U

extern S16 test_2004( void )
{
   S8 *p;

   p = ( S8 * )malloc( L );                /* MISRA Violation */
   free( p );                              /* MISRA Violation */

   p = ( S8 * )calloc( 10U, L );           /* MISRA Violation */
   p = ( S8 * )realloc( p, L );            /* MISRA Violation */

   return 0;
}

