package hr.rba.creditcardissuing.issuing;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IssuingConfig {
    @Bean
    public CreditCardIssuingDtoMapper mapper() {
        return Mappers.getMapper(CreditCardIssuingDtoMapper.class);
    }
}