package com.dfbz.service;

import com.dfbz.domain.SysLog;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface SysLogService extends IService<SysLog> {

    PageInfo<SysLog> selectByCondition(Map<String, Object> params);

    SysLog selectOneById(long id);
}
