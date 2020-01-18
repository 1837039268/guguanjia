package com.dfbz.service.impl;

import com.dfbz.domain.SysUser;
import com.dfbz.mapper.SysUserMapper;
import com.dfbz.service.SysUserService;
import com.dfbz.utils.EncryptUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/7 17:14
 * @description
 */
@Service
@Transactional
public class SysUserServiceImpl extends IServiceImpl<SysUser> implements SysUserService {

    @Autowired
    SysUserMapper sysUserMapper;

    @Override
    public PageInfo<SysUser> selectByCondition(Map<String, Object> params) {
        if (!params.containsKey("pageNum") || StringUtils.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (!params.containsKey("pageSize") || StringUtils.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 5);
        }
        PageHelper.startPage((int) params.get("pageNum"), (int) params.get("pageSize"));
        List<SysUser> list = sysUserMapper.selectByCondition(params);
        return new PageInfo<>(list);
    }

    @Override
    public SysUser selectOneByCondition(long uid) {
        SysUser sysUser = sysUserMapper.selectOneByCondition(uid);
        sysUser.setPassword(null);
        return sysUser;
    }

    @Override
    public SysUser checkSysUser(SysUser record) {
        record.setPassword(EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(record.getPassword()) + record.getUsername()));
        List<SysUser> select = sysUserMapper.select(record);
        if (select.size() > 0) {
            return select.get(0);
        }
        return null;
    }

    @Override
    public List<SysUser> selectByRid(long rid) {
        return sysUserMapper.selectByRid(rid);
    }

    @Override
    public List<SysUser> selectNoRole(long rid, long oid) {
        return sysUserMapper.selectNoRole(rid, oid);
    }

    @Override
    public int insertBatch(long oid, List<Long> rids) {
        return sysUserMapper.insertBatch(oid, rids);
    }
}
