package com.dfbz.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.dfbz.domain.SysArea;
import com.dfbz.domain.SysAreaListener;
import com.dfbz.mapper.SysAreaMapper;
import com.dfbz.service.SysAreaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/3 16:18
 * @description
 */
@Service
@Transactional
public class SysAreaServiceImpl extends IServiceImpl<SysArea> implements SysAreaService {

    @Autowired
    SysAreaMapper sysAreaMapper;

    @Override
    public OutputStream writeExcel(OutputStream outputStream) {
        //获取Excel写出对象
        ExcelWriter writer = EasyExcel.write(outputStream, SysArea.class).build();
        //获取sheet对象
        WriteSheet sheet = EasyExcel.writerSheet("sysarea数据").build();
        List<SysArea> sysAreas = mapper.selectAll();
        writer.write(sysAreas, sheet);//将数据写出到Excel表的对应sheet位置

        //关闭资源
        writer.finish();
        return outputStream;
    }

    @Override
    public int readExcel(InputStream inputStream) {
        int result = 0;
        ExcelReader excelReader = EasyExcel.read(inputStream, SysArea.class, new SysAreaListener(sysAreaMapper)).build();
        ReadSheet sheet = EasyExcel.readSheet(0).build();
        excelReader.read(sheet);
        excelReader.finish();
        result += 1;
        return result;
    }

    @Override
    public PageInfo<SysArea> selectByPage(Map<String, Object> params) {
        if (!params.containsKey("pageNum") || StringUtils.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (!params.containsKey("pageSize") || StringUtils.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 5);
        }
        PageInfo<SysArea> pageInfo = null;
        PageHelper.startPage((int) params.get("pageNum"), (int) params.get("pageSize"));
        if (params.containsKey("name") && !StringUtils.isEmpty(params.get("name"))) {
            String name = (String) params.get("name");
            SysArea sysArea = new SysArea();
            sysArea.setName(name);
            List<SysArea> select = sysAreaMapper.select(sysArea);
            pageInfo = new PageInfo<>(select);
        } else if (params.containsKey("aid") && !StringUtils.isEmpty(params.get("aid"))) {
            int aid = (int) params.get("aid");
            List<SysArea> list = sysAreaMapper.selectByPid(aid);
            pageInfo = new PageInfo<>(list);
        }
        return pageInfo;
    }

    @Override
    public SysArea selectByAid(long id) {
        SysArea sysArea = sysAreaMapper.selectByAid(id);
        sysArea.setOldParentIds(sysArea.getParentIds());//给旧parentIds绑定数据
        return sysArea;
    }

    /**
     * 1.更新区域的信息
     * 2.根据当前区域是否有更新parentIds来判断是否要更新所有的子区域的parentIds
     *
     * @param sysArea
     * @return
     */
    @Override
    public int updateArea(SysArea sysArea) {
        int i = 0;
        i += sysAreaMapper.updateByPrimaryKey(sysArea);
        if (!sysArea.getOldParentIds().equals(sysArea.getParentIds())) {
            i += sysAreaMapper.updateParentIds(sysArea);//更新所有的子区域的parentIds
        }
        return i;
    }
}
