package hr.rba.creditcardprint.issuing;

import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.data.Status;

public class CreditCardCsvEntity extends CreditCard implements CsvEntity<CreditCard> {
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

    @Override
    public CreditCard toEntity(String delimiter, String line) {
        String[] data = line.split(delimiter);
        this.setFirstName(data[0]);
        this.setLastName(data[1]);
        this.setOib(data[2]);
        this.setStatus(Status.valueOf(data[3]));
        return this;
    }
}
