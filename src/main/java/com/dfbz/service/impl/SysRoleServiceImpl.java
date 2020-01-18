package com.dfbz.service.impl;

import com.dfbz.domain.SysRole;
import com.dfbz.mapper.SysRoleMapper;
import com.dfbz.service.SysRoleService;
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
 * @date 2020/1/8 16:51
 * @description
 */
@Service
@Transactional
public class SysRoleServiceImpl extends IServiceImpl<SysRole> implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public PageInfo<SysRole> selectByCondition(Map<String, Object> params) {
        if (!params.containsKey("pageNum") || StringUtils.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (!params.containsKey("pageSize") || StringUtils.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 5);
        }
        PageHelper.startPage((int) params.get("pageNum"), (int) params.get("pageSize"));
        List<SysRole> list = sysRoleMapper.selectByCondition(params);
        return new PageInfo<>(list);
    }

    @Override
    public int deleteBatch(long rid, long[] uids) {
        return sysRoleMapper.deleteBatch(rid, uids);
    }

    @Override
    public int insertBatch(List<Long> cids, long rid) {
        return sysRoleMapper.insertBatch(cids, rid);
    }

    @Override
    public SysRole selectByUid(Long rid) {
        return sysRoleMapper.selectByUid(rid);
    }

    @Override
    public int deleteByRoleId(long roleId) {
        return sysRoleMapper.deleteByRoleId(roleId);
    }

    @Override
    public int insertRoleResource(Long[] rid, Long roleId) {
        return sysRoleMapper.insertRoleResource(rid, roleId);
    }

}
