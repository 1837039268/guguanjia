package com.dfbz.mapper;

import com.dfbz.domain.Examine;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ExamineMapper extends Mapper<Examine> {

    @SelectProvider(type = ExamineProvider.class, method = "selectByCondition")
    List<Examine> selectByCondition(Map<String, Object> params);

}
