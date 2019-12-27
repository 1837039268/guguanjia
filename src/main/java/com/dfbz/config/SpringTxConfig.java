package com.dfbz.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/24 17:01
 * @description
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.dfbz.service")
public class SpringTxConfig {

    @Autowired
    DruidDataSource dataSource;

    @Bean
    public DataSourceTransactionManager getTransactionManager(DruidDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

}
