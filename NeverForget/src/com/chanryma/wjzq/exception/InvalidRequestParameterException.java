package com.chanryma.wjzq.exception;

public class InvalidRequestParameterException extends Exception {
    private static final long serialVersionUID = 1L;

    public InvalidRequestParameterException(Exception e) {
        this.initCause(e);
    }

    public InvalidRequestParameterException(String msg) {
        super(msg);
    }
}
