package com.dfbz.service.impl;

import com.dfbz.domain.SysLog;
import com.dfbz.mapper.SysLogMapper;
import com.dfbz.service.SysLogService;
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
 * @date 2020/1/14 16:32
 * @description
 */
@Service
@Transactional
public class SysLogServiceImpl extends IServiceImpl<SysLog> implements SysLogService {

    @Autowired
    SysLogMapper sysLogMapper;

    @Override
    public PageInfo<SysLog> selectByCondition(Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (StringUtils.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 5);
        }
        PageHelper.startPage((Integer) params.get("pageNum"), (Integer) params.get("pageSize"));

        List<SysLog> list = sysLogMapper.selectByCondition(params);
        return new PageInfo<>(list);
    }

    @Override
    public SysLog selectOneById(long id) {
        return sysLogMapper.selectOneById(id);
    }
}
