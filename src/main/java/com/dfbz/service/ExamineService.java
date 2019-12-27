package com.dfbz.service;

import com.dfbz.domain.Examine;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface ExamineService extends IService<Examine> {

    PageInfo<Examine> selectByPage(Map<String, Object> params);

}
