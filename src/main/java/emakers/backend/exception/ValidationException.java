package emakers.backend.exception;

public class ValidationException extends RuntimeException {
    
    public ValidationException(){
        super("Valores inválidos!");
    }

    
}
