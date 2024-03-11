package SpringDoc.uz.pdp.docs.exceptions;

public class StoreNotFoundException extends RuntimeException{
    public StoreNotFoundException(String message) {
        super(message);
    }
}
