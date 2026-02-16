package com.paterni.appointment.domain.services.exceptions;

public class ParameterException extends RuntimeException {

    public ParameterException(String msg) {
        super(msg);
    }

}