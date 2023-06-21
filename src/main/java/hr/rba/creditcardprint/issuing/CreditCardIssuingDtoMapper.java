package hr.rba.creditcardprint.issuing;

import hr.rba.creditcardprint.data.CreditCard;
import org.mapstruct.Mapper;
/**
 * The CreditCardIssuingDtoMapper interface is responsible for mapping
 * CreditCard objects to CSV entities.
 */
@Mapper()
public interface CreditCardIssuingDtoMapper {

    /**
     * Maps a CreditCard object to a CreditCardCsvEntity.
     *
     * @param creditCard The CreditCard object to be mapped.
     * @return The corresponding CreditCardCsvEntity.
     */
    CreditCardCsvEntity toCsvEntity(CreditCard creditCard);
}
