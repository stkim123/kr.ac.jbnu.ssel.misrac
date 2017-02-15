#include "misra.h"
#include "m2cmex.h"


static S16 s0807a;               /* MISRA Violation - Only referenced in function test_0807a   */
static S16 s0807b;               /* MISRA Violation - Only referenced in function test_0807b   */
extern S16 obj_0807c = 0;        /* MISRA Violation - Only referenced in function test_0807b   */

extern S16 test_0807a( void )
{
   s0807a += s16a;

   return s0807a;
}

extern S16 test_0807b( void )
{
   s0807b = s16b;
   obj_0807c = s16a;

   return s0807b + obj_0807c;
}

