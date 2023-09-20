package com.gbadegesin.urlgen.exception;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GenericException extends RuntimeException {

    private HttpStatus httpStatus;
    private String localizedMessage;
    private String message;

    public GenericException(String localizedMessage, String message, HttpStatus httpStatus){
        super(message);
        this.localizedMessage = localizedMessage;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public GenericException(String localizedMessage,HttpStatus httpStatus){
        super();
        this.localizedMessage = localizedMessage;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
