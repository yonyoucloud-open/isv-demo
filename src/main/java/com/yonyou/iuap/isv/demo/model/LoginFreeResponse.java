package com.yonyou.iuap.isv.demo.model;

public class LoginFreeResponse {

    /**
     * 友互通用户 id
     */
    private String yhtUserId;

    /**
     * 租户 id
     */
    private String tenantId;

    /**
     * 友空间用户 id
     */
    private String memberId;

    /**
     * 友空间空间 id
     */
    private String qzId;

    public String getYhtUserId() {
        return yhtUserId;
    }

    public void setYhtUserId(String yhtUserId) {
        this.yhtUserId = yhtUserId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getQzId() {
        return qzId;
    }

    public void setQzId(String qzId) {
        this.qzId = qzId;
    }

    @Override
    public String toString() {
        return "LoginFreeResponse{" +
                "yhtUserId='" + yhtUserId + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", qzId='" + qzId + '\'' +
                '}';
    }
}
