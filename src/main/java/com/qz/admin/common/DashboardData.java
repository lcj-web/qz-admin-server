package com.qz.admin.common;

import com.qz.admin.entity.Consult;
import com.qz.admin.entity.Country;
import com.qz.admin.entity.Policy;

import java.util.List;

/**
 * 仪表盘聚合数据，结构需与前端 DashboardData 类型保持一致。
 */
public class DashboardData {

    private DashboardStats stats;
    private List<NameCount> continentStats;
    private List<NameCount> categoryStats;
    private List<NameCount> statusStats;
    private List<Country> hotCountries;
    private List<Consult> recentConsults;
    private List<Policy> latestPolicies;

    public DashboardStats getStats() {
        return stats;
    }

    public void setStats(DashboardStats stats) {
        this.stats = stats;
    }

    public List<NameCount> getContinentStats() {
        return continentStats;
    }

    public void setContinentStats(List<NameCount> continentStats) {
        this.continentStats = continentStats;
    }

    public List<NameCount> getCategoryStats() {
        return categoryStats;
    }

    public void setCategoryStats(List<NameCount> categoryStats) {
        this.categoryStats = categoryStats;
    }

    public List<NameCount> getStatusStats() {
        return statusStats;
    }

    public void setStatusStats(List<NameCount> statusStats) {
        this.statusStats = statusStats;
    }

    public List<Country> getHotCountries() {
        return hotCountries;
    }

    public void setHotCountries(List<Country> hotCountries) {
        this.hotCountries = hotCountries;
    }

    public List<Consult> getRecentConsults() {
        return recentConsults;
    }

    public void setRecentConsults(List<Consult> recentConsults) {
        this.recentConsults = recentConsults;
    }

    public List<Policy> getLatestPolicies() {
        return latestPolicies;
    }

    public void setLatestPolicies(List<Policy> latestPolicies) {
        this.latestPolicies = latestPolicies;
    }
}
