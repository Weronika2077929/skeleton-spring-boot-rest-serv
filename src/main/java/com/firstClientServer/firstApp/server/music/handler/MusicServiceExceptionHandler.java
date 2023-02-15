package com.firstClientServer.firstApp.server.music.handler;


import jakarta.xml.bind.ValidationException;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class MusicServiceExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = ValidationException.class)
    @ResponseBody
    public ResponseEntity handleTrackException(ValidationException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = TrackNotFoundException.class)
    @ResponseBody
    public ResponseEntity handleTrackNotFoundException(TrackNotFoundException exception) {
        log.info(exception.getMessage());
        return new ResponseEntity(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info(ex.getMessage());
        return new ResponseEntity(extractValidationMessage(ex), HttpStatus.BAD_REQUEST);
    }

    private String extractValidationMessage(MethodArgumentNotValidException exception) {
        log.info(exception.getMessage());
        return exception.getBindingResult().getAllErrors().stream().map(r -> r.getDefaultMessage()).collect(Collectors.toList()).toString();
    }

}
