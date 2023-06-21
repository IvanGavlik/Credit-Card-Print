package hr.rba.creditcardprint.issuing;

import java.util.List;
/**
 * The PrintJobChecker interface provides a method for checking the print job status.
 */
public interface PrintJobChecker {

    /**
     * Checks the result of print job by looking at the statues in CSV files.
     *
     * @return The list of CreditCardCsvEntity representing the credit card entities from CSV files.
     */
    List<CreditCardCsvEntity> checkPrintJob();
}
