package sorenrahimi.g2s3m2.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(int id){
        super("Il record con id: " + id + " non è stato trovato!");
    }
    public NotFoundException(String message){ super(message);}
}
