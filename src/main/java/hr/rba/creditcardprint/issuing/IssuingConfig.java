package hr.rba.creditcardprint.issuing;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IssuingConfig {

    /**
     * Instantiate mapper as spring bean.
     *
     * @return CreditCardIssuingDtoMapper as spring bean.
     */
    @Bean
    public CreditCardIssuingDtoMapper mapper() {
        return Mappers.getMapper(CreditCardIssuingDtoMapper.class);
    }
}
