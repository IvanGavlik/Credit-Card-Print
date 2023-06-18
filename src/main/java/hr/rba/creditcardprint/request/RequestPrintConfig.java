package hr.rba.creditcardprint.request;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RequestPrintConfig {

    /**
     * Instantiate mapper as spring bean.
     *
     * @return RequestPrintMapper as spring bean.
     */
    @Bean
    public RequestPrintMapper requestPrintMapper() {
        return Mappers.getMapper(RequestPrintMapper.class);
    }
}
