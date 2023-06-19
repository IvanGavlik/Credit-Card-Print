package hr.rba.creditcardprint.api;

import hr.rba.creditcardprint.issuing.IssuingService;
import hr.rba.creditcardprint.openapi.api.CreditCardPrintApiDelegate;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintInsertDto;
import hr.rba.creditcardprint.openapi.model.PrintCreditCard2XXResponse;
import hr.rba.creditcardprint.request.RequestPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class CreditCardPrintApi implements CreditCardPrintApiDelegate {

    private IssuingService issuing;

    private RequestPrintService requestPrint;

    /**
     * Constructor.
     *
     * @param issuingService
     * @param requestPrintService
     */
    @Autowired
    public CreditCardPrintApi(final IssuingService issuingService, final RequestPrintService requestPrintService) {
        this.issuing = issuingService;
        this.requestPrint = requestPrintService;
    }

    public ResponseEntity<CreditCardPrintDetailsDto> insertCreditCard(final CreditCardPrintInsertDto creditCardPrintInsertDto) {
        CreditCardPrintDetailsDto dto = this.requestPrint.requestForPrint(creditCardPrintInsertDto);
        return ResponseEntity.ok().body(dto);
    }

    public ResponseEntity<PrintCreditCard2XXResponse> printCreditCard(String oib) {
        return null;
    }

    public ResponseEntity<List<CreditCardPrintDetailsDto>> searchCreditCard(String firstName,
                                                                            String lastName,
                                                                            String oib) {
        return null;
    }

    // https://dzone.com/articles/best-practice-for-exception-handling-in-spring-boo
    // https://stackabuse.com/exception-handling-in-spring/
}

