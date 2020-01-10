package com.dfbz.service.impl;

import com.dfbz.domain.AppVersion;
import com.dfbz.mapper.AppVersionMapper;
import com.dfbz.service.AppVersionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/24 17:26
 * @description
 */
@Service
public class AppVersionServiceImpl extends IServiceImpl<AppVersion> implements AppVersionService {

    @Autowired
    AppVersionMapper appVersionMapper;

    @Override
    public PageInfo<AppVersion> selectPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AppVersion> list = appVersionMapper.selectAllNotDel();
        return new PageInfo<>(list);
    }
}
