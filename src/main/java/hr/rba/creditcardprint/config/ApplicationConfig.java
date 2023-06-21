package hr.rba.creditcardprint.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The configuration class that provides access to application settings.
 */
@Component
public class ApplicationConfig {

    @Value("${hr.rba.credicardprint.csvfile.dir}")
    private String fileDire;

    @Value("${hr.rba.credicardprint.csvfile.del}")
    private String fileDel;

    /**
     * Retrieves the directory path where CSV files for credit card printing are stored.
     *
     * @return The directory path for CSV files.
     */
    public String getFileDire() {
        return fileDire;
    }

    /**
     * Retrieves the delimiter used in CSV files for credit card printing.
     *
     * @return The CSV delimiter.
     */
    public String getFileDel() {
        return fileDel;
    }
}
