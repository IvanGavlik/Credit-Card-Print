package hr.rba.creditcardprint.issuing;

/**
 * The CsvEntity interface represents an entity that
 * can be converted to and from CSV file.
 *
 * @param <ENTITY> The type of entity to be represented.
 */
public interface CsvEntity<ENTITY> {

    /**
     * The default delimiter used for CSV representation.
     */
    String DEFAULT_CSV_DELIMITER = ",";

    /**
     * Converts the entity to a CSV string representation using the specified delimiter.
     *
     * @param delimiter .
     * @return A string representing the entity in CSV format.
     */
    String toCSV(String delimiter);

    /**
     * Converts a CSV string to an entity using the specified delimiter.
     *
     * @param delimiter The delimiter used in the CSV string.
     * @param line      The line of CSV data representing the entity.
     * @return An instance of the entity populated with data from line param.
     */
    ENTITY toEntity(String delimiter, String line);
}
