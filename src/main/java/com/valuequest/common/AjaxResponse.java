package com.valuequest.common;

public class AjaxResponse {

    private boolean success = true;
    private String message;
    private Object data;

    public AjaxResponse(){}
    public AjaxResponse(Object data){
        this.data = data;
    }
    public AjaxResponse(boolean success, String message){
        this.success = success;
        this.message = message;
    }
    public AjaxResponse(boolean success, String message, Object data){
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
}
