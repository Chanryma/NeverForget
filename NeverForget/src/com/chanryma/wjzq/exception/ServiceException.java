package com.chanryma.wjzq.exception;

public class ServiceException extends Exception {
    private static final long serialVersionUID = 1L;

    public ServiceException(Exception e) {
        this.initCause(e);
    }

    public ServiceException(String msg) {
        super(msg);
    }
}
