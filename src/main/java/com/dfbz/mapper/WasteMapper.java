package com.dfbz.mapper;

import com.dfbz.domain.Waste;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface WasteMapper extends Mapper<Waste> {

    /**
     * 根据office的id查询waste和waste_type信息
     *
     * @param oid
     * @return
     */
    @Select("select " +
            " wa.*,wt.code wasteTypeCode " +
            " from " +
            " sys_office so  " +
            " INNER JOIN " +
            " office_waste ow " +
            " on " +
            " so.id=ow.office_id " +
            " INNER JOIN " +
            " waste wa " +
            " on " +
            " ow.waste_id=wa.id " +
            " LEFT JOIN " +
            " waste_type wt " +
            " on " +
            " wa.parent_id=wt.id " +
            " where " +
            " so.id=#{oid}")
    List<Waste> selectByOid(long oid);

}
