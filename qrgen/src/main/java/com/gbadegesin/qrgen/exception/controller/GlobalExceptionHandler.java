package com.gbadegesin.qrgen.exception.controller;


import com.gbadegesin.qrgen.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;




@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(BadHeaderValuesException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadHeaderValuesException(HttpServletRequest request, BadHeaderValuesException ex){
        log.error("generic exception occurred while trying to process request: {} because: {}",request.getRequestURI(),
                ex.getMessage());

        return ResponseEntity.status((ex.getHttpStatus() == null) ? HttpStatus.INTERNAL_SERVER_ERROR :
                ex.getHttpStatus()).body((ex.getMessage() == null) ? (new ErrorResponseDTO(ex.getHttpStatus(), ex)) :
                (new ErrorResponseDTO(ex.getHttpStatus(), ex.getMessage(), ex)));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequestException(HttpServletRequest request, BadRequestException ex){
        log.error("BadRequestException exception occurred while trying to process request: {} because: {}",
                request.getRequestURI(),ex.getMessage());

        return ResponseEntity.status((ex.getHttpStatus() == null) ? HttpStatus.BAD_REQUEST :
                ex.getHttpStatus()).body((ex.getMessage() == null) ? (new ErrorResponseDTO(ex.getHttpStatus(), ex)) :
                (new ErrorResponseDTO(ex.getHttpStatus(), ex.getMessage(), ex)));
    }

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(HttpServletRequest request, GenericException ex){
        log.error("generic exception occurred while trying to process request: {} because: {}", request.getRequestURI(),
                ex.getMessage());

        return ResponseEntity.status((ex.getHttpStatus() == null) ? HttpStatus.INTERNAL_SERVER_ERROR :
                ex.getHttpStatus()).body((ex.getMessage() == null) ? (new ErrorResponseDTO(ex.getHttpStatus(), ex)) :
                (new ErrorResponseDTO(ex.getHttpStatus(), ex.getMessage(), ex)));
    }

    @ExceptionHandler(MissingHeaderException.class)
    public ResponseEntity<ErrorResponseDTO> handleMissingHeaderException(HttpServletRequest request, MissingHeaderException ex){
        log.error("generic exception occurred while trying to process request: {} because: {}",request.getRequestURI(),
                ex.getMessage());

        return ResponseEntity.status((ex.getHttpStatus() == null) ? HttpStatus.INTERNAL_SERVER_ERROR :
                ex.getHttpStatus()).body((ex.getMessage() == null) ? (new ErrorResponseDTO(ex.getHttpStatus(), ex)) :
                (new ErrorResponseDTO(ex.getHttpStatus(), ex.getMessage(), ex)));
    }


//    @ExceptionHandler(NullPointerException.class)
//    public ResponseEntity<ErrorResponseDTO> nullException(HttpServletRequest request, MissingHeaderException ex){
//        log.error("generic exception occurred while trying to process request: {} because: {}",request.getRequestURI(),
//                ex.getMessage());
//
//        return ResponseEntity.status((ex.getHttpStatus() == null) ? HttpStatus.BAD_REQUEST :
//                ex.getHttpStatus()).body((ex.getMessage() == null) ? (new ErrorResponseDTO(ex.getHttpStatus(), ex)) :
//                (new ErrorResponseDTO(ex.getHttpStatus(), ex.getMessage(), ex)));
//    }

}
