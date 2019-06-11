package org.kimbs.jpademo.exception;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorResponse {

    private Date timestamp;

    private String status;

    private String message;
    
    private String details;
  
    ErrorResponse(Date timestamp, String status, String message, String details) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.details = details;
    }
}