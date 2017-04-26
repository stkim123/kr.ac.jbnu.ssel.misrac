package kr.ac.jbnu.ssel.misrac.rulesupport;

/**
 * 
 * @author "STKIM"
 *
 */
public class MiaraCRuleException extends Exception {

    public MiaraCRuleException() {
    }

    public MiaraCRuleException(String message) {
	super(message);
    }

    public MiaraCRuleException(Throwable cause) {
	super(cause);
    }

    public MiaraCRuleException(String message, Throwable cause) {
	super(message, cause);
    }

    public MiaraCRuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
