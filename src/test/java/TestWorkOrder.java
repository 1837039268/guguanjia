import com.dfbz.config.SpringMybatisConfig;
import com.dfbz.mapper.WorkOrderMapper;
import com.dfbz.service.StatuteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2019/12/27 17:11
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
public class TestWorkOrder {

    @Autowired
    WorkOrderMapper workOrderMapper;



    @Test
    public void TestWorkOrder() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("status", 2);
        map.put("start", "2016-08-22");
        map.put("end", "2016-12-31");
        map.put("officeId", 54);
        workOrderMapper.selectByCondition(map);
    }




}
