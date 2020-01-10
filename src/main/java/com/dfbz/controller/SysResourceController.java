package com.dfbz.controller;

import com.dfbz.domain.SysResource;
import com.dfbz.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/9 17:15
 * @description
 */
@RestController
@RequestMapping("manager/menu")
public class SysResourceController {

    @Autowired
    SysResourceService sysResourceService;

    @RequestMapping("list")
    public List<SysResource> list() {
        return sysResourceService.selectAll();
    }

    @RequestMapping("selectByRid")
    public List<SysResource> selectByRid(Long rid) {
        return sysResourceService.selectByRid(rid);
    }

}
