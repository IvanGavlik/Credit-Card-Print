package hr.rba.creditcardprint.api;

import hr.rba.creditcardprint.integration.BaseIntegrationTest;
import hr.rba.creditcardprint.data.Status;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintDetailsDto;
import hr.rba.creditcardprint.openapi.model.CreditCardPrintStatusDto;
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
        final String oib = "85594729773";

        insertCreditCard(oib);

        Response response = RestAssured.get("/credit-card-print?oib="+oib);

        Assertions.assertEquals(200, response.getStatusCode());

        List<CreditCardPrintDetailsDto> detailsList = Arrays.asList(response.getBody()
                .as(CreditCardPrintDetailsDto[].class));

        Assertions.assertEquals(1, detailsList.size());
        Assertions.assertEquals(oib, detailsList.get(0).getOib());
    }
    @Test
    public void searchCreditCardNotFound() {
        final String oib = "Lorem ipsum";

        Response response = RestAssured.get("/credit-card-print?oib="+oib);

        Assertions.assertEquals(200, response.getStatusCode());

        List<CreditCardPrintDetailsDto> detailsList = Arrays.asList(response.getBody()
                .as(CreditCardPrintDetailsDto[].class));

        Assertions.assertEquals(0, detailsList.size());
    }

    @Test
    public void printCreditCard() {
        final String oib = "77514222966";

        insertCreditCard(oib);

        Response response = RestAssured.put("/credit-card-print?oib="+oib);

        Assertions.assertEquals(200, response. getStatusCode());

        CreditCardPrintStatusDto statusDto = response.getBody().as(CreditCardPrintStatusDto.class);
        Assertions.assertEquals(oib, statusDto.getOib());
        Assertions.assertEquals(CreditCardPrintStatusDto.ProcessStatusEnum.REQUEST_SUCCESSFULLY_WAITING_PRINTER,
                statusDto.getProcessStatus());
        Assertions.assertNull(statusDto.getMsg());

        // status should be IN_PROCESS
        response = RestAssured.get("/credit-card-print?oib="+oib);
        Assertions.assertEquals(200, response.getStatusCode());

        List<CreditCardPrintDetailsDto> detailsList = Arrays.asList(response.getBody()
                .as(CreditCardPrintDetailsDto[].class));

        Assertions.assertEquals(1, detailsList.size());
        Assertions.assertEquals(oib, detailsList.get(0).getOib());
        Assertions.assertEquals(Status.IN_PROCESS.name(), detailsList.get(0).getStatus());
    }

    private void insertCreditCard(String oibValue) {
        final String firstName = "Mario";
        final String lastName = "Jerković";
        final String oib = oibValue;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("firstName", firstName);
        jsonObject.put("lastName", lastName);
        jsonObject.put("oib", oibValue);

        int status = RestAssured.given()
                .header(applicationJsonContent)
                .body(jsonObject.toJSONString())
                .post("/credit-card-print")
                .getStatusCode();

        Assertions.assertEquals(200, status);
    }
}
