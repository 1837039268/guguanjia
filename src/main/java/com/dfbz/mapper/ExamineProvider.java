package com.dfbz.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/26 17:00
 * @description
 */
public class ExamineProvider {

    public String selectByCondition(Map<String, Object> params) {
        System.out.println(params);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " ex.*, " +
                " su.`name` username, " +
                " so.`name` officeName  " +
                "FROM " +
                " examine ex, " +
                " sys_user su, " +
                " sys_office so  " +
                "WHERE " +
                " ex.del_flag = 0  " +
                " AND ex.examine_user_id = su.id  " +
                " AND su.office_id = so.id");
        if (params.containsKey("officeId") && !StringUtils.isEmpty(params.get("officeId"))) {
            sb.append(" and so.id=#{officeId}");
        }
        if (params.containsKey("userName") && !StringUtils.isEmpty(params.get("userName"))) {
            sb.append(" and su.name like concat('%',#{userName},'%')");
        }
        if (params.containsKey("type") && !StringUtils.isEmpty(params.get("type"))) {
            sb.append(" and ex.type=#{type}");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

}
