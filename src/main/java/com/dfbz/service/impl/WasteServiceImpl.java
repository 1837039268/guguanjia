package com.dfbz.service.impl;

import com.dfbz.domain.Waste;
import com.dfbz.service.WasteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/6 19:20
 * @description
 */
@Service
@Transactional
public class WasteServiceImpl extends IServiceImpl<Waste> implements WasteService {
}
