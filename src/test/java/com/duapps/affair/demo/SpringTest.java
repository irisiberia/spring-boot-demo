package com.duapps.affair.demo;

import com.duapps.affair.demo.Configers.StudentBean;
import com.duapps.affair.demo.aspect.annotationTest.ArithmeticCalculator;
import com.duapps.affair.demo.aspect.annotationTest.AutoService2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author he.zhou
 * @Date 2020/5/25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringTest {

    @Resource
    private AutoService2 autoService2;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private StudentBean studentBean;

    @Resource
    private ArithmeticCalculator arithmeticCalculator;

    @Test
    public void testRedis() {
        System.out.println(stringRedisTemplate.opsForValue().get("_es_o_login_token_2_user_id_5dfb3faa67fdf8133fed7969"));
    }


    @Test
    public void test() {
        autoService2.substract(1, 2);
    }

    @Test
    public void test1() {
        autoService2.add(1, 2);
    }

    @Test
    public void test3(){
        System.out.println(studentBean.getCode());
    }

}
