/*
 * rule16_2_req.c
 *
 *  Created on: 2017. 1. 23.
 *      Author: User
 */

#include "misra.h"
#include "m2cmex.h"

static void test_1602a( S16 x );
static void test_1602b( S16 x );
static void test_1602c( S16 x );
static void test_1602d( S16 x );
static void test_1602e( S16 x );


extern S16 test_1602( void )
{
   test_1602a( 5 );
   test_1602e( 5 );
   return 1;
}

static void test_1602a( S16 x )
{
   if ( x > 0 )
   {
      --x;
      test_1602b( x );
   }
}

static void test_1602b( S16 x )
{
   if ( x > 0 )
   {
      --x;
      test_1602c( x );
   }
}

static void test_1602c( S16 x )
{
   if ( x > 0 )
   {
      --x;
      test_1602d( x );
   }
}

static void test_1602d( S16 x )
{
   if ( x > 0 )
   {
      --x;
      test_1602a( x );
   }
}

static void test_1602e( S16 x )
{
   if ( x > 0 )
   {
      --x;
      test_1602e( x );                /* MISRA Violation */
   }
}
