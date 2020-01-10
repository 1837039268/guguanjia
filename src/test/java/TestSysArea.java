import com.dfbz.config.SpringMybatisConfig;
import com.dfbz.domain.SysArea;
import com.dfbz.mapper.SysAreaMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/5 15:05
 * @description
 */

@ContextConfiguration(classes = SpringMybatisConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSysArea {

    @Autowired
    SysAreaMapper sysAreaMapper;

    @Test
    public void testSysAreaMapper(){
        List<SysArea> sysAreas = sysAreaMapper.selectByPid(1);
        for (SysArea sysArea : sysAreas) {
            System.out.println(sysArea);
        }
    }

}
