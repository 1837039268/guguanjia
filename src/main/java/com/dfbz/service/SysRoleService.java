package com.dfbz.service;

import com.dfbz.domain.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;


public interface SysRoleService extends IService<SysRole> {

    PageInfo<SysRole> selectByCondition(Map<String, Object> params);

    int deleteBatch(long rid, long[] uids);

    int insertBatch(List<Long> cids, long rid);

    SysRole selectByUid(Long rid);
}
