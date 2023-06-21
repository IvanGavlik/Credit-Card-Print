package hr.rba.creditcardprint.issuing;

import hr.rba.creditcardprint.delete.DeleteCardCancelService;
import hr.rba.creditcardprint.config.ApplicationConfig;
import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.data.CreditCardRepository;
import hr.rba.creditcardprint.data.Status;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * The PrintJobCheckerImpl class is responsible for checking and updating
 * the status of credit card print jobs.
 * It implements the PrintJobChecker interface.
 */
@Service
public class PrintJobCheckerImpl implements PrintJobChecker {

    private final CreditCardRepository repository;
    private final ApplicationConfig config;

    private final CreditCardIssuingDtoMapper mapper;

    /**
     * Constructor for creating a new PrintJobCheckerImpl instance.
     *
     * @param creditCardRepository     .
     * @param applicationConfig
     * @param creditCardIssuingDtoMapper
     */
    @Autowired
    public PrintJobCheckerImpl(final CreditCardRepository creditCardRepository,
                               final ApplicationConfig applicationConfig,
                               final CreditCardIssuingDtoMapper creditCardIssuingDtoMapper) {
        this.repository = creditCardRepository;
        this.config = applicationConfig;
        this.mapper = creditCardIssuingDtoMapper;
    }
    /**
     * Checks the result of print job by looking at the statues in CSV files.
     *
     * Updated database accordingly and returns a list of credit card entities from CSV files.
     * Files marked as deleted are skipped.
     * Not checking request in status NO_ACTIVE because they are not picked by print job.
     *
     * @return The list of CreditCardCsvEntity representing the credit card entities from CSV files.
     */
    public List<CreditCardCsvEntity> checkPrintJob() {
        final List<CreditCard> csvEntityList =
                Stream.of(new File(this.config.getFileDire()).listFiles())
                .filter(file -> !file.isDirectory()
                        && !file.getName().contains(DeleteCardCancelService.FILE_DELETED_MARKER))
                .flatMap(file -> {
                    try {
                        return Files.lines(file.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return Stream.empty();
                })
                .map(el -> new CreditCardCsvEntity()
                        .toEntity(this.config.getFileDel(), el))
                 // no need to check for NO_ACTIVE because they are waiting for print job
                .filter(el -> el.getStatus() != Status.NO_ACTIVE)
                .collect(Collectors.toList());

        return updateCsvEntities(csvEntityList).stream().
                map(el -> this.mapper.toCsvEntity(el))
                .collect(Collectors.toList());
    }

    /**
     * Updates the credit card entities based on the provided CSV entities.
     *
     * Only the status is updated.
     * They are matched by oib other data is not checked.
     *
     * @param csvEntityList The list of CreditCard entities from CSV files.
     * @return The list of updated CreditCard entities.
     */
    @Transactional
    public List<CreditCard> updateCsvEntities(final List<CreditCard> csvEntityList) {
        final List<String> oibList = csvEntityList.stream()
                .map(el -> el.getOib())
                .collect(Collectors.toList());
        final List<CreditCard> found = this.repository.findAllByOibIn(oibList);

        final List<CreditCard> updated = found.stream().map(el -> {
            Optional<CreditCard> creditCard = csvEntityList.stream()
                    .filter(item -> item.getOib().equals(el.getOib()))
                    .findFirst();

            if (creditCard.isPresent()) {
                el.setStatus(creditCard.get().getStatus());
            }
            return el;
        }).collect(Collectors.toList());

        return StreamSupport
                .stream(this.repository.saveAll(updated).spliterator(), false)
                .collect(Collectors.toList());
    }


}
