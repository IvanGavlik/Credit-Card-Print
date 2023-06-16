package hr.rba.creditcardissuing.issuing;

public interface IssuingService {

    /**
     * Issue credit card.
     * Create files for issuing credit card.
     *
     * @return Issued card or throws exception
     */
    IssuingDto issueCard();

}
