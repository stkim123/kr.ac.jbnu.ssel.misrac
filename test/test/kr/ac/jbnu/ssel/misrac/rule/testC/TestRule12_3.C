/*
 * TestRule12_3.C
 *
 *  Created on: 2017. 1. 20.
 *      Author: stkim
 */


int main(int argc, char **argv) {
	int32_t i;
	int32_t j;
	j = sizeof( i = 1234 );
	     /* j is set to the sizeof the type of i which is an int */
	     /* i is not set to 1234.                                */
	j = sizeof(i > 24);
}
