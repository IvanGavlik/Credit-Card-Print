package hr.rba.creditcardprint.request;


import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.data.CreditCardRepository;
import hr.rba.creditcardprint.data.Status;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintInsertDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.Valid;

import java.util.Optional;

@Service
@Validated
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
    public CreditCardPrintDetailsDto requestForPrint(@Valid final CreditCardPrintInsertDto insertDto) {
        final Optional<CreditCard> current = this.repository.findCreditCardByOib(insertDto.getOib());
        if (current.isPresent()) {
            throw new CreditCardPrintAlreadyExistException(
                    current.get().getOib(),
                    current.get().getStatus().name());
        }
        CreditCard creditCard = this.mapper.dtoToEntity(insertDto);
        creditCard.setStatus(Status.NO_ACTIVE);
        return this.mapper.entityToDetailsDto(repository.save(creditCard));
    }
}
