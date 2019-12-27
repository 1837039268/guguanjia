package com.dfbz.controller;

import com.dfbz.domain.SysOffice;
import com.dfbz.service.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/27 16:38
 * @description
 */
@RestController
@RequestMapping("manager/office")
public class SysOfficeController {

    @Autowired
    SysOfficeService service;


    @RequestMapping("list")
    public List<SysOffice> list (){
        return service.selectAll();
    }

}
