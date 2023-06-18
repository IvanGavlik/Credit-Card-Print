package hr.rba.creditcardprint.request;

import hr.rba.creditcardprint.data.CreditCard;
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
 }
