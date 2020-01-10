package com.dfbz.controller;

import com.dfbz.domain.Result;
import com.dfbz.domain.SysRole;
import com.dfbz.service.SysRoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2020/1/8 16:24
 * @description
 */
@RestController
@RequestMapping("manager/role")
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @RequestMapping("")
    public ModelAndView toIndex() {
        return new ModelAndView("/role/role");
    }

    @RequestMapping("toList")
    public PageInfo<SysRole> toList(Map<String, Object> params) {
        return sysRoleService.selectByCondition(params);
    }

    @RequestMapping("toRoleUser")
    public ModelAndView toRoleUser() {
        return new ModelAndView("/role/role-user");
    }

    @RequestMapping("toDetailPage")
    public ModelAndView toDetailPage() {
        return new ModelAndView("/role/role-detail");
    }

    @RequestMapping("toUpdatePage")
    public ModelAndView toUpdatePage() {
        return new ModelAndView("/role/role-save");
    }

    @RequestMapping("deleteBatch")
    public Result deleteBatch(long rid, long[] uids) {
        Result result = new Result();
        int i = sysRoleService.deleteBatch(rid, uids);
        if (i > 0) {
            result.setMsg("移出成功");
            result.setSuccess(true);
        }
        return result;
    }

    @RequestMapping("insertBatch")
    public Result insertBatch(long rid, Long[] cids) {
        List<Long> list = new ArrayList<>();
        int i = sysRoleService.insertBatch(Arrays.asList(cids), rid);
        Result result = new Result();
        if (i > 0) {
            result.setSuccess(true);
            result.setMsg("更新成功");
        }
        return result;
    }

    @RequestMapping("toUpdate")
    public SysRole toUpdate(Long rid) {
//        return sysRoleService.selectByUid(id);
        return null;
    }

}
