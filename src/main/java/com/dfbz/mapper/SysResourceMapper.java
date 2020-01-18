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

    @Select("SELECT\n" +
            "\tsre.id,\n" +
            "\tsre.`name`,\n" +
            "\tsre.common,\n" +
            "\tsre.icon,\n" +
            "\tsre.sort,\n" +
            "\tsre.parent_id,\n" +
            "\tsre.type,\n" +
            "\tsre.url,\n" +
            "\tsre.description,\n" +
            "\tsre.`status`,\n" +
            "\tsre.parent_ids,\n" +
            "\tsre.create_date,\n" +
            "\tsre.update_date,\n" +
            "\tsre.create_by,\n" +
            "\tsre.update_by,\n" +
            "\tsre.del_flag,\n" +
            "\tsre.permission_str \n" +
            "FROM\n" +
            "\tsys_user sus,\n" +
            "\tsys_user_role sur,\n" +
            "\tsys_role_resource srr,\n" +
            "\tsys_resource sre \n" +
            "WHERE\n" +
            "\tsus.id = #{uid}\n" +
            "\tAND sus.id = sur.user_id \n" +
            "\tAND sur.role_id = srr.role_id \n" +
            "\tAND srr.resource_id = sre.id")
    List<SysResource> selectByUid(long uid);

}
