package hr.rba.creditcardprint.search;

import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * The mapper interface that provides mapping functionality for converting
 * entities to Dto objects and vice versa.
 */
@Mapper
public interface SearchPrintMapper {

    /**
     * Converts a list of CreditCard entities to a list of CreditCardPrintDetailsDto objects.
     *
     * @param creditCardList The list of CreditCard entities to be converted.
     * @return A list of CreditCardPrintDetailsDto objects.
     */
    List<CreditCardPrintDetailsDto> entitiesToDtos(List<CreditCard> creditCardList);
}
