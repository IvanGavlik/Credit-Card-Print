package hr.rba.creditcardissuing.issuing;

import hr.rba.creditcardissuing.data.CreditCard;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreditCardIssuingDtoMapper {
    /**
     * Map entity to dto object.
     *
     * @param creditCard
     * @return dto
     */
    IssuingDto creditCardToIssuingDto(CreditCard creditCard);
}
