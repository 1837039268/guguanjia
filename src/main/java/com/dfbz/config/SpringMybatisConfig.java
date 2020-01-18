package com.dfbz.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.logging.log4j2.Log4j2Impl;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import tk.mybatis.spring.annotation.MapperScan;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
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
@PropertySource(value = "classpath:system.properties", encoding = "utf-8")
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

//设置性能监控配置   组合配置  性能监控、sql防火墙、日志信息
        try {
            druidDataSource.setFilters("stat,wall,log4j2");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    /***
     * 设置spring监控
     */
    @Bean("druidStatInterceptor")
    public DruidStatInterceptor getDruidStatInterceptor() {
        return new DruidStatInterceptor();
    }

    //设置代理
    @Bean
    public BeanNameAutoProxyCreator getAutoProxyCreator() {
        BeanNameAutoProxyCreator autoProxyCreator = new BeanNameAutoProxyCreator();
        autoProxyCreator.setInterceptorNames("druidStatInterceptor");
        autoProxyCreator.setProxyTargetClass(true);
        autoProxyCreator.setBeanNames(new String[]{"*Mapper", "*ServiceImpl"});//设置需要监控的类
        return autoProxyCreator;
    }

}
