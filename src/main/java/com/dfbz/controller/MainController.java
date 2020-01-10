package com.dfbz.controller;

import com.dfbz.domain.Result;
import com.dfbz.domain.SysUser;
import com.dfbz.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/7 20:11
 * @description
 */
@Controller
public class MainController {

    @Autowired
    SysUserService sysUserService;

    /***
     * 登录页
     */
    @RequestMapping("toLogin")
    public String toLogin() {
        return "/login";
    }

    /**
     * 登录处理
     * 1.校验验证码是否正确
     * 2.正确验证码，则校验登录
     */
    @RequestMapping("doLogin")
    @ResponseBody
    public Result doLogin(@RequestBody Map<String, Object> params, HttpSession session) {
        Result result = new Result();
//获取session中的vcode
        String vcode = (String) session.getAttribute("vcode");

        if (vcode.equals(params.get("code"))) {//校验验证码正确
            SysUser sysUser = new SysUser();
            sysUser.setUsername((String) params.get("username"));
            sysUser.setPassword((String) params.get("password"));
            SysUser checkSysUser = sysUserService.checkSysUser(sysUser);
            if (checkSysUser != null) {//登录成功
                result.setSuccess(true);
                result.setMsg("登录成功");
                result.setObj(checkSysUser);
                //将用户信息放入session
                session.setAttribute("userInfo", checkSysUser);
            }
        }
        return result;
    }

    @RequestMapping("index")
    public String index() {
        return "/index";
    }

}
