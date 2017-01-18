
#include "misra.h"
#include "m2cmex.h"

#define  L      5
#undef   L              /* MISRA Violation of Rule 19.6 */

extern S16 test_1905( void )
{
#define  M      10      /* MISRA Violation of Rule 19.5 */
#undef   M              /* MISRA Violation of Rule 19.5
                                          and Rule 19.6 */
   return 0;
}

