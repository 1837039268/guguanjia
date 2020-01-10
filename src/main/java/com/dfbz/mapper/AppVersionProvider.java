package com.dfbz.mapper;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/31 17:32
 * @description
 */
public class AppVersionProvider {

    public String selectAllNotDel(){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT " +
                " *  " +
                "FROM " +
                " app_version  " +
                "WHERE " +
                " del_flag = 0");
        return sb.toString();
    }

}
