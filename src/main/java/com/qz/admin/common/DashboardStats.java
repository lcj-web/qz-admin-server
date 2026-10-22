package com.qz.admin.common;

/**
 * 仪表盘统计指标。
 */
public class DashboardStats {

    private long countryTotal;
    private long policyTotal;
    private long consultTotal;
    private long pendingTotal;
    private long topPolicyTotal;
    private long visTypeTotal;

    public DashboardStats() {
    }

    public DashboardStats(long countryTotal, long policyTotal, long consultTotal,
                          long pendingTotal, long topPolicyTotal, long visTypeTotal) {
        this.countryTotal = countryTotal;
        this.policyTotal = policyTotal;
        this.consultTotal = consultTotal;
        this.pendingTotal = pendingTotal;
        this.topPolicyTotal = topPolicyTotal;
        this.visTypeTotal = visTypeTotal;
    }

    public long getCountryTotal() {
        return countryTotal;
    }

    public void setCountryTotal(long countryTotal) {
        this.countryTotal = countryTotal;
    }

    public long getPolicyTotal() {
        return policyTotal;
    }

    public void setPolicyTotal(long policyTotal) {
        this.policyTotal = policyTotal;
    }

    public long getConsultTotal() {
        return consultTotal;
    }

    public void setConsultTotal(long consultTotal) {
        this.consultTotal = consultTotal;
    }

    public long getPendingTotal() {
        return pendingTotal;
    }

    public void setPendingTotal(long pendingTotal) {
        this.pendingTotal = pendingTotal;
    }

    public long getTopPolicyTotal() {
        return topPolicyTotal;
    }

    public void setTopPolicyTotal(long topPolicyTotal) {
        this.topPolicyTotal = topPolicyTotal;
    }

    public long getVisTypeTotal() {
        return visTypeTotal;
    }

    public void setVisTypeTotal(long visTypeTotal) {
        this.visTypeTotal = visTypeTotal;
    }
}
