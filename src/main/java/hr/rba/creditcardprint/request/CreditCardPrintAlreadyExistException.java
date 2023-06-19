package hr.rba.creditcardprint.request;

public class CreditCardPrintAlreadyExistException extends RuntimeException {
    public CreditCardPrintAlreadyExistException(String oib, String status) {
        super(
                String.format("Credit Card Print {oib: %s} already exist and it is in status %s",
                        oib, status)
        );
    }
}
