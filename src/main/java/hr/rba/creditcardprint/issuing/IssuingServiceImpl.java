package hr.rba.creditcardprint.issuing;

import hr.rba.creditcardprint.config.ApplicationConfig;
import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.data.CreditCardRepository;
import hr.rba.creditcardprint.data.Status;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintStatusDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.PrintWriter;

import java.util.Optional;

@Service
public class IssuingServiceImpl implements IssuingService {

    private final CreditCardRepository repository;

    private final CreditCardIssuingDtoMapper mapper;

    private final ApplicationConfig config;


    /**
     * IssuingServiceImpl constructor.
     *
     * @param creditCardRepository
     * @param mapperIssuing
     */
    @Autowired
    public IssuingServiceImpl(final CreditCardRepository creditCardRepository,
                              final CreditCardIssuingDtoMapper mapperIssuing,
                              final ApplicationConfig applicationConfig) {
        this.repository = creditCardRepository;
        this.mapper = mapperIssuing;
        this.config = applicationConfig;
    }

    @Override
    @Transactional
    public CreditCardPrintStatusDto issueCard(String oib) {
        final Optional<CreditCard> current = this.repository.findCreditCardByOib(oib);
        if (!current.isPresent()) {
            return createStatus(oib,
                    CreditCardPrintStatusDto.ProcessStatusEnum.FAILURE,
                    Optional.of(String.format("For {oib: %s} Credit Card Print does not exist", oib))
            );
        }

        final CreditCard creditCard = current.get();
        if (Status.NO_ACTIVE != creditCard.getStatus()) {
            return createStatus(oib,
                    CreditCardPrintStatusDto.ProcessStatusEnum.FAILURE,
                    Optional.of(
                            String.format("Only credit cards in %s state can be printed this { oib: %s } is in %s state",
                                    Status.NO_ACTIVE.name(), oib, creditCard.getStatus())
                    )
            );
        }

        File file = null;
        try {
            file = generateFile(creditCard);
            creditCard.setStatus(Status.IN_PROCESS);
            this.repository.save(creditCard);
            return createStatus(oib,
                    CreditCardPrintStatusDto.ProcessStatusEnum.REQUEST_SUCCESSFULLY_WAITING_PRINTER,
                    Optional.empty()
            );
        } catch (Exception ex) {
            ex.printStackTrace();
            String msg =
                    String.format("Credit Card Print {oib: %s} print failed. File for print was not created.", oib);
            if (file != null && file.exists()) {
                msg = String.format("Credit Card Print {oib: %s} print failed.", oib);
                file.delete();
            }
            return createStatus(oib,
                    CreditCardPrintStatusDto.ProcessStatusEnum.FAILURE,
                    Optional.of(msg)
            );
        }
    }

    private CreditCardPrintStatusDto createStatus(String oib,
                                                  CreditCardPrintStatusDto.ProcessStatusEnum status,
                                                  Optional<String> msg) {
        CreditCardPrintStatusDto statusDto = new CreditCardPrintStatusDto();
        statusDto.setOib(oib);
        statusDto.setProcessStatus(status);
        if (msg.isPresent()) {
            statusDto.setMsg(msg.get());
        }
        return statusDto;
    }
    private File generateFile(CreditCard creditCard) throws Exception {
        final String fileName = IssuingService.nameCreditCardFile(creditCard);
        Optional<File> file = FileStorageService.createFile(config.getFileDire() + fileName);
        if (!file.isPresent()) {
            return null;
        }

        String delimiter = config.getfileDel();
        if (delimiter == null) {
            delimiter = CsvEntity.DEFAULT_CSV_DELIMITER;
        }
        try (PrintWriter pw = new PrintWriter(file.get())) {
            pw.print(
                    this.mapper.toCsvEntity(creditCard).toCSV(delimiter)
            );
            return file.get();
        }
    }
}
