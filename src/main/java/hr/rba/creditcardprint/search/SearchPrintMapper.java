package hr.rba.creditcardprint.search;

import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface SearchPrintMapper {
    List<CreditCardPrintDetailsDto> entitiesToDtos(List<CreditCard> creditCardList);
}
