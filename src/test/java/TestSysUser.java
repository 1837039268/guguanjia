import com.dfbz.config.SpringMybatisConfig;
import com.dfbz.domain.SysUser;
import com.dfbz.mapper.SysUserMapper;
import com.dfbz.service.SysUserService;
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
 * @date 2020/1/7 17:27
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
public class TestSysUser {

    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysUserService sysUserService;

    @Test
    public void test1() {
        SysUser sysUser = new SysUser();
        List<SysUser> select = sysUserMapper.select(sysUser);

            System.out.println(select.get(0));

    }

}
