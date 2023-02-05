package com.firstClientServer.firstApp.server.music.handler;


import jakarta.xml.bind.ValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class MusicServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    public ResponseEntity handleTrackException(ValidationException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = TrackNotFoundException.class)
    @ResponseBody
    public ResponseEntity handleTrackNotFoundException(TrackNotFoundException exception) {
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return new ResponseEntity(extractValidationMessage(ex), HttpStatus.BAD_REQUEST);
    }

    private String extractValidationMessage(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().stream().map(r -> r.getDefaultMessage()).collect(Collectors.toList()).toString();
    }

}
