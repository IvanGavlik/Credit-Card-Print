package hr.rba.creditcardprint.search;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchPrintConfig {

    @Bean
    public SearchPrintMapper searchPrintMapper() {
        return Mappers.getMapper(SearchPrintMapper.class);
    }
}
