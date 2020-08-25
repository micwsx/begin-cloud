package com.micwsx.cloud.conifg;

import com.micwsx.cloud.bean.ConfigProperties;
import com.micwsx.cloud.bean.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;


/**
 * @author Michael
 * @create 8/18/2020 5:51 PM
 */
@RefreshScope
//自定义名称为refresh的scope，scope负责创建此bean过程
//@Scope(RefreshScope.SCOPE_NAME)
@Configuration
public class DataSourceProperties {

    @Value("${config.datasource.driverClassName}")
    private String driverName;
    @Value("${config.datasource.url}")
    private String url;
    @Value("${config.datasource.name}")
    private String dbName;
    @Value("${config.datasource.username}")
    private String userName;
    @Value("${config.datasource.password}")
    private String password;
    @Value("${config.datasource.minPoolSize}")
    private String minPoolSize;
    @Value("${config.datasource.maxPoolSize}")
    private String maxPoolSize;
    @Value("${config.datasource.maxIdleTime}")
    private String maxIdleTime;

//    @Value("${xx.name}")
//    private String zookeeperName;

    @Autowired
    private Environment environment;

    @Override
    public String toString() {
        String env_username = environment.getProperty("config.datasource.username");
        return  "DataSourceProperties{" +hashCode()+
                ", driverName='" + driverName + '\'' +
                ", url='" + url + '\'' +
                ", dbName='" + dbName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", minPoolSize='" + minPoolSize + '\'' +
                ", maxPoolSize='" + maxPoolSize + '\'' +
                ", maxIdleTime='" + maxIdleTime + '\'' +
//                ", zookeeperName='" + zookeeperName + '\'' +
                ", environment("+environment.hashCode()+") env_username=" + env_username +
                '}';
    }

    //        DriverManagerDataSource dataSource=new DriverManagerDataSource(url,userName,password);
//        dataSource.setDriverClassName(driverName);
//        return dataSource;

}
