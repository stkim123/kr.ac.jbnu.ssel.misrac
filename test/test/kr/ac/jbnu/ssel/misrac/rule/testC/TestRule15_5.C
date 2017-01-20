
int main(){
	switch ( x )
	{
	    uint8_t var;        /* not compliant - decl before 1st case     */
	case 0:
	    a = b;
	    break;              /* break is required here                   */
	case 1:                 /* empty clause, break not required         */
	case 2:
	    a = c;              /* executed if x is 1 or 2                  */
	    if ( a == b )
	    {
	        case 3:         /* Not compliant - case is not allowed here */
	    }
	    break;              /* break is required here                   */
	case 4:
	    a = b;              /* Not compliant - non empty drop through   */
	case 5:
	    a = c;
	    break;
	default:                /* default clause is required               */
	    errorflag = 1;      /* should be non-empty if possible          */
	    break;              /* break is required here, in case a
	                           future modification turns this into a
	                           case clause                              */
	}
}
