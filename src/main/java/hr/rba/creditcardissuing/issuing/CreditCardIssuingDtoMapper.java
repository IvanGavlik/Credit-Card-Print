package hr.rba.creditcardissuing.issuing;

import hr.rba.creditcardissuing.data.CreditCard;
import org.mapstruct.Mapper;

@Mapper()
public interface CreditCardIssuingDtoMapper {
    IssuingDto creditCardToIssuingDto(CreditCard creditCard);
}
