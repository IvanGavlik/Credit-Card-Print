package hr.rba.creditcardprint.issuing;

import hr.rba.creditcardprint.config.ApplicationConfig;
import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.data.CreditCardRepository;
import hr.rba.creditcardprint.data.Status;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintStatusDto;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IssuingServiceTest {
    private final static String fileDir = "./src/test/resources/print/files";
    private CreditCardRepository mockRepo = mock(CreditCardRepository.class);
    private ApplicationConfig mockConfig = mock(ApplicationConfig.class);
    private IssuingService issuingService;

    @BeforeEach
    public void setUp() {
        when(mockConfig.getFileDire()).thenReturn(fileDir);
        when(mockConfig.getfileDel()).thenReturn(CsvEntity.DEFAULT_CSV_DELIMITER);

        this.issuingService = new IssuingServiceImpl(
                mockRepo,
                Mappers.getMapper(CreditCardIssuingDtoMapper.class),
                mockConfig
        );
    }

    @Test
    public void issuePrint() {
        final String fName = "M";
        final String lName = "M";
        final String oib = "12179050887";
        final Status status = Status.NO_ACTIVE;

        CreditCard creditCard = new CreditCard();
        creditCard.setFirstName(fName);
        creditCard.setLastName(lName);
        creditCard.setOib(oib);
        creditCard.setStatus(status);

        when(mockRepo.findCreditCardByOib(oib)).thenReturn(Optional.of(creditCard));
        CreditCardPrintStatusDto creditCardPrintStatusDto = this.issuingService.issueCard(oib);

        Assertions.assertEquals(oib, creditCardPrintStatusDto.getOib());
        Assertions.assertEquals(CreditCardPrintStatusDto.ProcessStatusEnum.REQUEST_SUCCESSFULLY_WAITING_PRINTER,
                creditCardPrintStatusDto.getProcessStatus());
        Assertions.assertNull(creditCardPrintStatusDto.getMsg());
    }

    @Test
    public void issuePrintNotFound() {
        final String oib = "72179050882";

        when(mockRepo.findCreditCardByOib(oib)).thenReturn(Optional.empty());
        CreditCardPrintStatusDto creditCardPrintStatusDto = this.issuingService.issueCard(oib);

        Assertions.assertEquals(oib, creditCardPrintStatusDto.getOib());
        Assertions.assertEquals(CreditCardPrintStatusDto.ProcessStatusEnum.FAILURE,
                creditCardPrintStatusDto.getProcessStatus());
        Assertions.assertNotNull(creditCardPrintStatusDto.getMsg());
    }

    @Test
    public void issuePrintNotInNO_ACTIVE() {
        final String fName = "M";
        final String lName = "M";
        final String oib = "12179050887";
        final Status status = Status.ACTIVE;

        CreditCard creditCard = new CreditCard();
        creditCard.setFirstName(fName);
        creditCard.setLastName(lName);
        creditCard.setOib(oib);
        creditCard.setStatus(status);

        when(mockRepo.findCreditCardByOib(oib)).thenReturn(Optional.of(creditCard));
        CreditCardPrintStatusDto creditCardPrintStatusDto = this.issuingService.issueCard(oib);

        Assertions.assertEquals(oib, creditCardPrintStatusDto.getOib());
        Assertions.assertEquals(CreditCardPrintStatusDto.ProcessStatusEnum.FAILURE,
                creditCardPrintStatusDto.getProcessStatus());
        Assertions.assertNotNull(creditCardPrintStatusDto.getMsg());
    }

    @Test
    public void issuePrintFileNotGenerated() {
        when(mockConfig.getFileDire()).thenReturn(null);
        when(mockConfig.getfileDel()).thenReturn(null);

        final String fName = "M";
        final String lName = "M";
        final String oib = "12179050887";
        final Status status = Status.NO_ACTIVE;

        CreditCard creditCard = new CreditCard();
        creditCard.setFirstName(fName);
        creditCard.setLastName(lName);
        creditCard.setOib(oib);
        creditCard.setStatus(status);

        when(mockRepo.findCreditCardByOib(oib)).thenReturn(Optional.of(creditCard));
        CreditCardPrintStatusDto creditCardPrintStatusDto = this.issuingService.issueCard(oib);

        Assertions.assertEquals(oib, creditCardPrintStatusDto.getOib());
        Assertions.assertEquals(CreditCardPrintStatusDto.ProcessStatusEnum.FAILURE,
                creditCardPrintStatusDto.getProcessStatus());
        Assertions.assertNotNull(creditCardPrintStatusDto.getMsg());
    }

    @Test
    public void issuePrintDBFailed() {
        final String fName = "M";
        final String lName = "M";
        final String oib = "12179050887";
        final Status status = Status.NO_ACTIVE;

        CreditCard creditCard = new CreditCard();
        creditCard.setFirstName(fName);
        creditCard.setLastName(lName);
        creditCard.setOib(oib);
        creditCard.setStatus(status);

        when(mockRepo.findCreditCardByOib(oib)).thenReturn(Optional.of(creditCard));
        when(mockRepo.save(any())).thenThrow(RuntimeException.class);
        CreditCardPrintStatusDto creditCardPrintStatusDto = this.issuingService.issueCard(oib);

        Assertions.assertEquals(oib, creditCardPrintStatusDto.getOib());
        Assertions.assertEquals(CreditCardPrintStatusDto.ProcessStatusEnum.FAILURE,
                creditCardPrintStatusDto.getProcessStatus());
        Assertions.assertNotNull(creditCardPrintStatusDto.getMsg());
    }

}
