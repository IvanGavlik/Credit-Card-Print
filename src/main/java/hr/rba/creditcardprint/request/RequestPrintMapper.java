package hr.rba.creditcardprint.request;

import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintInsertDto;
import org.mapstruct.Mapper;

@Mapper
public interface RequestPrintMapper {
    /**
     * Map dto to entity.
     *
     * @param insertDto
     * @return CreditCard instance
     */
    CreditCard dtoToEntity(CreditCardPrintInsertDto insertDto);

    /**
     *  Maps a CreditCard entity to a CreditCardPrintDetailsDto object.
     *
     * @param creditCard The CreditCard entity to be mapped.
     * @return The CreditCardPrintDetailsDto object representing
     * the details of the credit card.
     */
    CreditCardPrintDetailsDto entityToDetailsDto(CreditCard creditCard);
 }
