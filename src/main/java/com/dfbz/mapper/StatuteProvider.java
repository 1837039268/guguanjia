package com.dfbz.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/30 18:28
 * @description
 */
public class StatuteProvider {

    public String selectByCondition(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " *  " +
                "FROM " +
                " statute s  " +
                "WHERE " +
                " s.del_flag = 0");
        if (params.containsKey("type") && !StringUtils.isEmpty(params.get("type"))) {
            sb.append(" and s.type = #{type} ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

}
