package hr.rba.creditcardprint.issuing;

import hr.rba.creditcardprint.data.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class IssuingServiceImpl implements IssuingService {

    private final CreditCardRepository creditCardRepo;

    private final CreditCardIssuingDtoMapper mapper;

    /**
     * IssuingServiceImpl constructor.
     *
     * @param creditCardRepository
     * @param mapperIssuing
     */
    @Autowired
    public IssuingServiceImpl(final CreditCardRepository creditCardRepository,
                              final CreditCardIssuingDtoMapper mapperIssuing) {
        this.creditCardRepo = creditCardRepository;
        this.mapper = mapperIssuing;
    }

    @Override
    public void issueCard() {
        creditCardRepo.findAll().forEach(
                el -> System.out.println(el)
        );
    }
}
