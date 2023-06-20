package hr.rba.creditcardissuing.api;

import hr.rba.creditcardissuing.integration.BaseIntegrationTest;
import hr.rba.creditcardprint.data.Status;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class CreditCardPrintApiTest extends BaseIntegrationTest {

    @Test
    public void testInsertCreditCard() {
        final String firstName = "Marko";
        final String lastName = "Polo";
        final String oib = "57191619656";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        jsonObject.put("oib", oib);

        Response response = RestAssured.given()
                .header(applicationJsonContent)
                .body(jsonObject.toJSONString())
                .post("/credit-card-print");

        Assertions.assertEquals(200, response. getStatusCode());

        CreditCardPrintDetailsDto detailsDto = response.getBody().as(CreditCardPrintDetailsDto.class);
        Assertions.assertEquals(firstName, detailsDto.getFirstName());
        Assertions.assertEquals(lastName, detailsDto.getLastName());
        Assertions.assertEquals(oib, detailsDto.getOib());
        Assertions.assertEquals(Status.NO_ACTIVE.name(), detailsDto.getStatus());
    }

    @Test
    public void searchCreditCardByOib() {
        insertCreditCard();

        final String oib = "85594729773";

        Response response = RestAssured.get("/credit-card-print?oib="+oib);

        Assertions.assertEquals(200, response.getStatusCode());

        List<CreditCardPrintDetailsDto> detailsList = Arrays.asList(response.getBody()
                .as(CreditCardPrintDetailsDto[].class));

        Assertions.assertEquals(1, detailsList.size());
        Assertions.assertEquals(oib, detailsList.get(0).getOib());
    }

    private void insertCreditCard() {
        final String firstName = "Mario";
        final String lastName = "Jerković";
        final String oib = "85594729773";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        jsonObject.put("oib", oib);

        int status = RestAssured.given()
                .header(applicationJsonContent)
                .body(jsonObject.toJSONString())
                .post("/credit-card-print")
                .getStatusCode();

         Assertions.assertEquals(200, status);
    }

    @Test
    public void searchCreditCardNotFound() {
        insertCreditCard();

        final String oib = "Lorem ipsum";

        Response response = RestAssured.get("/credit-card-print?oib="+oib);

        Assertions.assertEquals(200, response.getStatusCode());

        List<CreditCardPrintDetailsDto> detailsList = Arrays.asList(response.getBody()
                .as(CreditCardPrintDetailsDto[].class));

        Assertions.assertEquals(0, detailsList.size());
    }
}
