/*
 * Rule14_8.C
 *
 *  Created on: 2017. 1. 16.
 *      Author: presentation
 */


int main()
{
	int i = 0;

for ( i = 0; i < N_ELEMENTS; ++i )
{
    buffer[i] = 0;       /* Even a single statement must be in braces */
}

while ( new_data_available )
    process_data();       /* Incorrectly not enclosed in braces */
    service_watchdog();   /* Added later but, despite the appearance
                             (from the indent) it is actually not
                             part of the body of the while statement,
                             and is executed only after the loop has
                             terminated */
}
