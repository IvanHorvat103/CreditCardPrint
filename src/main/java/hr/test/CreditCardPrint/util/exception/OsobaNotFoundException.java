package hr.test.CreditCardPrint.util.exception;

public class OsobaNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4484914123423432060L;

	public OsobaNotFoundException(String oib) {
        super("Osoba not found with OIB: " + oib);
    }
}