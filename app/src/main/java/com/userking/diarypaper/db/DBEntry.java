package com.userking.diarypaper.db;


import cn.litesuits.orm.db.annotation.Column;
import cn.litesuits.orm.db.annotation.NotNull;
import cn.litesuits.orm.db.annotation.PrimaryKey;
import cn.litesuits.orm.db.annotation.Table;

/**
 * Created by ${Jay} on 2016/4/28 0028.
 */
@Table("EntryTable")
public class DBEntry {

    public static final String COL_ID = "_id";
    public static final String COL_KEY = "_key";
    public static final String COL_RESULT= "_result";
    public static final String COL_UPDATETIME= "_updatetime";
    public static final String COL_EXPIRETIME= "_expiretime";

    public DBEntry() {
    }

    public DBEntry(Long updateTime, Long id, String key, String result) {
        this.updateTime = updateTime;
        this.id = id;
        this.key = key;
        this.result = result;
    }

    @PrimaryKey(PrimaryKey.AssignType.AUTO_INCREMENT)
    @Column(COL_ID)
    public Long id;

    @NotNull
    @Column(COL_KEY)
    public String key;

    @NotNull
    @Column(COL_RESULT)
    public String result;

    @NotNull
    @Column(COL_UPDATETIME)
    public Long updateTime;

    @NotNull
    @Column(COL_EXPIRETIME)
    public Long expireTime=-1l;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }
}
