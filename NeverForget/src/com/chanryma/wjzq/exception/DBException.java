package com.chanryma.wjzq.exception;

public class DBException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String msg;

    public DBException(Exception e) {
        this.initCause(e);
    }

    public DBException(String msg) {
        this.setMsg(msg);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
