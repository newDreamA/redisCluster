package com.txw;

import com.txw.service.IUserService;
import com.txw.vo.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by tangxiewen on 2016/10/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)     //表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations = {"classpath:config/mybatis/spring-mybatis.xml"})
public class TestMybatis {
    @Resource
    private IUserService userService = null;

//  @Before
//  public void before() {
//      ac = new ClassPathXmlApplicationContext("applicationContext.xml");
//      userService = (IUserService) ac.getBean("userService");
//  }

    @Test
    public void test1() {
        User user = new User();
        user.settId(1);
        user.settSex("m");
        user.settName("txw");

        userService.insertUser(user);
        // System.out.println(user.getUserName());
        // logger.info("值："+user.getUserName());

    }
}
