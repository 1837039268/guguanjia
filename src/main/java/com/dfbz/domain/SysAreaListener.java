package com.dfbz.domain;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/3 16:46
 * @description
 */

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.dfbz.mapper.SysAreaMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * 官方文档提到： 该监听器不要被spring容器管理，如果需要使用spring容器中的对象，需要通过构造方法注入方式注入即可
 */
public class SysAreaListener extends AnalysisEventListener<SysArea> {

    private SysAreaMapper sysAreaMapper;
    private List<SysArea> list = new ArrayList<>();

    public SysAreaListener() {
    }

    public SysAreaListener(SysAreaMapper sysAreaMapper) {
        this.sysAreaMapper = sysAreaMapper;
    }

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    @Override
    public void invoke(SysArea sysArea, AnalysisContext analysisContext) {
        list.add(sysArea);
        if (list.size() == 5) {
//            System.out.println("模拟保存操作到数据库.....");
//            System.out.println(list);
            sysAreaMapper.insertBath(list);
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        System.out.println(list.size());
//        System.out.println("模拟最后一次保存操作。。。。。。");
        if (list.size() > 0) {
            sysAreaMapper.insertBath(list);
        }
    }
}
