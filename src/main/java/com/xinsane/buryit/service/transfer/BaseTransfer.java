package com.xinsane.buryit.service.transfer;

public class BaseTransfer implements Transfer {
    private int error = 0;
    private String msg;

    public int getError() {
        return error;
    }
    public BaseTransfer setError(int error) {
        this.error = error;
        return this;
    }

    public String getMsg() {
        return msg;
    }
    public BaseTransfer setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
