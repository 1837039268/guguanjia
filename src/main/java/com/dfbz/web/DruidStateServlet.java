package com.dfbz.web;

import com.alibaba.druid.support.http.StatViewServlet;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/14 20:11
 * @description
 */
@WebServlet(urlPatterns = "/druid/*",initParams = {
        @WebInitParam(name="loginUsername",value = "druid"),//druid性能监控登录页面账号
        @WebInitParam(name="loginPassword",value = "123456"),
        @WebInitParam(name="allow",value = ""),//允许进入性能监控页的ip
        @WebInitParam(name="deny",value = "")//黑名单
})
public class DruidStateServlet extends StatViewServlet {
}
