package hr.rba.creditcardprint.issuing;

import hr.rba.creditcardprint.data.CreditCard;

public class CreditCardCsvEntity extends CreditCard implements CsvEntity {
    @Override
    public String toCSV(String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(getFirstName()).append(delimiter)
                .append(getLastName()).append(delimiter)
                .append(getOib()).append(delimiter)
                .append(getStatus()).append(System.lineSeparator());
        return stringBuilder.toString();
    }
}
