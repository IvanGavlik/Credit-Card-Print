package hr.rba.creditcardprint.delete;

import hr.rba.creditcardprint.config.ApplicationConfig;
import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.data.CreditCardRepository;
import hr.rba.creditcardprint.issuing.FileStorageService;
import hr.rba.creditcardprint.issuing.IssuingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeleteCardCancelServiceImpl implements DeleteCardCancelService {

    private CreditCardRepository repository;
    private ApplicationConfig config;

    @Autowired
    public DeleteCardCancelServiceImpl(CreditCardRepository creditCardRepository,
                                       ApplicationConfig applicationConfig
                                       ) {
        this.repository = creditCardRepository;
        this.config = applicationConfig;
    }

    @Override
    public void delete(String oib) {
        Optional<CreditCard> creditCard = this.repository.findCreditCardByOib(oib);
        if(!creditCard.isPresent()) {
            return;
        }

        try {
            String existing = String.format("%s%s",
                    config.getFileDire(),
                    IssuingService.nameCreditCardFile(creditCard.get()));
            FileStorageService.renameFile(existing, existing+ DeleteCardCancelService.FILE_DELETED_MARKER);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.repository.delete(creditCard.get());
    }
}
