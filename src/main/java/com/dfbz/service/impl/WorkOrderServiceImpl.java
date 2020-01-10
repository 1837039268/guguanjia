package com.dfbz.service.impl;

import com.dfbz.domain.*;
import com.dfbz.mapper.DetailMapper;
import com.dfbz.mapper.SysUserMapper;
import com.dfbz.mapper.TransferMapper;
import com.dfbz.mapper.WorkOrderMapper;
import com.dfbz.service.WorkOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/27 17:19
 * @description
 */
@Service
@Transactional
public class WorkOrderServiceImpl extends IServiceImpl<WorkOrder> implements WorkOrderService {

    @Autowired
    WorkOrderMapper workOrderMapper;

    @Autowired
    SysUserMapper userMapper;

    @Autowired
    DetailMapper detailMapper;

    @Autowired
    TransferMapper transferMapper;

    @Override
    public PageInfo<WorkOrder> selectAll(Map<String, Object> params) {
        if (!params.containsKey("pageNum") || StringUtils.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (!params.containsKey("pageSize") || StringUtils.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 5);
        }
        PageHelper.startPage((int) params.get("pageNum"), (int) params.get("pageSize"));
        List<WorkOrder> list = workOrderMapper.selectByCondition(params);
        return new PageInfo<>(list);
    }

    @Override
    public Map<String, Object> selectByOid(long oid) {
        WorkOrder workOrder = mapper.selectByPrimaryKey(oid);
        SysUser createUser = userMapper.selectById(workOrder.getCreateUserId());


        SysUser transportUser = null;
        if(!StringUtils.isEmpty(workOrder.getTransportUserId())){
            transportUser=  userMapper.selectById(workOrder.getTransportUserId());
        }

        SysUser recipientUser = null;
        if(!StringUtils.isEmpty(workOrder.getRecipientUserId())){
            recipientUser = userMapper.selectById(workOrder.getRecipientUserId());
        }
        List<Detail> details = detailMapper.selectByOid(oid);
        List<Transfer> transfers = transferMapper.selectByOid(oid);
        HashMap<String, Object> map = new HashMap<>();
        map.put("work",workOrder);
        map.put("createUser",createUser);
        map.put("transportUser",transportUser);
        map.put("recipientUser",recipientUser);
        map.put("details",details);
        map.put("transfers",transfers);

        return map;
    }

}
