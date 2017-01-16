/*
 * Rule8_1.C
 *
 *  Created on: 2017. 1. 14.
 *      Author: stkim
 */

#include <stdio.h>

static void foo(void);
extern int aaa(void);

void bar(void)
{
    foo();
}

static void foo(void)
{
    bar();	/* violation */
    int par = aaa(100);
}

static void foo(int)
{
    bar();	/* violation */
}

extern void aaa(int)
{
    bar();	/* violation */
}

