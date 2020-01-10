package com.dfbz.controller;

import com.dfbz.domain.Result;
import com.dfbz.domain.SysUser;
import com.dfbz.service.SysUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/7 17:44
 * @description
 */
@RestController
@RequestMapping("manager/sysuser")
public class SysUserController {

    @Autowired
    SysUserService sysUserService;

    @RequestMapping("")
    public ModelAndView toIndex() {
        return new ModelAndView("/user/user-list");
    }

    @RequestMapping("toDetailPage")
    public ModelAndView toDetailPage() {
        return new ModelAndView("/user/user-detail");
    }

    @RequestMapping("toUpdatePage")
    public ModelAndView toUpdatePage() {
        return new ModelAndView("/user/detail");
    }

    @RequestMapping("toList")
    public PageInfo<SysUser> toList(@RequestBody Map<String, Object> params) {
        return sysUserService.selectByCondition(params);
    }

    @RequestMapping("detail")//toUpdate
    public SysUser detail(long id) {
        return sysUserService.selectOneByCondition(id);
    }

    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody SysUser sysUser) {
        return null;
    }

    /***
     * 根据用户的角色id查询已经分配该角色的用户信息
     */
    @RequestMapping("selectByRid")
    public List<SysUser> selectBtRid(long rid) {
        return sysUserService.selectByRid(rid);
    }

    @RequestMapping("selectNoRole")
    public List<SysUser> selectNoRole(long rid, long oid) {
        return sysUserService.selectNoRole(rid, oid);
    }
}
