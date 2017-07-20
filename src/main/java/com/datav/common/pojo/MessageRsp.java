package com.datav.common.pojo;

/**
 * 返回消息封装类
 *
 * @Author YAO
 * @Create 2017/1/12 19:18
 **/
public class MessageRsp {
    private int errorcode = StaticValue.SUCCESS;
    private Object data;
    private String errormsg = StaticValue.MSGSUCCESS;


    public int getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrormsg() {
        return errormsg;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
}
