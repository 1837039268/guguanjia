import com.dfbz.config.SpringMybatisConfig;
import com.dfbz.domain.SysOffice;
import com.dfbz.domain.Waste;
import com.dfbz.domain.WasteType;
import com.dfbz.mapper.SysOfficeMapper;
import com.dfbz.mapper.WasteMapper;
import com.dfbz.service.WasteTypeService;
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
 * @date 2020/1/7 15:18
 * @description
 */
@ContextConfiguration(classes = SpringMybatisConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestOffice {

    @Autowired
    WasteTypeService wasteTypeService;

    @Autowired
    SysOfficeMapper sysOfficeMapper;

    @Autowired
    WasteMapper wasteMapper;

    @Test
    public void selectWasteType() {
        List<WasteType> wasteTypes = wasteTypeService.selectAll();
        for (WasteType wasteType : wasteTypes) {
            System.out.println(wasteType.toString());
        }
    }


    @Test
    public void testOfficeMapper() {
//        SysOffice sysOffice = sysOfficeMapper.selectByOid(1L);
        List<Waste> wastes = wasteMapper.selectByOid(1);
        for (Waste waste : wastes) {
            System.out.println("6666666"+waste);
        }
//        System.out.println(sysOffice.toString());
    }

}
