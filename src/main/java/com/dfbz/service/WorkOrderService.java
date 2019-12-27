package com.dfbz.service;

import com.dfbz.domain.WorkOrder;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface WorkOrderService extends IService<WorkOrder> {

    PageInfo<WorkOrder> selectAll(Map<String, Object> params);

}
