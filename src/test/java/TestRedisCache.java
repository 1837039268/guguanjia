import com.dfbz.config.SpringCacheConfig;
import com.dfbz.config.SpringMybatisConfig;
import com.dfbz.service.StatuteService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhou
 * @version 1.0.1
 * @company 东方标准
 * @date 2020/1/2 17:43
 * @description
 */
@ContextConfiguration(classes = {SpringCacheConfig.class, SpringMybatisConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedisCache {

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    StatuteService statuteService;

    @Test
    public void getRedisConnectionFactory() {
        Jedis jedis = (Jedis) redisConnectionFactory.getConnection().getNativeConnection();
        System.out.println(jedis.keys("*"));
    }

    @Test
    public void TestRedisTemplate() {
//        System.out.println(redisTemplate.keys("*"));
        ValueOperations<String, Object> stringObjectValueOperations = redisTemplate.opsForValue();
        stringObjectValueOperations.set("redisTemplate", "test");
        stringObjectValueOperations.set("张无忌", "张无忌测试");
        System.out.println("----------------");
//        System.out.println(redisTemplate.keys("*"));
        System.out.println(redisTemplate.hasKey("redisTemplate"));//true

        String val1 = (String) stringObjectValueOperations.get("redisTemplate");
        String val2 = (String) stringObjectValueOperations.get("张无忌");
        System.out.println(val1);//test
        System.out.println(val2);//张无忌测试
        System.out.println(redisTemplate.hasKey("num"));//false
        System.out.println(stringObjectValueOperations.get("num"));//null
    }

    @Test
    public void testRedisTemplate2() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("Lettuce");
        strings.add("redis");
        redisTemplate.opsForList().leftPushAll("redisList", strings);

        List<Object> redisList = redisTemplate.opsForList().range("redisList", 0, -1);
        for (Object o : redisList) {
            System.out.println(o);//[Lettuce, redis]
        }
    }

    @Test
    public void getStatuteService() {
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum", 1);
        map.put("pageSize", 5);
        statuteService.selectByPage(map);
    }

}
