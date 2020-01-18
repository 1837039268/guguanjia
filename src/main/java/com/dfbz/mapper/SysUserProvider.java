package com.dfbz.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/7 16:41
 * @description
 */
public class SysUserProvider {

    public String selectByCondition(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " su.*, " +
                " so.`name` officeName  " +
                "FROM " +
                " sys_user su " +
                " LEFT JOIN sys_office so ON su.office_id = so.id " +
                " LEFT JOIN sys_user_role sur ON sur.user_id = su.id " +
                " LEFT JOIN sys_role sr ON sur.role_id = sr.id  " +
                "WHERE " +
                " su.del_flag = 0 ");
        if (params.containsKey("rid") && !StringUtils.isEmpty(params.get("rid"))) {
            sb.append(" and sr.id=#{rid} ");
        }
        if (params.containsKey("uid") && !StringUtils.isEmpty(params.get("uid"))) {
            sb.append(" and su.id=#{uid} ");
        }
        if (params.containsKey("oid") && !StringUtils.isEmpty(params.get("oid"))) {
            sb.append(" and so.id=#{oid} ");
        }
        if (params.containsKey("name") && !StringUtils.isEmpty(params.get("name"))) {
            sb.append(" AND su.`name` like CONCAT('%',#{name},'%') ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public String insertBatch(@Param("oid") long oid, @Param("rids") List<Long> rids) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO sys_role_office ( role_id, office_id, create_by, create_date, update_by, update_date, del_flag )\n" +
                "VALUES ");
        for (int i = 0; i < rids.size(); i++) {
            sb.append("(#{rids[" + i + "]}, #{oid}, null, null, null, null, 0),");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());
        return sb.toString();
    }

}
