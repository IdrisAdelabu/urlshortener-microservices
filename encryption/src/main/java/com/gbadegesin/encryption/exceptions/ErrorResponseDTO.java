package com.gbadegesin.encryption.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
//@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ErrorResponseDTO {

    private HttpStatus status;
    private String responseMessage;
    private String responseCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private String timestamp;
    private String message;
    private String debugMessage;

    private ErrorResponseDTO() {
        this.timestamp = String.valueOf(LocalDateTime.now());
    }

    public ErrorResponseDTO(HttpStatus status) {
        this();
        this.status = status;
    }

    public ErrorResponseDTO(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.responseCode = "" + status.value();
        this.responseMessage = status.getReasonPhrase();
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ErrorResponseDTO(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.responseCode = "" + status.value();
        this.responseMessage = status.getReasonPhrase();
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();

    }
}
