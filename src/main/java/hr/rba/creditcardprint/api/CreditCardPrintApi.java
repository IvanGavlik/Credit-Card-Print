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

/**
 * Implementation of the CreditCardPrintApiDelegate interface that handles
 * credit card printing API operations.
 *
 * CreditCardPrintApiDelegate is generated class by OpenAPI.
 */
@Service
public final class CreditCardPrintApi implements CreditCardPrintApiDelegate {

    private IssuingService issuing;

    private RequestPrintService requestPrint;

    private SearchPrintService search;

    private DeleteCardCancelService deleteService;

    /**
     * Constructor.
     *
     * @param issuingService        the issuing service
     * @param requestPrintService   the request print service
     * @param searchPrintService    the search print service
     * @param deleteCardCancelService the delete card cancel service
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

    /**
     * Inserts a credit card print request and returns the details of the inserted print.
     *
     * @param creditCardPrintInsertDto the credit card print insert DTO
     * @return the response entity containing the credit card print details DTO
     */
    public ResponseEntity<CreditCardPrintDetailsDto> insertCreditCard(
            final CreditCardPrintInsertDto creditCardPrintInsertDto) {
        final CreditCardPrintDetailsDto dto = this.requestPrint.requestForPrint(creditCardPrintInsertDto);
        return ResponseEntity.ok().body(dto);
    }

    /**
     * Prints a credit card with the specified OIB and returns the status of the print.
     *
     * @param oib the OIB (personal identification number) of the credit card
     * @return the response entity containing the credit card print status DTO
     */
    public ResponseEntity<CreditCardPrintStatusDto> printCreditCard(final String oib) {
        final CreditCardPrintStatusDto statusDto = issuing.issueCard(oib);
        return ResponseEntity.ok().body(statusDto);
    }

    /**
     * Searches for credit card prints based on the specified search criteria and returns a list of matching prints.
     *
     * @param firstName the first name of the credit card holder (optional)
     * @param lastName  the last name of the credit card holder (optional)
     * @param oib       the OIB (personal identification number) of the credit card (optional)
     * @param status    the status of the credit card print (optional)
     * @return the response entity containing the list of credit card print details DTOs
     */
    public ResponseEntity<List<CreditCardPrintDetailsDto>> searchCreditCard(final String firstName,
                                                                            final String lastName,
                                                                            final String oib,
                                                                            final String status) {
        final List<CreditCardPrintDetailsDto> list = search.search(firstName, lastName, oib, status);
        return ResponseEntity.ok().body(list);
    }

    /**
     * Deletes a credit card with the specified OIB and marks its file as deleted.
     *
     *
     * @param oib the OIB (personal identification number) of the credit card to delete
     * @return the response OK
     * */
    public ResponseEntity<Void> deleteCreditCard(final String oib) {
        this.deleteService.delete(oib);
        return ResponseEntity.ok().build();
    }
}

