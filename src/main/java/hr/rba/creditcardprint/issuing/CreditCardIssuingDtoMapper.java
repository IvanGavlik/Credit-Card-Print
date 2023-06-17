package hr.rba.creditcardprint.issuing;

import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.openapi.model.IssuingDto;
import org.mapstruct.Mapper;

@Mapper()
public interface CreditCardIssuingDtoMapper {
    /**
     * Map entity to dto object.
     *
     * @param creditCard
     * @return dto
     */
    IssuingDto creditCardToIssuingDto(CreditCard creditCard);
}
