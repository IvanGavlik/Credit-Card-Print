package hr.rba.creditcardprint.request;

/**
 * The CreditCardPrintAlreadyExistException class is an exception that is
 * thrown when attempting to create a credit card print that already exists.
 */
public class CreditCardPrintAlreadyExistException extends RuntimeException {

    /**
     * Constructs a new CreditCardPrintAlreadyExistException
     * with the specified OIB and status.
     *
     * @param oib
     * @param status
     * */
    public CreditCardPrintAlreadyExistException(final String oib, final String status) {
        super(
                String.format("Credit Card Print {oib: %s} already exist and it is in status %s",
                        oib, status)
        );
    }
}
