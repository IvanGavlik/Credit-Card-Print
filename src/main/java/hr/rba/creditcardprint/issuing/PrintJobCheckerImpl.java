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

@Service
public class PrintJobCheckerImpl implements PrintJobChecker {

    private final CreditCardRepository repository;
    private final ApplicationConfig config;

    private final CreditCardIssuingDtoMapper mapper;
    @Autowired
    public PrintJobCheckerImpl(final CreditCardRepository creditCardRepository,
                               final ApplicationConfig applicationConfig,
                               CreditCardIssuingDtoMapper creditCardIssuingDtoMapper) {
        this.repository = creditCardRepository;
        this.config = applicationConfig;
        this.mapper = creditCardIssuingDtoMapper;
    }

    public List<CreditCardCsvEntity> checkPrintJob() {
        List<CreditCard> csvEntityList = Stream.of(new File(this.config.getFileDire()).listFiles())
                .filter(file -> !file.isDirectory() && !file.getName().contains(DeleteCardCancelService.FILE_DELETED_MARKER))
                .flatMap(file -> {
                    try {
                        return Files.lines(file.toPath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return Stream.empty();
                })
                .map(el -> new CreditCardCsvEntity().toEntity(this.config.getfileDel(), el))
                .filter(el -> el.getStatus() != Status.NO_ACTIVE) // no need to check for NO_ACTIVE because they are waiting for print job
                .collect(Collectors.toList());

        return updateCsvEntities(csvEntityList).stream().
                map(el -> this.mapper.toCsvEntity(el))
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CreditCard> updateCsvEntities(List<CreditCard> csvEntityList) {
        List<String> oibList = csvEntityList.stream()
                .map(el -> el.getOib())
                .collect(Collectors.toList());
        List<CreditCard> found = this.repository.findAllByOibIn(oibList);

        List<CreditCard> updated = found.stream().map(el -> {
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
