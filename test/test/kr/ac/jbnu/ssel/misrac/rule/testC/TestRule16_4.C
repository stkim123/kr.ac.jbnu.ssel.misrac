/*
 * rule16.4_req.c
 *
 *  Created on: 2017. 1. 23.
 *      Author: User
 */
/* PRQA S 2983,3203 ++ */
#include "misra.h"
#include "m2cmex.h"

static S16 test_1604a(S16 aa);

extern S16 test_1604( void )
{
    S16 (*pfx)(S16 a);
    pfx = &test_1604a;

    return 1;
}

static S16 test_1604a(S16 aaa)                  /* MISRA Violation */
{
    return aaa;
}
