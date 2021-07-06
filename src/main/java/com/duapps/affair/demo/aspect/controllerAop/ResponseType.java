package com.duapps.affair.demo.aspect.controllerAop;

public class ResponseType {

    private Integer code;
    private String message;

    public ResponseType(Integer errCode, String errMsg) {
        this.code = errCode;
        this.message = errMsg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ResponseType responseSuccess = new ResponseType(200, "ok");
}
