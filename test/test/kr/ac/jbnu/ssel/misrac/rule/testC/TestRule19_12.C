
/* PRQA S 3429 ++ */

#include "misra.h"
#include "m2cmex.h"

#define PASTE( front, middle, back ) ( #front##middle##back ) /* MISRA Violation */
#define TWOST(A,B) #A #B                                      /* MISRA Violation */

extern S16 test_1912( void )
{
   return 1;
}

