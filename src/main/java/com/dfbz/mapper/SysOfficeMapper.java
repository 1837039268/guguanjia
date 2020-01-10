package com.dfbz.mapper;

import com.dfbz.domain.SysOffice;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SysOfficeMapper extends Mapper<SysOffice> {

    @SelectProvider(type = SysOfficeProvide.class, method = "selectByCondition")
    List<SysOffice> selectByCondition(Map<String, Object> params);

    //关联映射
    @Select("select so.*,sa.name areaName from  " +
            " sys_office so,sys_area sa " +
            " where " +
            " so.id=#{oid} " +
            " and " +
            " so.del_flag=0 " +
            " and " +
            " so.area_id=sa.id ")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "areaName", property = "areaName"),
            @Result(column = "id", property = "wastes", many = @Many(select = "com.dfbz.mapper.WasteMapper.selectByOid"))
    })
    SysOffice selectByOid(long oid);

    @Delete("delete from office_waste where office_id=#{id}")
    int deleteOfficeWaste(long id);


    @InsertProvider(type = SysOfficeProvide.class, method = "insertBathOfficeWaste")
    int insertBathOfficeWaste(@Param("id") long id, @Param("wasteIds") long[] wasteIds);

    @Select("SELECT\n" +
            "\tso.* \n" +
            "FROM\n" +
            "\tsys_role_office sro,\n" +
            "\tsys_office so \n" +
            "WHERE\n" +
            "\tso.id = sro.office_id \n" +
            "\tAND sro.role_id =#{rid}")
    List<SysOffice> selectByRid(Long rid);
}
