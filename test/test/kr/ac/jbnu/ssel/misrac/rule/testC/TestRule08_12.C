/*
 * rule08.12_req.c
 *
 *  Created on: 2017. 2. 27.
 *      Author: User
 */
int array1[ 10 ];                          /* Compliant     */
extern int array2[ ];                      /* Not compliant */
int array2[ ] = { 0, 10, 15 };             /* Compliant     */
