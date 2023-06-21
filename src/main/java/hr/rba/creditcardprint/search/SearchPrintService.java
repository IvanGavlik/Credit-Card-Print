package hr.rba.creditcardprint.search;

import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;

import java.util.List;

/**
 * The interface provides functionality to search for credit card print details
 * based on various search criteria.
 */
public interface SearchPrintService {

    /**
     * Searches for credit card print details based on params.
     *
     * @param fName
     * @param lName
     * @param oib
     * @param status
     * @return A list of CreditCardPrintDetailsDto objects that match the search criteria.
     */
    List<CreditCardPrintDetailsDto> search(String fName, String lName, String oib, String status);
}
