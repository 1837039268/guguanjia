import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.dfbz.config.SpringMybatisConfig;
import com.dfbz.domain.SysArea;
import com.dfbz.domain.SysAreaListener;
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
 * @date 2020/1/3 15:44
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)
public class TestEasyExcel {

    @Autowired
    SysAreaMapper sysAreaMapper;

    /**
     * 读取java对象信息，写入到excel
     * <p>
     * EasyExcel:封装了excel读写的api
     */
    @Test
    public void testWrite() {
        //获取Excel 写出对象
        ExcelWriter writer = EasyExcel.write("E:\\temp\\area.xls", SysArea.class).build();
        //获取sheet对象
        WriteSheet sheet = EasyExcel.writerSheet("sysarea数据").build();
        List<SysArea> sysAreas = sysAreaMapper.selectAll();
        writer.write(sysAreas, sheet);//将数据库写出到Excel表的对应的sheet位置

        //关闭资源
        writer.finish();
    }

    /**
     * 读取excel信息，变成java对象
     * 1.编写一个实现了监听器接口的类
     * 2.获取excel读取对象
     * 3.获取sheet对象
     * 4.读资源
     */
    @Test
    public void testRead() {
        ExcelReader excelReader = EasyExcel.read("E:\\temp\\area.xls", SysArea.class, new SysAreaListener(sysAreaMapper)).build();
        ReadSheet sheet = EasyExcel.readSheet(0).build();
        excelReader.read(sheet);
        excelReader.finish();
    }

}
