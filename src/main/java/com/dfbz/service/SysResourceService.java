package com.dfbz.service;

import com.dfbz.domain.SysResource;

import java.util.List;

public interface SysResourceService extends IService<SysResource> {

    List<SysResource> selectByRid(long rid);

    List<SysResource> selectByUid(long uid);
}
