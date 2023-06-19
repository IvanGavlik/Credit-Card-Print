package hr.rba.creditcardprint.request;

import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintInsertDto;

public interface RequestPrintService {
    /**
     * Request
     * @param insertDto
     */
    CreditCardPrintDetailsDto requestForPrint(final CreditCardPrintInsertDto insertDto);

}
