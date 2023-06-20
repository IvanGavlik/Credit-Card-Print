package hr.rba.creditcardprint.delete;



public interface DeleteCardCancelService {
    String FILE_DELETED_MARKER = "_DELETED";

    void delete(String oib);
}
