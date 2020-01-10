package com.dfbz.controller;

import com.dfbz.domain.Qualification;
import com.dfbz.domain.Result;
import com.dfbz.domain.Statute;
import com.dfbz.service.StatuteService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/30 17:46
 * @description
 */
@RestController
@RequestMapping("manager/statute")
public class StatuteController {

    @Autowired
    StatuteService statuteService;

    @RequestMapping("index")
    public ModelAndView index() {
        return new ModelAndView("/statute/index");
    }

    @RequestMapping("toList")
    public PageInfo<Statute> toList(@RequestBody Map<String, Object> params) {
        return statuteService.selectByPage(params);
    }

    @RequestMapping("toUpdate")
    public Statute toUpdate(Long id) {
        return statuteService.selectByPrimaryKey(id);
    }

    @RequestMapping("toUpdatePage")
    public ModelAndView toUpdatePage() {
        return new ModelAndView("/statute/update");
    }

    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody Statute statute) {
        int i = statuteService.updateByPrimaryKeySelective(statute);
        Result result = new Result();
        if (i > 0) {
            result.setSuccess(true);
            result.setMsg("修改成功");
        }
        return result;
    }

    @RequestMapping("insert")
    public Result insert(@RequestBody Statute statute) {
        statute.setCreateDate(new Date());
        statute.setUpdateDate(new Date());
        statute.setDelFlag("0");
        Result result = new Result();
        int i = statuteService.insertSelective(statute);
        if (i > 0) {
            result.setSuccess(true);
            result.setMsg("添加成功");
        }
        return result;
    }

}
