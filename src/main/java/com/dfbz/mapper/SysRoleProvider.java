package com.dfbz.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/8 16:41
 * @description
 */
public class SysRoleProvider {

    public String selectByCondition(Map<String, Object> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT\n" +
                "\tsr.*,\n" +
                "\tso.`name` officeName \n" +
                "FROM\n" +
                "\tsys_role sr,\n" +
                "\tsys_office so \n" +
                "WHERE\n" +
                "\tsr.office_id = so.id \n" +
                "\tAND sr.del_flag = 0");
        if (params.containsKey("dataScope") && !StringUtils.isEmpty(params.get("dataScope"))) {
            sb.append(" and sr.data_scope=#{dataScope}  ");
        }
        if (params.containsKey("remarks") && !StringUtils.isEmpty(params.get("remarks"))) {
            sb.append(" and sr.remarks=#{remarks} ");
        }
        if (params.containsKey("oid") && !StringUtils.isEmpty(params.get("oid"))) {
            sb.append(" and so.id=#{oid} ");
        }
        if (params.containsKey("name") && !StringUtils.isEmpty(params.get("name"))) {
            sb.append(" AND sr.`name` like CONCAT('%',#{name},'%') ");
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    public String deleteBatch(@Param("rid") long rid, @Param("uids") long[] uids) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE \n" +
                "FROM\n" +
                "\tsys_user_role \n" +
                "WHERE\n" +
                "\trole_id = #{rid} \n" +
                "\tAND user_id IN ");
        sb.append("(");
        for (int i = 0; i < uids.length; i++) {
            sb.append(" #{uids[" + i + "]},");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        System.out.println(sb.toString());
        return sb.toString();
    }

    public String insertBatch(@Param("cids") List<Long> cids, @Param("rid") long rid) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO sys_user_role ( role_id, user_id, create_by, create_date, update_by, update_date, del_flag )\n" +
                "VALUES ");
        for (int i = 0; i < cids.size(); i++) {
            sb.append("(#{rid}, #{cids[" + i + "]}, null, now(), null, now(), 0),");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());
        return sb.toString();
    }

}
