package com.dfbz.mapper;

import com.dfbz.domain.SysLog;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysLogMapper extends Mapper<SysLog> {

    @SelectProvider(type = SysLogProvider.class, method = "selectByCondition")
    List<SysLog> selectByCondition(Map<String, Object> params);

    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tsys_log \n" +
            "WHERE\n" +
            "\tid = #{id}")
    SysLog selectOneById(long id);
}
