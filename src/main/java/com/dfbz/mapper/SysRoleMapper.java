package com.dfbz.mapper;

import com.dfbz.domain.SysRole;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysRoleMapper extends Mapper<SysRole> {

    @Select("SELECT " +
            " sr.*  " +
            "FROM " +
            " sys_user su " +
            " LEFT JOIN sys_user_role sur ON su.id = sur.user_id " +
            " LEFT JOIN sys_role sr ON sr.id = sur.role_id  " +
            "WHERE " +
            " su.id =#{uid}")
    List<SysRole> selectRoleByUid(long uid);

    @SelectProvider(type = SysRoleProvider.class, method = "selectByCondition")
    List<SysRole> selectByCondition(Map<String, Object> params);

    @Delete("delete from sys_role_resource where role_id = #{roleId}")
    int deleteByRoleId(long roleId);

    @InsertProvider(type = SysRoleProvider.class,method = "insertRoleResource")
    int insertRoleResource(@Param("rids") Long[] rids, @Param("roleId") Long roleId);

    @DeleteProvider(type = SysRoleProvider.class, method = "deleteBatch")
    int deleteBatch(@Param("rid") long rid, @Param("uids") long[] uids);

    @InsertProvider(type = SysRoleProvider.class, method = "insertBatch")
    int insertBatch(@Param("cids") List<Long> cids, @Param("rid") long rid);

    @Select("")
    SysRole selectByUid(Long rid);


}
