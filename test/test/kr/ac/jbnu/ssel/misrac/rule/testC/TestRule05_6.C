#include "misra.h"
#include "m2cmex.h"

struct { int16_t key; int16_t value; } record;
union {int16_t A;} test;

void fincEx(){
	int16_t value;                /* Rule violation - 2nd use of value */

	record.key = 1;
	value = 0;                    /* should have been record.value */
}

