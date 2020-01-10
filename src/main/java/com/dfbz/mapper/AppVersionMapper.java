package com.dfbz.mapper;


import com.dfbz.domain.AppVersion;
import org.apache.ibatis.annotations.SelectProvider;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AppVersionMapper extends Mapper<AppVersion> {

    @SelectProvider(type=AppVersionProvider.class, method="selectAllNotDel")
    List<AppVersion> selectAllNotDel();

}