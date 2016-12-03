package com.txw.controller;

import com.txw.service.impl.UserServiceImpl;
import com.txw.util.JedisClusterFactory;
import com.txw.vo.user.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * Created by tangxiewen on 2016/10/11.
 */
@Controller
public class IndexController implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Resource
    private UserServiceImpl userService;
    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/action")
    @ResponseBody
    public String action(){
        return "index";
    }

    @RequestMapping("/insertUser")
    @ResponseBody
    public String action(@ModelAttribute User user){
        userService.insertUser(user);
        return "ok";
    }

    @RequestMapping("/setRedisValue")
    @ResponseBody
    public String setValue()throws Exception{
        JedisCluster  jedisCluster=(JedisCluster) applicationContext.getBean("jedisCluster");
      // JedisCluster JedisCluster = jedisClusterFactory.getObject();
        for(int i=0;i<1000;i++){
            jedisCluster.set("key"+i,i+"");
        }

        return "ok";
    }

    @RequestMapping("/getRedisValue")
    @ResponseBody
    public String getValue(@RequestParam String param)throws Exception{
        JedisCluster  jedisCluster=(JedisCluster) applicationContext.getBean("jedisCluster");
        // JedisCluster JedisCluster = jedisClusterFactory.getObject();

        return  jedisCluster.get(param);


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
