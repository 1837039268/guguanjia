package com.dfbz.service.impl;

import com.dfbz.domain.SysResource;
import com.dfbz.mapper.SysResourceMapper;
import com.dfbz.service.SysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/9 17:14
 * @description
 */
@Service
@Transactional
public class SysResourceServiceImpl extends IServiceImpl<SysResource> implements SysResourceService {

    @Autowired
    SysResourceMapper sysResourceMapper;

    @Override
    public List<SysResource> selectByRid(long rid) {
        return sysResourceMapper.selectByRid(rid);
    }

    @Override
    public List<SysResource> selectByUid(long uid) {
        return sysResourceMapper.selectByUid(uid);
    }

    @Override
    @Cacheable(cacheNames = "resourceCache", key = "'com.dfbz.service.impl.SysResourceServiceImpl:selectAll'")
    public List<SysResource> selectAll() {
        return super.selectAll();
    }
}
