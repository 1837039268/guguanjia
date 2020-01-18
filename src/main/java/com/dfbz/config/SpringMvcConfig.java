package com.dfbz.config;

import com.dfbz.Interceptor.LoginInterceptor;
import com.dfbz.Interceptor.ResourcesInterceptor;
import com.dfbz.aspect.LogAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/24 17:28
 * @description
 */
@Configuration
@ComponentScan(basePackages = "com.dfbz.controller")
@EnableWebMvc
@EnableAspectJAutoProxy
public class SpringMvcConfig implements WebMvcConfigurer {

    @Autowired
    ResourcesInterceptor resourcesInterceptor;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver getViewResolver() {
        return new InternalResourceViewResolver("/WEB-INF/html", ".html");
    }

    @Bean("multipartResolver")
    public CommonsMultipartResolver getMultipartResolver() {
        return new CommonsMultipartResolver();
    }

    @Bean
    public ResourcesInterceptor getResourcesInterceptor() {
        return new ResourcesInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        //注册拦截器对象
        InterceptorRegistration loginRegistration = registry.addInterceptor(loginInterceptor);
        //设置拦截逻辑
        loginRegistration.addPathPatterns(new String[]{"/**"});//拦截所有请求
        //设置放行逻辑
        loginRegistration.excludePathPatterns(new String[]{"/toLogin", "/doLogin", "/index", "/manager/menu/selectByUid"});
        loginRegistration.order(1);

        InterceptorRegistration resourcesRegistration = registry.addInterceptor(resourcesInterceptor);
        //设置拦截逻辑
        resourcesRegistration.addPathPatterns(new String[]{"/**"});//拦截所有请求
        resourcesRegistration.excludePathPatterns(new String[]{"/toLogin", "/doLogin", "/index", "/manager/menu/selectByUid"});
        resourcesRegistration.order(2);
    }

    @Bean
    public LogAspect getLogAspect(){
        return new LogAspect();
    }
}
