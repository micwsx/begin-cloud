package com.micwsx.cloud.bean;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author Michael
 * @create 8/19/2020 12:03 PM
 */
public class ConfigProperties {


    private String driverName;

    private String url;

    private String dbName;

    private String userName;

    private String password;

    private String minPoolSize;

    private String maxPoolSize;

    private String maxIdleTime;

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(String minPoolSize) {
        this.minPoolSize = minPoolSize;
    }

    public String getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(String maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public String getMaxIdleTime() {
        return maxIdleTime;
    }

    public void setMaxIdleTime(String maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    @Override
    public String toString() {
        return "ConfigProperties{" +
                "driverName='" + driverName + '\'' +
                ", url='" + url + '\'' +
                ", dbName='" + dbName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", minPoolSize='" + minPoolSize + '\'' +
                ", maxPoolSize='" + maxPoolSize + '\'' +
                ", maxIdleTime='" + maxIdleTime + '\'' +
                '}';
    }
}
