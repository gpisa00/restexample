package it.arteprogrammazione.restexample.commons.exceptions;

import it.arteprogrammazione.restexample.commons.exceptions.customers.CustomerConflictException;
import it.arteprogrammazione.restexample.commons.exceptions.customers.CustomerNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        AtomicReference<String> errors = new AtomicReference<>(new String());
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.set(errors + " fieldName = " + fieldName + ", errorMessage = " + errorMessage);
        });
        ErrorMessage errorObj = new ErrorMessage(new Date(), errors.get());
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerConflictException.class)
    public final ResponseEntity<ErrorMessage> handleAllExceptions(CustomerConflictException ex) {
        ErrorMessage errorObj = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleAllExceptions(CustomerNotFoundException ex) {
        ErrorMessage errorObj = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorMessage> handleAllExceptions(Exception ex) {
        ErrorMessage errorObj = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
