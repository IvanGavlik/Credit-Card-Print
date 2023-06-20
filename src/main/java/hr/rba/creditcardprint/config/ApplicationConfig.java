package hr.rba.creditcardprint.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

    @Value("${hr.rba.credicardprint.csvfile.dir}")
    private String fileDire;

    @Value("${hr.rba.credicardprint.csvfile.del}")
    private String fileDel;

    public String getFileDire() {
        return fileDire;
    }

    public String getfileDel() {
        return fileDel;
    }
}
