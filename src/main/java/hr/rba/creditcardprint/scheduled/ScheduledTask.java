package hr.rba.creditcardprint.scheduled;

import hr.rba.creditcardprint.config.ApplicationConfig;
import hr.rba.creditcardprint.issuing.PrintJobChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
@EnableAsync
public class ScheduledTask {

    private final PrintJobChecker printJobChecker;
    private final ApplicationConfig config;

    @Autowired
    public ScheduledTask(final PrintJobChecker checker, final ApplicationConfig applicationConfig) {
        this.printJobChecker = checker;
        this.config = applicationConfig;
    }


    // https://www.baeldung.com/cron-expressions
    @Scheduled(cron = "${hr.rba.credicardprint.csvfile.print.schedule}")
    public void checkPrintJob() {
       this.printJobChecker.checkPrintJob();
    }
}
