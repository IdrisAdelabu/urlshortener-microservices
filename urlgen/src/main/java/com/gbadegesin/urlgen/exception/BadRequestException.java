package com.gbadegesin.urlgen.exception;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;

@Data
@Accessors(chain = true)
public class BadRequestException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public BadRequestException(String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
