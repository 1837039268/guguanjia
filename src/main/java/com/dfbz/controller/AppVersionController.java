package com.dfbz.controller;

import com.dfbz.domain.AppVersion;
import com.dfbz.domain.Result;
import com.dfbz.service.AppVersionService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/24 17:39
 * @description
 */
@RestController
@RequestMapping("manager/app")
public class AppVersionController {

    @Autowired
    AppVersionService service;

    @RequestMapping("index")
    public ModelAndView toIndex() {
        return new ModelAndView("/app/index");
    }

    @RequestMapping("toList")
    public PageInfo<AppVersion> toList(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "5") Integer pageSize) {
        return service.selectPage(pageNum, pageSize);
    }

    @RequestMapping("toUpdate")
    public AppVersion toUpdate(Integer id) {
        return service.selectByPrimaryKey(id);
    }

    @RequestMapping("toUpdatePage")
    public ModelAndView toUpdatePage() {
        return new ModelAndView("/app/update");
    }

    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody AppVersion appVersion) {
        appVersion.setUpdateDate(new Date());
        Result result = new Result();
        int i = service.updateByPrimaryKeySelective(appVersion);
        if (i > 0) {
            result.setSuccess(true);
            result.setMsg("更新成功");
        }
        return result;
    }

    @RequestMapping("insert")
    public Result insert(@RequestBody AppVersion appVersion) {
        appVersion.setUpdateDate(new Date());
        appVersion.setDelFlag("0");
        appVersion.setCreateDate(new Date());
        Result result = new Result();
        int i = service.insertSelective(appVersion);
        if (i > 0) {
            result.setSuccess(true);
            result.setMsg("添加成功");
        }
        return result;
    }

    @RequestMapping("toDetailPage")
    public ModelAndView toDetailPage() {
        return new ModelAndView("/app/detail");
    }

    @RequestMapping("deleteById")
    public Result deleteById(long id) {
        AppVersion appVersion = new AppVersion();
        appVersion.setId(id);
        appVersion.setDelFlag("1");
        int i = service.updateByPrimaryKeySelective(appVersion);
        Result result = new Result();
        if (i > 0) {
            result.setSuccess(true);
            result.setMsg("删除成功");
        }
        return result;
    }

}
