package hr.rba.creditcardprint.integration;

import hr.rba.creditcardprint.CreditCardPrintApplication;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(
        classes = CreditCardPrintApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("integrationTest")
@Testcontainers
public abstract class BaseIntegrationTest {
    private final Header applicationJsonContent = new Header("Content-Type", "application/json");

    @LocalServerPort
    private int port;
    @Container
    @ServiceConnection
    private static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:15.1");

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    protected Header getApplicationJsonContent() {
        return this.applicationJsonContent;
    }

}
