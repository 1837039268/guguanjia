package com.dfbz.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/25 16:34
 * @description
 */
public class QualificationProvider {

    public String selectByCondition(Map<String, Object> params) {
        System.out.println(params);
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " qu.*, " +
                " su.`name` upLoadUser, " +
                " cu.`name` checkUser  " +
                "FROM " +
                " qualification qu " +
                " LEFT JOIN sys_user su ON qu.upload_user_id = su.id " +
                " LEFT JOIN sys_user cu ON qu.check_user_id = cu.id  " +
                "WHERE " +
                " qu.del_flag = 0 ");
        if (params.containsKey("type") && !StringUtils.isEmpty(params.get("type"))) {
            sb.append(" and qu.type=#{type}");
        }
        if (params.containsKey("check") && !StringUtils.isEmpty(params.get("check"))) {
            sb.append(" and qu.`check`=#{check}");
        }
        if (params.containsKey("start") && !StringUtils.isEmpty(params.get("start"))) {
            sb.append(" and qu.create_date>=#{start}");
        }
        if (params.containsKey("end") && !StringUtils.isEmpty(params.get("end"))) {
            sb.append(" and qu.create_date<=#{end}");
        }
        return sb.toString();
    }

}
