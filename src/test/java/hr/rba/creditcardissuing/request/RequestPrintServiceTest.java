package hr.rba.creditcardissuing.request;

import hr.rba.creditcardprint.data.CreditCardRepository;
import hr.rba.creditcardprint.data.Status;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintInsertDto;
import hr.rba.creditcardprint.request.RequestPrintMapper;
import hr.rba.creditcardprint.request.RequestPrintService;
import hr.rba.creditcardprint.request.RequestPrintServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RequestPrintServiceTest {

    private CreditCardRepository mockRepo = mock(CreditCardRepository.class);
    private RequestPrintService requestPrintService;

    @BeforeEach
    public void setUp() {
        requestPrintService =
                new RequestPrintServiceImpl(mockRepo, Mappers.getMapper(RequestPrintMapper.class));
    }
    @Test
    public void requestForPrintStatusNoActive() {
        // return passed param
        when(mockRepo.save(any())).thenAnswer(i -> i.getArguments()[0]);

        final String name = "Josipa";
        final String lastName ="Jean-Philippe";
        final String oib = "57191619656";

        final CreditCardPrintInsertDto insertDto = new CreditCardPrintInsertDto();
        insertDto.setFirstName(name);
        insertDto.setLastName(lastName);
        insertDto.setOib(oib);

        final CreditCardPrintDetailsDto detailsDto = requestPrintService.requestForPrint(insertDto);

        Assertions.assertEquals(name, detailsDto.getFirstName());
        Assertions.assertEquals(lastName, detailsDto.getLastName());
        Assertions.assertEquals(oib, detailsDto.getOib());
        Assertions.assertEquals(Status.NO_ACTIVE.name(), detailsDto.getStatus());
    }

}
