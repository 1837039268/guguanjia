package com.dfbz.mapper;

import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/27 16:48
 * @description
 */
public class WorkOrderProvider {

    public String selectByCondition(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " wo.*, " +
                " con.`name` createOfficeName, " +
                " cu.`name` createUserName, " +
                " tu.`name` transportUserName, " +
                " ru.`name` recipientUserName  " +
                "FROM " +
                " work_order wo " +
                " LEFT JOIN sys_user cu ON wo.create_user_id = cu.id " +
                " LEFT JOIN sys_user tu ON wo.transport_user_id = tu.id " +
                " LEFT JOIN sys_user ru ON wo.recipient_user_id = ru.id " +
                " LEFT JOIN sys_office con ON cu.office_id = con.id " +
                " LEFT JOIN sys_office tun ON tu.office_id = tun.id " +
                " LEFT JOIN sys_office run ON ru.office_id = run.id  " +
                "WHERE " +
                " wo.del_flag = 0 ");
        //状态
        if (params.containsKey("status") && !StringUtils.isEmpty(params.get("status"))) {
            sb.append(" and wo.`status`=#{status} ");
        }
        if (params.containsKey("startTime") && !StringUtils.isEmpty(params.get("startTime"))) {
            sb.append(" and wo.create_date>=#{startTime} ");
        }
        if (params.containsKey("endTime") && !StringUtils.isEmpty(params.get("endTime"))) {
            sb.append(" and wo.create_date<=#{endTime} ");
        }
        if (params.containsKey("officeId") && !StringUtils.isEmpty(params.get("officeId"))) {
            sb.append(" and (con.id=#{officeId} or tun.id=#{officeId} or run.id=#{officeId} ) ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

}
