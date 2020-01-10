package com.dfbz.mapper;

import com.dfbz.domain.SysResource;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysResourceMapper extends Mapper<SysResource> {

    @Select("SELECT\n" +
            "\tsre.* \n" +
            "FROM\n" +
            "\tsys_role_resource srr,\n" +
            "\tsys_resource sre \n" +
            "WHERE\n" +
            "\tsrr.role_id = #{rid} \n" +
            "\tAND srr.resource_id = sre.id")
    List<SysResource> selectByRid(Long rid);

}
