package com.hasakiii.rfid.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultModel implements Serializable{
    private int errorCode;
    private String msg;
    private Object data ;



    public ResultModel(int errorCode, String msg, Object data) {
        this.errorCode = errorCode;
        this.msg = msg;
        this.data = data;
    }
    public ResultModel(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
        this.data = null;
    }
    public ResultModel(boolean errorCode, String msg) {
        this.errorCode = errorCode ?1:0;
        this.msg = msg;
        this.data = null;
    }

}
