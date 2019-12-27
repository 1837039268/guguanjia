package com.dfbz.service;

import com.dfbz.domain.AppVersion;
import com.github.pagehelper.PageInfo;

public interface AppVersionService extends IService<AppVersion> {

    PageInfo<AppVersion> selectPage(Integer pageNum, Integer pageSize);

}
