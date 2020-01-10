package com.dfbz.service;

import com.dfbz.domain.SysOffice;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface SysOfficeService extends IService<SysOffice> {

    PageInfo<SysOffice> selectByCondition(Map<String, Object> params);

    SysOffice selectByOid(long oid);

    int update(SysOffice sysOffice);

    List<SysOffice> selectByRid(Long rid);
}
