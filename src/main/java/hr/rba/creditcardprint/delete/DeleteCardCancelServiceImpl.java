package hr.rba.creditcardprint.delete;

import hr.rba.creditcardprint.config.ApplicationConfig;
import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.data.CreditCardRepository;
import hr.rba.creditcardprint.issuing.FileStorageService;
import hr.rba.creditcardprint.issuing.IssuingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * The class provides functionality to delete a credit card print entity and mark its files as deleted.
 */
@Service
public class DeleteCardCancelServiceImpl implements DeleteCardCancelService {

    private final CreditCardRepository repository;
    private final ApplicationConfig config;


    /**
     * Constructs a new DeleteCardCancelServiceImpl instance.
     *
     * @param creditCardRepository
     * @param applicationConfig
     */
    @Autowired
    public DeleteCardCancelServiceImpl(final CreditCardRepository creditCardRepository,
                                       final ApplicationConfig applicationConfig
                                       ) {
        this.repository = creditCardRepository;
        this.config = applicationConfig;
    }

    /**
     * Deletes the credit card with the specified OIB and marks its file as deleted.
     *
     * @param oib
     */
    @Override
    public void delete(final String oib) {
        Optional<CreditCard> creditCard = this.repository.findCreditCardByOib(oib);
        if (!creditCard.isPresent()) {
            return;
        }

        try {
            String existing = String.format("%s%s",
                    config.getFileDire(),
                    IssuingService.nameCreditCardFile(creditCard.get()));
            FileStorageService.renameFile(existing, existing + DeleteCardCancelService.FILE_DELETED_MARKER);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.repository.delete(creditCard.get());
    }
}
