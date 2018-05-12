package com.xinsane.buryit.service.transfer;

public class TokenTransfer implements Transfer {
    private int error = 0;
    private String token;
    private String msg;

    public int getError() {
        return error;
    }
    public TokenTransfer setError(int error) {
        this.error = error;
        return this;
    }

    public String getToken() {
        return token;
    }
    public TokenTransfer setToken(String token) {
        this.token = token;
        return this;
    }

    public String getMsg() {
        return msg;
    }
    public TokenTransfer setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
