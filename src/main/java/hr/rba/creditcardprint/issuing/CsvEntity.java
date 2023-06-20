package hr.rba.creditcardprint.issuing;

public interface CsvEntity<ENTITY> {

    String DEFAULT_CSV_DELIMITER = ",";

    String toCSV(String delimiter);

    ENTITY toEntity(String delimiter, String line);
}
