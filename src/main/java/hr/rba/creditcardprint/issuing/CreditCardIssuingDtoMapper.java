package hr.rba.creditcardprint.issuing;

import hr.rba.creditcardprint.data.CreditCard;
import org.mapstruct.Mapper;

@Mapper()
public interface CreditCardIssuingDtoMapper {

    CreditCardCsvEntity toCsvEntity(CreditCard creditCard);
}
