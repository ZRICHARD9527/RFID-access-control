package com.hasakiii.rfid.result;

public class ResultSuccess extends ResultModel{
    public ResultSuccess(String msg, Object data) {
        super(0, msg, data);
    }
    public ResultSuccess(String msg) {
        super(0, msg, null);
    }
}
