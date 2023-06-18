package hr.rba.creditcardprint.request;

import hr.rba.creditcardprint.openapi.model.CreditCardPrintInsertDto;

public interface RequestPrintService {
    /**
     * Request
     * @param insertDto
     */
    void requestForPrint(final CreditCardPrintInsertDto insertDto);

}
