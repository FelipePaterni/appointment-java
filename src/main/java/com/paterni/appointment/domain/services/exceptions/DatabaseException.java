package com.paterni.appointment.domain.services.exceptions;


import org.springframework.http.HttpStatus;

public class DatabaseException extends RuntimeException{
    private HttpStatus status;

    public DatabaseException(String msg, HttpStatus status) {
        super(msg);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }


}