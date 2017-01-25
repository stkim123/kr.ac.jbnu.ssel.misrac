int main(){
	flag = 1;
	for ( i = 0; ( i < 5 ) && ( flag == 1 ); i++ )
	{
	    flag = 0;   /* Compliant - allows early termination of loop */
	    i = i + 3;  /* Not compliant - altering the loop counter    */
	}

}
