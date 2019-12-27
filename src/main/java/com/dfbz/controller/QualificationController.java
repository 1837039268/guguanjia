package com.dfbz.controller;

import com.dfbz.domain.Qualification;
import com.dfbz.domain.Result;
import com.dfbz.service.QualificationService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/25 16:18
 * @description
 */
@RestController
@RequestMapping("manager/qualification")
public class QualificationController {

    @Autowired
    QualificationService service;

    @RequestMapping("index")
    public ModelAndView index() {
        return new ModelAndView("/qualification/index");
    }

    @RequestMapping("toList")
    public PageInfo<Qualification> toList(@RequestBody Map<String, Object> params) {
        return service.selectByPage(params);
    }

    /**
     * 根据Qualification的id返回资质对象的公司id和Qualification对象
     */
    @RequestMapping("toUpdate")
    public Map<String, Object> toUpdate(Long id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("qualification", service.selectByPrimaryKey(id));
        map.put("oid", service.selectOfficeId(id));
        return map;
    }

    @RequestMapping("toUpdatePage")
    public ModelAndView toUpdatePage() {
        return new ModelAndView("/qualification/update");
    }

    @RequestMapping("doUpdate")
    public Result doUpdate(@RequestBody Qualification qualification) {
        qualification.setCheck(1);
        Result result = new Result();
        int i = service.updateByPrimaryKeySelective(qualification);
        if (i > 0) {
            result.setSuccess(true);
            result.setMsg("审核通过");
        }
        return result;
    }

    @RequestMapping("doNotUpdate")
    public Result doNotUpdate(@RequestBody Qualification qualification) {
        qualification.setCheck(2);
        Result result = new Result();
        int i = service.updateByPrimaryKeySelective(qualification);
        if (i > 0) {
            result.setSuccess(true);
            result.setMsg("审核不通过");
        }
        return result;
    }

}
