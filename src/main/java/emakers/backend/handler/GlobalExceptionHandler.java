package emakers.backend.handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import emakers.backend.exception.ConflictException;
import emakers.backend.exception.IdNotFoundException;
import emakers.backend.exception.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
    
    @ExceptionHandler(ConflictException.class)
    private ResponseEntity<MensagemDeErro> conflictExceptionHandler(ConflictException conflictException){
        HttpStatus status = HttpStatus.CONFLICT;
        MensagemDeErro response = new MensagemDeErro(status, conflictException.getMessage());
        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(IdNotFoundException.class)
    private ResponseEntity<MensagemDeErro> idNotFoundExceptionHandler(IdNotFoundException idNotFoundException){
        HttpStatus status = HttpStatus.NOT_FOUND;
        MensagemDeErro response = new MensagemDeErro(status, idNotFoundException.getMessage());
        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<MensagemDeErro> badCredentialsExceptionHandler(){
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        MensagemDeErro response = new MensagemDeErro(status, "Não autorizado");
        return ResponseEntity.status(status).body(response);
    }
    
    @ExceptionHandler(ValidationException.class)
    private ResponseEntity<MensagemDeErro> validationExceptionHandler(ValidationException validationException){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        MensagemDeErro response = new MensagemDeErro(status, validationException.getMessage());
        return ResponseEntity.status(status).body(response);
    }




    @ExceptionHandler(DataIntegrityViolationException.class)
    private void handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        conflictExceptionHandler(new ConflictException());
    }


}
