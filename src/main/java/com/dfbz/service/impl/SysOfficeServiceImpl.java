package com.dfbz.service.impl;

import com.dfbz.domain.SysOffice;
import com.dfbz.mapper.SysOfficeMapper;
import com.dfbz.service.SysOfficeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/27 16:37
 * @description
 */
@Service
@Transactional
//设置整个类中的缓存默认名
@CacheConfig(cacheNames = "sysOfficeCache")
public class SysOfficeServiceImpl extends IServiceImpl<SysOffice> implements SysOfficeService {

    @Autowired
    SysOfficeMapper sysOfficeMapper;

    /**
     * @return
     * @Cacheable: 使用缓存，不存在key则自动查询数据库，存在则直接返回key对应的缓存数据
     * cacheNames:设置当前缓存数据存放的缓存对象
     * key：缓存的key值，值是springEl表达式语法
     * 1.字符串必须用''扩住   'cn:nyse:service:impl:SysOfficeServiceImpl:selectAll'
     * 2.获取方法的参数的值则可以通过#参数名.属性名     比如方法参数User user     key动态添加user进来    #user
     * 3.获取方法的参数是map类型可以通过#参数名['key']
     */
    @Cacheable(key = "'con.dfbz.service.impl.sysOfficeServiceImpl:selectAll'")
    public List<SysOffice> selectAll() {
        return super.selectAll();
    }

    /**
     * 当发生增删改需要清空所有管理的缓存信息
     * CacheEvict:清除缓存信息
     * allEntries：表示清空所有当前缓存管理对象的key
     *
     * @param record
     * @return
     */
    @CacheEvict(allEntries = true)
    @Override
    public int updateByPrimaryKeySelective(SysOffice record) {
        return super.updateByPrimaryKeySelective(record);
    }

    @Override
    public PageInfo<SysOffice> selectByCondition(Map<String, Object> params) {
        if (!params.containsKey("pageNum") || StringUtils.isEmpty(params.get("pageNum"))) {
            params.put("pageNum", 1);
        }
        if (!params.containsKey("pageSize") || StringUtils.isEmpty(params.get("pageSize"))) {
            params.put("pageSize", 5);
        }
        PageHelper.startPage((int) params.get("pageNum"), (int) params.get("pageSize"));
        List<SysOffice> list = sysOfficeMapper.selectByCondition(params);
        return new PageInfo<>(list);
    }

    @Override
    public SysOffice selectByOid(long oid) {
        return sysOfficeMapper.selectByOid(oid);
    }

    @Override
    public int update(SysOffice sysOffice) {
        long[] wasteIds = null;
        int result = 0;

        mapper.updateByPrimaryKeySelective(sysOffice);
        result += 1;
        sysOfficeMapper.deleteOfficeWaste(sysOffice.getId());
        result += 1;
        if (sysOffice.getWastes().size() > 0) {
            wasteIds = new long[sysOffice.getWastes().size()];
            for (int i = 0; i < sysOffice.getWastes().size(); i++) {
                wasteIds[i] = sysOffice.getWastes().get(i).getId();
            }
            sysOfficeMapper.insertBathOfficeWaste(sysOffice.getId(), wasteIds);
            result += 1;
        }
        return result;
    }

    @Override
    public List<SysOffice> selectByRid(Long rid) {
        return sysOfficeMapper.selectByRid(rid);
    }


}
