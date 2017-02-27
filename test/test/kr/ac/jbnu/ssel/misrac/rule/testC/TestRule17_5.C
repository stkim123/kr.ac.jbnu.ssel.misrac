/*
 * rule17.5_adv.c
 *
 *  Created on: 2017. 2. 27.
 *      Author: User
 */
typedef int8_t * INTPTR;

struct s
{
   int8_t *   s1; /* compliant     */
   int8_t **  s2; /* compliant     */
   int8_t *** s3; /* not compliant */
};

struct s *   ps1; /* compliant     */
struct s **  ps2; /* compliant     */
struct s *** ps3; /* not compliant */

int8_t **  (   *pfunc1 )(); /* compliant     */
int8_t **  (  **pfunc2 )(); /* compliant     */
int8_t **  ( ***pfunc3 )(); /* not compliant */
int8_t *** (  **pfunc4 )(); /* not compliant */

void function( int8_t *   par1,
               int8_t **  par2,
               int8_t *** par3,                /* not compliant */
               INTPTR *   par4,
               INTPTR *   const * const par5,  /* not compliant */
               int8_t *   par6[],
               int8_t **  par7[] )             /* not compliant */
{
   int8_t *   ptr1;
   int8_t **  ptr2;
   int8_t *** ptr3;               /* not compliant */
   INTPTR *   ptr4;
   INTPTR *   const * const ptr5; /* not compliant */
   int8_t *   ptr6[10];
   int8_t **  ptr7[10];
}
