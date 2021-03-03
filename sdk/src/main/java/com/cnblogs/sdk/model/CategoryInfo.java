package com.cnblogs.sdk.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 分类信息
 * @author RAE
 * @date 2021/02/26
 */
@Entity(tableName = "CATEGORY")
public class CategoryInfo {

    /**
     * 自增ID
     */
    @PrimaryKey(autoGenerate = true)
    private long autoId;

    /**
     * 分类ID
     */
    private String categoryId;

    /**
     * 上一级ID
     */
    @ColumnInfo(defaultValue = "0")
    private String parentId;

    /**
     * 分类名称
     */
    @SerializedName("name")
    private String name;

    /**
     * 分类类型名称
     */
    @SerializedName("type")
    private String typeName;

    /**
     * 分类排序
     */
    @ColumnInfo(defaultValue = "0")
    private int orderNo;

    /**
     * 状态：0显示，-1隐藏
     */
    @ColumnInfo(defaultValue = "0")
    private int state;

    /**
     * 子类
     */
    @Ignore
    private List<CategoryInfo> children;

    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public List<CategoryInfo> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryInfo> children) {
        this.children = children;
    }
}
