package hr.rba.creditcardprint.search;

import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;

import java.util.List;

public interface SearchPrintService {
    List<CreditCardPrintDetailsDto> search(String fName, String lName, String oib, String status);
}
