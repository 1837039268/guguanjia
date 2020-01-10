package com.dfbz.service.impl;

import com.dfbz.domain.WasteType;
import com.dfbz.service.WasteTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/6 19:21
 * @description
 */
@Service
@Transactional
public class WasteTypeServiceImpl extends IServiceImpl<WasteType> implements WasteTypeService {
}
