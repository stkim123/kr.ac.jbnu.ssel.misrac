int main(){

	if ( x < 0 )
	{
	    log_error( 3 );
	    x = 0;
	}
	else if ( y < 0 )
	{
	    x = 3;
	}
	else
	{      /* programmer expects this will never be reached */
	       /* no change in value of x */
	}

}
