package com.dfbz.service;

import com.dfbz.domain.Statute;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface StatuteService extends IService<Statute> {

    PageInfo<Statute> selectByPage(Map<String, Object> params);

}
