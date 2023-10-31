package hr.rba.CreditCardPrint.util.exception;

public class CreditCardNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4484914123423432060L;

	public CreditCardNotFoundException(String oib) {
        super("Credit card not found with OIB: " + oib);        
    }
}