package hr.rba.creditcardprint.issuing;

import hr.rba.creditcardprint.data.CreditCard;
import hr.rba.creditcardprint.data.Status;
/**
 * It represents a credit card entity in CSV format.
 */
public class CreditCardCsvEntity extends CreditCard implements CsvEntity<CreditCard> {

    /**
     * Converts the credit card entity to a CSV string using the specified delimiter.
     *
     * @param delimiter
     * @return The CSV representation of the credit card entity.
     */
    @Override
    public String toCSV(final String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(getFirstName()).append(delimiter)
                .append(getLastName()).append(delimiter)
                .append(getOib()).append(delimiter)
                .append(getStatus()).append(System.lineSeparator());
        return stringBuilder.toString();
    }


    /**
     * Converts the CSV string to a credit card entity using the specified delimiter.
     *
     * @param delimiter
     * @param line      The CSV line representing the credit card entity.
     * @return The credit card entity created from the CSV line.
     */
    @Override
    public CreditCard toEntity(final String delimiter, final String line) {
        String[] data = line.split(delimiter);
        final int firstNameIndex = 0;
        this.setFirstName(data[firstNameIndex]);
        final int lastNameIndex = 1;
        this.setLastName(data[lastNameIndex]);
        final int oibIndex = 2;
        this.setOib(data[oibIndex]);
        final int statusIndex = 3;
        this.setStatus(Status.valueOf(data[statusIndex]));
        return this;
    }
}
