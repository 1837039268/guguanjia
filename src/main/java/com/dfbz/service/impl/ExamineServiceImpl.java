package com.dfbz.service.impl;

import com.dfbz.domain.Examine;
import com.dfbz.mapper.ExamineMapper;
import com.dfbz.service.ExamineService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/26 17:36
 * @description
 */
@Service
@Transactional
public class ExamineServiceImpl extends IServiceImpl<Examine> implements ExamineService {

    @Autowired
    ExamineMapper examineMapper;

    @Override
    public PageInfo<Examine> selectByPage(Map<String, Object> params) {

        if (!params.containsKey("pageNum") || StringUtils.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (!params.containsKey("pageSize") || StringUtils.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 5);
        }
        PageHelper.startPage((int) params.get("pageNum"), (int) params.get("pageSize"));
        List<Examine> list = examineMapper.selectByCondition(params);
        return new PageInfo<>(list);

    }
}
