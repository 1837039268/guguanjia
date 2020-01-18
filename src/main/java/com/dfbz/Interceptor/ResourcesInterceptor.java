package com.dfbz.Interceptor;

import com.dfbz.domain.SysResource;
import com.dfbz.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/14 9:00
 * @description
 */
public class ResourcesInterceptor implements HandlerInterceptor {

    @Autowired
    SysResourceService sysResourceService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        List<SysResource> sysResources = sysResourceService.selectAll();
        String uri = request.getRequestURI();
        boolean flag = false;
        for (SysResource sysResource : sysResources) {
            if (!StringUtils.isEmpty(sysResource.getUrl()) && uri.contains(sysResource.getUrl())) {
                flag = true;
                break;
            }
        }
        if (!flag) {
            return true;
        } else {
            boolean hasResources = false;
            List<SysResource> resources = (List<SysResource>) request.getSession().getAttribute("resources");
            if (!StringUtils.isEmpty(resources) && resources.size() > 0) {
                for (SysResource resource : resources) {
                    if (!StringUtils.isEmpty(resource.getUrl()) && uri.contains(resource.getUrl())) {
                        hasResources = true;
                        return true;
                    }
                }
            }
            request.getRequestDispatcher("/index?msg=没有访问权限").forward(request, response);
        }
        return false;
    }
}
