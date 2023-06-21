package hr.rba.creditcardprint.issuing;


import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintStatusDto;

/**
 * The IssuingService interface provides methods for issuing print of credit cards.
 */
public interface IssuingService {

    /**
     * Print the credit card by generating print file for external print job.
     *
     * @param oib The personal identification number (OIB) of the cardholder.
     * @return The status of the credit card printing process.
     */
    CreditCardPrintStatusDto issueCard(String oib);

    /**
     * Generates the name of the credit card file based on the credit card details.
     *
     * Oib is used for name.
     *
     * @param creditCard
     * @return The name of the credit card file.
     */
    static String nameCreditCardFile(final CreditCard creditCard) {
        return String.format("%s.csv", creditCard.getOib());
    }
}
