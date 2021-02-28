package top.lizec.smartreview;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import top.lizec.smartreview.mapper.KnowledgeDao;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class SmartReviewApplicationTests {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Resource
    KnowledgeDao knowledgeDao;

    @Test
    void contextLoads() {
    }

    @Test
    void databaseBaseTest() {
        //knowledgeDao.
    }

    @Test
    void redisBaseTest() {
        String key = "redisBaseTest";
        String value = "redisBaseTestValue";
        stringRedisTemplate.opsForValue().set(key, value);
        String getValue = stringRedisTemplate.opsForValue().get(key);
        assertEquals(value, getValue);

        stringRedisTemplate.delete(key);
        getValue = stringRedisTemplate.opsForValue().get(key);
        assertNull(getValue);
    }

}
