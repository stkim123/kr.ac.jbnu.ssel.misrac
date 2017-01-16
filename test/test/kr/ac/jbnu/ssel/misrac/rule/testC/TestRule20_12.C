/*
 * TestRule20_12.C
 *
 *  Created on: 2017. 1. 16.
 *      Author: stkim
 */


#include <time.h>                          /* MISRA Violation */

#include "misra.h"
#include "m2cmex.h"

extern S16 test_2012( void )
{
  S16    r = 0;
  time_t xtime;

  if ( time( &xtime ) == ( time_t )-1 )    /* MISRA Violation */
  {
      r = -1;
  }

  return r;
}
