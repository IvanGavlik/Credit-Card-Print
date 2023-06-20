package hr.rba.creditcardprint.issuing;


import hr.rba.creditcardprint.openapi.model.CreditCardPrintStatusDto;

public interface IssuingService {

    /**
     * Print credit card.
     * Create files for printing credit card.
     *
     */
    CreditCardPrintStatusDto issueCard(String oib);

}
