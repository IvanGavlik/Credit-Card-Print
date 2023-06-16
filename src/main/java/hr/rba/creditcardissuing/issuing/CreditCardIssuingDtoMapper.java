package hr.rba.creditcardissuing.issuing;

import hr.rba.creditcardissuing.data.CreditCard;
import hr.rba.creditcardissuing.openapi.model.IssuingDto;
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
