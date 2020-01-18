package com.dfbz.controller;

import com.dfbz.domain.SysLog;
import com.dfbz.service.SysLogService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/14 16:34
 * @description
 */
@RequestMapping("manager/syslog")
@RestController
public class SysLogController {

    @Autowired
    SysLogService sysLogService;

    @RequestMapping("")
    public ModelAndView toIndex() {
        return new ModelAndView("/log/log");
    }

    @RequestMapping("toList")
    public PageInfo<SysLog> selectPage(@RequestBody Map<String, Object> params) {
        return sysLogService.selectByCondition(params);
    }

    @RequestMapping("toDetailPage")
    public ModelAndView toDetailPage() {
        return new ModelAndView("/log/log-detail");
    }

    @RequestMapping("detail")
    public SysLog detail(long id) {
        return sysLogService.selectOneById(id);
    }
}
