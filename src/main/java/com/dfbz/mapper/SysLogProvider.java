package com.dfbz.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/14 16:28
 * @description
 */
public class SysLogProvider {

    public String selectByCondition(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();

        sb.append("select  " +
                " *  " +
                "from " +
                " sys_log ");
        boolean flag = true;
        if (params.containsKey("type") && !StringUtils.isEmpty(params.get("type"))) {
            sb.append(" where type=#{type} ");
            flag = false;
        }
        if (params.containsKey("description") && !StringUtils.isEmpty(params.get("description"))) {
            if (flag) {
                sb.append(" where ");
            } else {
                sb.append(" and ");
            }
            sb.append(" description like concat('%',#{description},'%')");
        }

        System.out.println(sb.toString());
        return sb.toString();
    }

}
