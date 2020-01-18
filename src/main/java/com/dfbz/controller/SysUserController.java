package com.dfbz.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dfbz.domain.Result;
import com.dfbz.domain.SysUser;
import com.dfbz.service.SysUserService;
import com.dfbz.utils.EncryptUtils;
import com.github.pagehelper.PageInfo;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
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
//    public Result doUpdate(@RequestBody SysUser sysUser) {
    public Result doUpdate(String user) {
        int i = 0;
        SysUser sysUser = JSON.parseObject(user, SysUser.class);
        String username = sysUser.getUsername();
        String password = sysUser.getPassword();
        if (password == null) {
            i += sysUserService.updateByPrimaryKeySelective(sysUser);
        } else {
            String md5Password = EncryptUtils.MD5_HEX(password);
            sysUser.setPassword(EncryptUtils.MD5_HEX(username + md5Password));
            i += sysUserService.updateByPrimaryKeySelective(sysUser);
        }
        Result result = new Result();
        if (i > 0) {
            result.setSuccess(true);
            result.setMsg("修改成功");
        }
        return result;
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

    @RequestMapping("insertBatch")
    public Result insertBatch(long oid, Long[] rids, String sysUser) {
        SysUser user = JSON.parseObject(sysUser, SysUser.class);
//        System.out.println(user);
        user.setPassword(EncryptUtils.MD5_HEX(user.getUsername() + EncryptUtils.MD5_HEX(user.getPassword())));
        user.setDelFlag("0");
        user.setOfficeId(oid);
        int i = 0;
        i += sysUserService.insertBatch(oid, Arrays.asList(rids));
        i += sysUserService.insertSelective(user);
        Result result = new Result();
        if (i > 0) {
            result.setSuccess(true);
            result.setMsg("添加成功");
        }
        return result;
    }
}
