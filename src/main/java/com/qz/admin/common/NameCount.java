package com.qz.admin.common;

/**
 * 名称-数量分布项（大洲分布、分类分布、状态分布共用）。
 */
public class NameCount {

    private String name;
    private int count;

    public NameCount() {
    }

    public NameCount(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
