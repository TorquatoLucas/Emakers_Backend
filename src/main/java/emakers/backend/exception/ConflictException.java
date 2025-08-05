package emakers.backend.exception;

public class ConflictException extends RuntimeException{
    
    public ConflictException(){
        super("Entidade já criada!");
    }

}
