package com.itsupportme.gis.component.response;

public class AjaxResponse {

    public static final Integer CODE_SUCCESS           = 0;
    public static final Integer CODE_VALIDATION_FAILED = 1;

    private int code = AjaxResponse.CODE_SUCCESS;

    private String message;

    private Object object;

    public AjaxResponse() {

    }

    public AjaxResponse(int code) {
        this.code    = code;
    }

    public AjaxResponse(int code, String message) {
        this.code    = code;
        this.message = message;
    }

    public AjaxResponse(Object object) {
        this.object = object;
    }

    public AjaxResponse(int code, String message, Object object) {
        this.code    = code;
        this.message = message;
        this.object  = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
