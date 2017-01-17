
/* Permit nested C-style comments: */
/* PRQA S 3108 ++ */

/* PRQA S 0883 ++ */

#include "misra.h"
#include "m2cmex.h"

#if 0
/* No header files exist which correspond to
   the following #include directives.
   QAC will generate Hard Errors (Level 9)
   if this code block is not excluded      */

#include ".\abc.h"                      /* MISRA Violation */
#include "John's.h"                     /* MISRA Violation */
#include "Fred.h/*temporary*/"          /* MISRA Violation */
#include "Money$.h"                     /* MISRA Violation */
#endif

extern S16 test_1902( void )
{
   return 1;
}
