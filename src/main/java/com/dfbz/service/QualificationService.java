package com.dfbz.service;

import com.dfbz.domain.Qualification;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface QualificationService extends IService<Qualification> {

    PageInfo<Qualification> selectByPage(Map<String, Object> params);

    Long selectOfficeId(long quid);
}
