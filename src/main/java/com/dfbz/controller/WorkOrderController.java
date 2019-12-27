package com.dfbz.controller;

import com.dfbz.domain.WorkOrder;
import com.dfbz.service.WorkOrderService;
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
 * @date 2019/12/27 17:21
 * @description
 */
@RestController
@RequestMapping("manager/admin/work")
public class WorkOrderController {

    @Autowired
    WorkOrderService workOrderService;

    @RequestMapping("")
    public ModelAndView index() {
        return new ModelAndView("/work/admin/work");
    }

    @RequestMapping("toList")
    public PageInfo<WorkOrder> toiList(@RequestBody Map<String, Object> params) {
        return workOrderService.selectAll(params);
    }

}
