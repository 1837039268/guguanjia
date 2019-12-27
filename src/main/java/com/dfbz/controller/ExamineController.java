package com.dfbz.controller;

import com.dfbz.domain.Examine;
import com.dfbz.service.ExamineService;
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
 * @date 2019/12/26 15:32
 * @description
 */
@RestController
@RequestMapping("manager/examine")
public class ExamineController {

    @Autowired
    ExamineService examineService;

    @RequestMapping("index")
    public ModelAndView index() {
        return new ModelAndView("/examine/index");
    }

    @RequestMapping("toList")
    public PageInfo<Examine> toList(@RequestBody Map<String, Object> params) {
        return examineService.selectByPage(params);
    }
}
