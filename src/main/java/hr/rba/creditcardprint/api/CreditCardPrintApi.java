package hr.rba.creditcardprint.api;

import hr.rba.creditcardprint.delete.DeleteCardCancelService;
import hr.rba.creditcardprint.issuing.IssuingService;
import hr.rba.creditcardprint.openapi.api.CreditCardPrintApiDelegate;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintInsertDto;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintStatusDto;
import hr.rba.creditcardprint.request.RequestPrintService;
import hr.rba.creditcardprint.search.SearchPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class CreditCardPrintApi implements CreditCardPrintApiDelegate {

    private IssuingService issuing;

    private RequestPrintService requestPrint;

    private SearchPrintService search;

    private DeleteCardCancelService deleteService;
    /**
     * Constructor.
     *
     * @param issuingService
     * @param requestPrintService
     */
    @Autowired
    public CreditCardPrintApi(final IssuingService issuingService,
                              final RequestPrintService requestPrintService,
                              final SearchPrintService searchPrintService,
                              final DeleteCardCancelService deleteCardCancelService) {
        this.issuing = issuingService;
        this.requestPrint = requestPrintService;
        this.search = searchPrintService;
        this.deleteService = deleteCardCancelService;
    }

    public ResponseEntity<CreditCardPrintDetailsDto> insertCreditCard(final CreditCardPrintInsertDto creditCardPrintInsertDto) {
        CreditCardPrintDetailsDto dto = this.requestPrint.requestForPrint(creditCardPrintInsertDto);
        return ResponseEntity.ok().body(dto);
    }

    public ResponseEntity<CreditCardPrintStatusDto> printCreditCard(String oib) {
        CreditCardPrintStatusDto statusDto = issuing.issueCard(oib);
        return ResponseEntity.ok().body(statusDto);
    }

    public ResponseEntity<List<CreditCardPrintDetailsDto>> searchCreditCard(String firstName,
                                                                            String lastName,
                                                                            String oib,
                                                                            String status) {
        List<CreditCardPrintDetailsDto> list = search.search(firstName, lastName, oib, status);
        return ResponseEntity.ok().body(list);
    }
    public ResponseEntity<Void> deleteCreditCard(final String oib) {
        this.deleteService.delete(oib);
        return ResponseEntity.ok().build();
    }
}

