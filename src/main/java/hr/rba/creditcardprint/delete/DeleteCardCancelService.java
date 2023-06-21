package hr.rba.creditcardprint.delete;


/**
 * The interface provides functionality to delete a credit card print entity and mark its files as deleted.
 */
public interface DeleteCardCancelService {

    /**
     * Marker used to indicate that a file has been deleted.
     */
    String FILE_DELETED_MARKER = "_DELETED";

    /**
     * Deletes the credit card with the specified OIB and marks its file as deleted.
     *
     * @param oib
     */
    void delete(String oib);
}
