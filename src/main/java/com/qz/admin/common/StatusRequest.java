package com.qz.admin.common;

/**
 * 通用状态更新请求体（如咨询状态）。
 */
public class StatusRequest {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
