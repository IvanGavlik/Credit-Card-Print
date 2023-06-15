package hr.rba.creditcardissuing.issuing;

import hr.rba.creditcardissuing.data.CreditCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssuingServiceImpl implements IssuingService {

    private CreditCardRepository creditCardRepository;

    private CreditCardIssuingDtoMapper mapper;
    @Autowired
    public IssuingServiceImpl(CreditCardRepository creditCardRepository, CreditCardIssuingDtoMapper mapper) {
        this.creditCardRepository = creditCardRepository;
        this.mapper = mapper;
    }

    @Override
    public IssuingDto issueCard() {
        creditCardRepository.findAll().forEach(el -> System.out.println(this.mapper.creditCardToIssuingDto(el)));
        return null;
    }
}
