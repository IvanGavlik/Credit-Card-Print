package hr.rba.creditcardprint.request;


import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.data.CreditCardRepository;
import hr.rba.creditcardprint.data.Status;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintInsertDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestPrintServiceImpl implements RequestPrintService {

    private CreditCardRepository repository;
    private RequestPrintMapper mapper;

    /**
     * Constructor.
     *
     * @param creditCardRepository
     * @param requestPrintMapper
     */
    @Autowired
    public RequestPrintServiceImpl(final CreditCardRepository creditCardRepository,
                                   final RequestPrintMapper requestPrintMapper) {
        this.repository = creditCardRepository;
        this.mapper = requestPrintMapper;
    }

    @Override
    public void requestForPrint(final CreditCardPrintInsertDto insertDto) {
        CreditCard creditCard = this.mapper.dtoToEntity(insertDto);
        creditCard.setStatus(Status.NO_ACTIVE);
        repository.save(creditCard);
    }
}
