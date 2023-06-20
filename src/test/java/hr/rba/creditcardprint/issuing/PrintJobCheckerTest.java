package hr.rba.creditcardprint.issuing;

import hr.rba.creditcardprint.config.ApplicationConfig;
import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.data.CreditCardRepository;
import hr.rba.creditcardprint.data.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PrintJobCheckerTest {

    private final static String fileDir = "./src/test/resources/print/files/printJob";
    private CreditCardRepository mockRepo = mock(CreditCardRepository.class);
    private ApplicationConfig mockConfig = mock(ApplicationConfig.class);
    private PrintJobChecker printJobChecker;

    @BeforeEach
    public void setUp() {
        when(mockConfig.getFileDire()).thenReturn(fileDir);
        when(mockConfig.getfileDel()).thenReturn(CsvEntity.DEFAULT_CSV_DELIMITER);

        this.printJobChecker = new PrintJobCheckerImpl(mockRepo, mockConfig, Mappers.getMapper(CreditCardIssuingDtoMapper.class));
    }

    /**
     * Test read csv files at {@link PrintJobCheckerTest#fileDir}
     * will confirm that {@link PrintJobChecker#checkPrintJob()} correctly updates entities
     *
     * Files: 12179050887.csv, 18561704886.csv, 69546524558.csv, 77570421961.csv, 91070672093.csv
     * Each file has one status
     *
     * Note: although integration test is recommended, Decided for unit test because this is run by scheduler.
     */
    @Test
    public void checkPrintJob() {
        // return passed param
        when(mockRepo.saveAll(any())).thenAnswer(i -> i.getArguments()[0]);
        when(this.mockRepo.findAllByOibIn(any())).thenAnswer(i -> creditCardsSameAsFiles().stream()
                .filter(el -> el.getStatus() != Status.NO_ACTIVE)
                .collect(Collectors.toList()));

        List<CreditCardCsvEntity> creditCardList = this.printJobChecker.checkPrintJob();
        Assertions.assertEquals(3, creditCardList.size()); // NO_ACTIVE is not updated
        Assertions.assertEquals(1, creditCardList.stream().filter(el -> el.getStatus() == Status.ACTIVE).count());
        Assertions.assertEquals(1, creditCardList.stream().filter(el -> el.getStatus() == Status.IN_PROCESS).count());
        Assertions.assertEquals(1, creditCardList.stream().filter(el -> el.getStatus() == Status.BLOCKED).count());
        Assertions.assertEquals(0, creditCardList.stream().filter(el -> el.getStatus() == Status.NO_ACTIVE).count());
    }

    private List<CreditCard> creditCardsSameAsFiles() {
        List<CreditCard> creditCardList = new ArrayList<>();

        CreditCard creditCard = new CreditCard();
        creditCard.setFirstName("Marko");
        creditCard.setLastName("Polo");
        creditCard.setOib("12179050887");
        creditCard.setStatus(Status.NO_ACTIVE);
        creditCardList.add(creditCard);

        creditCard = new CreditCard();
        creditCard.setFirstName("Vera");
        creditCard.setLastName("Marković");
        creditCard.setOib("18561704886");
        creditCard.setStatus(Status.ACTIVE);
        creditCardList.add(creditCard);

        creditCard = new CreditCard();
        creditCard.setFirstName("Matko");
        creditCard.setLastName("Zorić");
        creditCard.setOib("69546524558");
        creditCard.setStatus(Status.IN_PROCESS);
        creditCardList.add(creditCard);

        creditCard = new CreditCard();
        creditCard.setFirstName("Dragoljub");
        creditCard.setLastName("Golub");
        creditCard.setOib("77570421961");
        creditCard.setStatus(Status.BLOCKED);
        creditCardList.add(creditCard);

        return creditCardList;
    }
}
