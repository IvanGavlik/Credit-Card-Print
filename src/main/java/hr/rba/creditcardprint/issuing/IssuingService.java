package hr.rba.creditcardprint.issuing;


import hr.rba.creditcardprint.openapi.model.IssuingDto;

public interface IssuingService {

    /**
     * Issue credit card.
     * Create files for issuing credit card.
     *
     * @return Issued card or throws exception
     */
    IssuingDto issueCard();

}
