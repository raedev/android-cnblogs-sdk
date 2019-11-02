package com.cnblogs.api.db.model;

import com.cnblogs.api.model.CategoryBean;

public class CategoryDbModel extends CategoryBean {

    // 排序
    private int orderNo;

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
}
