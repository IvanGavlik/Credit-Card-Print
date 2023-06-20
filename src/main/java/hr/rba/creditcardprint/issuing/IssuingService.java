package hr.rba.creditcardprint.issuing;


import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintStatusDto;

public interface IssuingService {

    /**
     * Print credit card.
     * Create files for printing credit card.
     *
     */
    CreditCardPrintStatusDto issueCard(String oib);

    static String nameCreditCardFile(CreditCard creditCard) {
        return String.format("%s.csv", creditCard.getOib());
    }
}
