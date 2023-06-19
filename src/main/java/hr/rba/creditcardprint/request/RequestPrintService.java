package hr.rba.creditcardprint.request;

import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintInsertDto;
import jakarta.validation.Valid;

public interface RequestPrintService {
    /**
     * Request
     * @param insertDto
     */
    CreditCardPrintDetailsDto requestForPrint(@Valid final CreditCardPrintInsertDto insertDto);

}
