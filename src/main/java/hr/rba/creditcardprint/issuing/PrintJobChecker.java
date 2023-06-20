package hr.rba.creditcardprint.issuing;

import java.util.List;

public interface PrintJobChecker {

    List<CreditCardCsvEntity> checkPrintJob();
}
