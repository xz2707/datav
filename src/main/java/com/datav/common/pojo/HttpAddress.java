package com.datav.common.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * http请求地址
 * Created by xiaozhi on 2017/6/28.
 */
@ConfigurationProperties(prefix = "httpserver")
public class HttpAddress {
    private String labelAddress;
    private String mediaAddress;

    public String getLabelAddress() {
        return labelAddress;
    }

    public void setLabelAddress(String labelAddress) {
        this.labelAddress = labelAddress;
    }

    public String getMediaAddress() {
        return mediaAddress;
    }

    public void setMediaAddress(String mediaAddress) {
        this.mediaAddress = mediaAddress;
    }
}
