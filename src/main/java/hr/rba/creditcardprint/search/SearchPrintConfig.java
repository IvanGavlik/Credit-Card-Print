package hr.rba.creditcardprint.search;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for search package.
 */
@Configuration
public class SearchPrintConfig {

    /**
     * Creates and returns an instance of the SearchPrintMapper as spring bean.
     *
     * @return The SearchPrintMapper instance as spring bean.
     */
    @Bean
    public SearchPrintMapper searchPrintMapper() {
        return Mappers.getMapper(SearchPrintMapper.class);
    }
}
