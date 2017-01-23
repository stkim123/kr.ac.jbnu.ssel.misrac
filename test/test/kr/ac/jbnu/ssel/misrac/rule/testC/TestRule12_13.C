/*
 * TestRule12_3.C
 *
 *  Created on: 2017. 1. 20.
 *      Author: STKim2
 */


int main()
{
u8a = ++u8b + u8c--;      /* Not compliant */

u8a = ++u8b;      /* Not compliant */

++u8b;
u8a = u8b + u8c;
u8c--;
}
