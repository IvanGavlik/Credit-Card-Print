package hr.rba.creditcardprint.issuing;

public interface CsvEntity {

    String DEFAULT_CSV_DELIMITER = ",";

    String toCSV(String delimiter);
}
