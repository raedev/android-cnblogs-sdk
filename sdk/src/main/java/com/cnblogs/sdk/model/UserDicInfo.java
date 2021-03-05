package com.cnblogs.sdk.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 用户字典表
 */
@Entity(tableName = "USER_DIC")
public class UserDicInfo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "AUTO_ID")
    public long autoId;
    @ColumnInfo(name = "USER_ID")
    public String userId;
    @ColumnInfo(name = "DIC_KEY")
    public String key;
    @ColumnInfo(name = "DIC_VALUE")
    public String value;

    public long getAutoId() {
        return autoId;
    }

    public void setAutoId(long autoId) {
        this.autoId = autoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
