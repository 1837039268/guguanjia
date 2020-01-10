package com.dfbz.service.impl;

import com.dfbz.domain.Statute;
import com.dfbz.mapper.StatuteMapper;
import com.dfbz.service.StatuteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/30 18:50
 * @description
 */
@Transactional
@Service
@CacheConfig(cacheNames = "statuteCache")
public class StatuteServiceImpl extends IServiceImpl<Statute> implements StatuteService {

    @Autowired
    StatuteMapper statuteMapper;

    @Cacheable(key = "'StatuteServiceImpl:selectByPage'+#params['pageNum']+#params['pageSize']+#params['type']")
    @Override
    public PageInfo<Statute> selectByPage(Map<String, Object> params) {
        if (!params.containsKey("pageNum") || StringUtils.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (!params.containsKey("pageSize") || StringUtils.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 5);
        }
        PageHelper.startPage((int) params.get("pageNum"), (int) params.get("pageSize"));
        List<Statute> list = statuteMapper.selectByCondition(params);
        return new PageInfo<>(list);
    }
}
