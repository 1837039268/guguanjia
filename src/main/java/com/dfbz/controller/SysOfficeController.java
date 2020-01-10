package com.dfbz.controller;

import com.dfbz.domain.Result;
import com.dfbz.domain.SysOffice;
import com.dfbz.domain.Waste;
import com.dfbz.domain.WasteType;
import com.dfbz.service.SysOfficeService;
import com.dfbz.service.WasteService;
import com.dfbz.service.WasteTypeService;
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
 * @date 2019/12/27 16:38
 * @description
 */
@RestController
@RequestMapping("manager/office")
public class SysOfficeController {

    @Autowired
    SysOfficeService sysOfficeService;

    @Autowired
    WasteService wasteService;

    @Autowired
    WasteTypeService wasteTypeService;


    @RequestMapping("list")
    public List<SysOffice> list() {
        return sysOfficeService.selectAll();
    }

    @RequestMapping("")
    public ModelAndView toIndex() {
        return new ModelAndView("/office/office");
    }

    @RequestMapping("toDetailPage")
    public ModelAndView toDetailPage() {
        return new ModelAndView("/office/detail");
    }

    @RequestMapping("toUpdatePage")
    public ModelAndView toUpdatePage() {
        return new ModelAndView("/office/update");
    }

    @RequestMapping("toList")
    public PageInfo<SysOffice> toList(@RequestBody Map<String, Object> params) {
        return sysOfficeService.selectByCondition(params);
    }

    @RequestMapping("toUpdate")
    public SysOffice toUpdate(Long oid) {
        return sysOfficeService.selectByOid(oid);
    }

    @RequestMapping("selectWaste")
    public List<Waste> selectWaste(Long selected) {
        Waste waste = new Waste();
        waste.setParentId(selected);
        return wasteService.select(waste);
    }

    @RequestMapping("selectWasteType")
    public List<WasteType> selectWasteType() {
        return wasteTypeService.selectAll();
    }

    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody SysOffice sysOffice) {
        int update = sysOfficeService.update(sysOffice);
        Result result = new Result();
        if (update > 0) {
            result.setMsg("更新成功");
            result.setSuccess(true);
        }
        return result;
    }

    @RequestMapping("selectByRid")
    public List<SysOffice> selectByRid(Long rid) {
        return sysOfficeService.selectByRid(rid);
    }

}
