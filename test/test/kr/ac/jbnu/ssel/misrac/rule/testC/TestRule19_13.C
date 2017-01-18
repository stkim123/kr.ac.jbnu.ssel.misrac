/*
 * rule19.13_adv.c
 *
 *  Created on: 2017. 1. 17.
 *      Author: User
 */

#include "misra.h"

#include "gen.h"
#include "m2cmex.h"

#define STRINGIFY( S ) #S
#define GLUE( S1, S2 ) S1 ## S2

extern S16 test_1913( void )
{
   const PC rule[] = "rule 19.13";

   put_line( STRINGIFY( MISRA ) );
   put_line( GLUE( ru, le ) );

   return 0;
}

