package hr.rba.creditcardprint.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


/**
 * The {@code OibValidator} class implements the validation logic for the {@link Oib} annotation.
 * It validates whether a given OIB (Personal Identification Number) is in the correct format.
 */
public class OibValidator implements ConstraintValidator<Oib, String> {


    /**
     * Checks if the provided OIB is valid.
     *
     * @param oib
     * @param constraintValidatorContext
     * @return {@code true} if the OIB is valid, {@code false} otherwise.
     */
    @Override
    public boolean isValid(final String oib,
                           final ConstraintValidatorContext constraintValidatorContext) {
        return checkOIBState(oib);
    }


    /**
     * Checks whether the OIB is in the correct format.
     *
     * @param oib
     * @return {@code true} if the OIB is in the correct format, {@code false} otherwise.
     */
    public static boolean checkOIBState(final String oib) {
        if (oib.length() != 11) {
            return false;
        }

        char[] chars = oib.toCharArray();

        int a = 10;
        final int asciiDigitsOffset = '0';
        for (int i = 0; i < 10; i++) {
            char c = chars[i];
            if (c < '0' || c > '9') {
                return false;
            }

            a = a + (c - asciiDigitsOffset);
            a = a % 10;
            if (a == 0) {
                a = 10;
            }

            a *= 2;
            a = a % 11;
        }
        int kontrolni = 11 - a;
        kontrolni = kontrolni % 10;

        if (kontrolni != (chars[10] - asciiDigitsOffset)) {
            return false;
        }

        return true;
    }

    /*

    api call not working

    private final static String OIB_VALIDATOR_ENDPOINT = "https://www.triviapps.com/validators/oib-validator";
    private final static String OIB_VALID = "OIB VALID!";

    private String askApi(String s) {
        try {
            var values = new HashMap<String, String>() {{
                put("InputText", s);
            }};

            var objectMapper = new ObjectMapper();
            String requestBody = objectMapper
                    .writeValueAsString(values);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OIB_VALIDATOR_ENDPOINT))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Origin", "https://www.triviapps.com")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            return response.body();
        } catch (Exception exception) {
            return null;
        }
        return OIB_VALID;
    }
    */
}
