package com.dfbz.mapper;

import com.dfbz.domain.SysUser;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import javax.persistence.Column;
import java.util.List;
import java.util.Map;

public interface SysUserMapper extends Mapper<SysUser> {


    /**
     * 根据用户id查询 用户信息和公司信息
     */
    @Select("select  " +
            " su.*,so.name officeName,so.id officeId " +
            "from " +
            " sys_user su,sys_office so " +
            "where " +
            " su.id=#{uid} " +
            "and " +
            " su.office_id=so.id")
    @Results({
            @Result(property = "id", column = "su.id"),
            @Result(property = "sysOffice.id", column = "officeId"),
            @Result(property = "sysOffice.name", column = "officeName")
    })
    SysUser selectById(long uid);


    @SelectProvider(type = SysUserProvider.class, method = "selectByCondition")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "roles", many = @Many(select = "com.dfbz.mapper.SysRoleMapper.selectRoleByUid"))
    })
    List<SysUser> selectByCondition(Map<String, Object> params);

    @Select("SELECT " +
            " su.*, " +
            " so.`name` officeName " +
            "FROM " +
            " sys_user su " +
            " LEFT JOIN sys_office so ON su.office_id = so.id  " +
            "WHERE " +
            " su.id =#{uid}")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "id", property = "roles", many = @Many(select = "com.dfbz.mapper.SysRoleMapper.selectRoleByUid"))
    })
    SysUser selectOneByCondition(long uid);

    @Select("SELECT\n" +
            "\tsu.* \n" +
            "FROM\n" +
            "\tsys_user su,\n" +
            "\tsys_role sr,\n" +
            "\tsys_user_role sur \n" +
            "WHERE\n" +
            "\tsr.id = #{rid} \n" +
            "\tAND sr.id = sur.role_id \n" +
            "\tAND sur.user_id = su.id")
    List<SysUser> selectByRid(long rid);

    @Select("SELECT\n" +
            "\t* \n" +
            "FROM\n" +
            "\tsys_user \n" +
            "WHERE\n" +
            "\toffice_id = #{oid} \n" +
            "\tAND id NOT IN ( SELECT sur.user_id FROM sys_role sr, sys_user_role sur WHERE sr.id = #{rid} AND sr.id = sur.role_id )")
    List<SysUser> selectNoRole(@Param("rid") long rid, @Param("oid") long oid);
}