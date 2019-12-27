package com.dfbz.mapper;

import com.dfbz.domain.Qualification;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface QualificationMapper extends Mapper<Qualification> {

    @SelectProvider(type = QualificationProvider.class, method = "selectByCondition")
    List<Qualification> selectByCondition(Map<String, Object> params);

    @Select("SELECT " +
            " su.office_id  " +
            "FROM " +
            " qualification qu " +
            " LEFT JOIN sys_user su ON qu.upload_user_id = su.id  " +
            "WHERE " +
            " qu.id =#{id}")
    Long selectOfficeId(long quid);
}
