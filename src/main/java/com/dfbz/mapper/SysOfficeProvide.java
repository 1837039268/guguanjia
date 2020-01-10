package com.dfbz.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/6 16:40
 * @description
 */
public class SysOfficeProvide {

    public String selectByCondition(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " so.*, " +
                " sa.`name` areaName  " +
                "FROM " +
                " sys_office so, " +
                " sys_area sa  " +
                "WHERE " +
                " so.del_flag = 0  " +
                " AND so.area_id = sa.id");
        if (params.containsKey("name") && !StringUtils.isEmpty(params.get("name"))) {
            sb.append(" and so.name like concat('%',#{name},'%')");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public String insertBathOfficeWaste(@Param("id") long id, @Param("wasteIds") long[] wasteIds) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO office_waste(`waste_id`, `office_id`, `del_flag`, `create_date`, `update_date`, `create_by`) VALUES ");
        for (int i = 0; i < wasteIds.length; i++) {
            sb.append("(");
            sb.append("#{wasteIds[" + i + "]},#{id},'0',now(),now(),null");
            sb.append("),");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();

    }

}
