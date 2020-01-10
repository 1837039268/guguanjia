package com.dfbz.mapper;

import com.dfbz.domain.SysArea;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysAreaMapper extends Mapper<SysArea> {

    @SelectProvider(type = SysAreaProvider.class, method = "insertBath")
//    int insertBath(@Param("areas") List<SysArea> areas);
    Integer insertBath(@Param("areas") List<SysArea> areas);


    /**
     * 根据父区域id查找所有区域
     *
     * @return
     */
    @Select("SELECT " +
            " sub.*, " +
            " parent.`name` parentName  " +
            "FROM " +
            " sys_area sub, " +
            " sys_area parent  " +
            "WHERE " +
            " sub.parent_ids LIKE concat('%',#{aid},'%') " +
            " AND sub.parent_id = parent.id")
    List<SysArea> selectByPid(long aid);

    @Select("SELECT " +
            " sub.*, " +
            " parent.`name` parentName  " +
            "FROM " +
            " sys_area sub, " +
            " sys_area parent  " +
            "WHERE " +
            " sub.parent_id = parent.id  " +
            " AND sub.id = #{id}")
    SysArea selectByAid(long id);

    /**
     * 根据父区域更新所有的子区域的parentIds
     *
     * @param sysArea
     * @return
     */
    @Update("UPDATE sys_area  " +
            "SET parent_ids = REPLACE( parent_ids, #{oldParentIds}, #{parentIds} )  " +
            "WHERE " +
            " parent_ids LIKE concat( '%,', #{id} , ',%' )")
    int updateParentIds(SysArea sysArea);

}
