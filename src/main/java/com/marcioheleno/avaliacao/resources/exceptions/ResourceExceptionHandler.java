package com.marcioheleno.avaliacao.resources.exceptions;

import com.marcioheleno.avaliacao.services.exceptions.DatabaseException;
import com.marcioheleno.avaliacao.services.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandartError> entityNotFound(ResourceNotFoundException e, HttpServletRequest servletRequest) {
        var status = HttpStatus.NOT_FOUND;
        StandartError standartError = new StandartError();
        standartError.setTimestamp(Instant.now());
        standartError.setStatus(status.value());
        standartError.setError("Resource not found");
        standartError.setMessage(e.getMessage());
        standartError.setPath(servletRequest.getRequestURI());
        return ResponseEntity.status(status).body(standartError);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandartError> dataBase(DatabaseException e, HttpServletRequest servletRequest) {
        var status = HttpStatus.BAD_REQUEST;
        StandartError standartError = new StandartError();
        standartError.setTimestamp(Instant.now());
        standartError.setStatus(status.value());
        standartError.setError("Database exception");
        standartError.setMessage(e.getMessage());
        standartError.setPath(servletRequest.getRequestURI());
        return ResponseEntity.status(status).body(standartError);
    }

}
