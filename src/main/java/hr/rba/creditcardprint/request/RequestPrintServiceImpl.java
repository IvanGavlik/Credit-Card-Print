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

/**
 * The RequestPrintServiceImpl class is responsible for handling requests
 * related to printing credit cards.
 *
 * It implements the RequestPrintService interface.
 */
@Service
@Validated
public class RequestPrintServiceImpl implements RequestPrintService {

    private CreditCardRepository repository;
    private RequestPrintMapper mapper;

    /**
     * Constructor for creating a new RequestPrintServiceImpl instance.
     *
     * @param creditCardRepository The CreditCardRepository dependency.
     * @param requestPrintMapper   The RequestPrintMapper dependency.
     */
    @Autowired
    public RequestPrintServiceImpl(final CreditCardRepository creditCardRepository,
                                   final RequestPrintMapper requestPrintMapper) {
        this.repository = creditCardRepository;
        this.mapper = requestPrintMapper;
    }

    /**
     * Processes a request for printing a credit card.
     *
     * @param insertDto
     * @return The CreditCardPrintDetailsDto object representing the details of the printed credit card.
     * @throws CreditCardPrintAlreadyExistException If the credit card print already exists with the same
     * OIB and status.
     */
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
