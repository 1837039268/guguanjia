package com.dfbz.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/24 16:56
 * @description
 */
@Configuration
@MapperScan(basePackages = "com.dfbz.mapper")
@Import(SpringTxConfig.class)
public class SpringMybatisConfig {

    @Bean
    public DruidDataSource getDataSource() {
        DruidDataSource druidDataSource = new DruidDataSource();
        InputStream is = SpringMybatisConfig.class.getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        druidDataSource.configFromPropety(properties);
        return druidDataSource;
    }

    @Bean
    public SqlSessionFactoryBean getFactoryBean(DruidDataSource dataSource) {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);

        tk.mybatis.mapper.session.Configuration configuration = new tk.mybatis.mapper.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLogImpl(Log4j2Impl.class);

        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(new Properties());
        factoryBean.setPlugins(new Interceptor[]{pageInterceptor});
        factoryBean.setConfiguration(configuration);
        return factoryBean;
    }

}
