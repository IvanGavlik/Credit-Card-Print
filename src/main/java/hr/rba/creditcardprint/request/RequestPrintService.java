package hr.rba.creditcardprint.request;

import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintInsertDto;
import jakarta.validation.Valid;

/**
 * The RequestPrintService interface is responsible for
 * handling requests related to printing credit cards.
 */
public interface RequestPrintService {
    /**
     * Processes a request for printing a credit card.
     *
     * @param insertDto
     * @return The CreditCardPrintDetailsDto object
     * representing the details of the printed credit card.
     */
    CreditCardPrintDetailsDto requestForPrint(@Valid CreditCardPrintInsertDto insertDto);

}
