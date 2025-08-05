package emakers.backend.exception;

public class IdNotFoundException extends RuntimeException {
    
    public IdNotFoundException(){
        super("Id não encontrado!");
    }

    
}
