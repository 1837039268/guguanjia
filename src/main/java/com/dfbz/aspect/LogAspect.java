package com.dfbz.aspect;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/14 19:30
 * @description
 */

import com.dfbz.domain.SysLog;
import com.dfbz.domain.SysUser;
import com.dfbz.service.SysLogService;
import com.dfbz.utils.IPUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 日志处理切面类：
 * 1.将正常操作、异常操作日志记录
 * 2.将日志记录保存到数据库中存放
 */
@Aspect
public class LogAspect {

    @Autowired
    SysLogService sysLogService;

    @Autowired
    HttpServletRequest request;

    @Pointcut(value = "within(com.dfbz.controller.*Controller)")
    public void pointcut() {};

    //正常日志
    @AfterReturning(pointcut = "pointcut()", returning = "obj")
    public void afterReturning(JoinPoint joinPoint, Object obj) {
        saveLog(joinPoint, null);
    }

    //异常日志
    @AfterThrowing(pointcut = "pointcut()", throwing = "e")
    public void afterThrowing(JoinPoint joinPoint, Exception e) {
        saveLog(joinPoint, e);
    }

    //日志保存处理
    private void saveLog(JoinPoint joinPoint, Exception e) {
        SysLog sysLog = new SysLog();
//        System.out.println(request);
//        System.out.println(sysLogService);
        sysLog.setType(e == null ? "1" : "2");
        sysLog.setException(e == null ? "" : e.toString());
        if (request != null) {
            sysLog.setRequestUri(request.getRequestURI());
            sysLog.setRemoteAddr(IPUtils.getClientAddress(request));
            sysLog.setUserAgent(request.getHeader("User-Agent"));
            SysUser userInfo = (SysUser) request.getSession().getAttribute("userInfo");
            if (userInfo != null) {
                sysLog.setCreateBy(userInfo.getName());
            }
            sysLog.setCreateDate(new Date());
            sysLog.setMethod(request.getMethod());
        }
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                String simpleName = args[i].getClass().getSimpleName();
                sb.append("[参数").append(i + 1).append(",类型:").append(simpleName)
                        .append("，值:").append(args[i].toString()).append("],");
            }
            sb.deleteCharAt(sb.length() - 1);
            sysLog.setParams(sb.toString());
        }
        sysLogService.insert(sysLog);
    }
}
