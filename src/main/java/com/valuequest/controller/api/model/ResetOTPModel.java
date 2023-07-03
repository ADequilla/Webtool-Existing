package com.valuequest.controller.api.model;

public class ResetOTPModel {
    private String instiCode;
    private String to;
    private String msg;
    private String action;
    
    public String getInstiCode() {
        return instiCode;
    }
    public void setInstiCode(String instiCode) {
        this.instiCode = instiCode;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }

    
}
