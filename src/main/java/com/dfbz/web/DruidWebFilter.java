package com.dfbz.web;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/14 20:12
 * @description
 */
@WebFilter(urlPatterns = "/",initParams = {
        @WebInitParam(name = "exclusions",value = "*.js,*.jpg,*.png,*.css,/druid/*"),//设置忽略规则
        @WebInitParam(name = "profileEnable",value = "true"),//设置单个url的调用sql列表
        @WebInitParam(name = "principalSessionName",value="userInfo")
})
public class DruidWebFilter extends WebStatFilter {
}
