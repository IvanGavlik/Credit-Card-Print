package hr.rba.creditcardprint.scheduled;

import hr.rba.creditcardprint.config.ApplicationConfig;
import hr.rba.creditcardprint.issuing.PrintJobChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Configuration class for scheduling and executing tasks.
 */
@Configuration
@EnableScheduling
@EnableAsync
public class ScheduledTask {

    private final PrintJobChecker printJobChecker;
    private final ApplicationConfig config;

    /**
     * Constructor.
     *
     * @param checker           the print job checker
     * @param applicationConfig the application configuration
     */
    @Autowired
    public ScheduledTask(final PrintJobChecker checker, final ApplicationConfig applicationConfig) {
        this.printJobChecker = checker;
        this.config = applicationConfig;
    }


    /**
     * Scheduled method to check the print job.
     * <p>
     * This method is executed based on the cron expression specified in the application configuration.
     * It triggers the {@link PrintJobChecker} to check for print jobs and perform necessary actions.
     */
    @Scheduled(cron = "${hr.rba.credicardprint.csvfile.print.schedule}") // https://www.baeldung.com/cron-expressions
    public void checkPrintJob() {
       this.printJobChecker.checkPrintJob();
    }
}
