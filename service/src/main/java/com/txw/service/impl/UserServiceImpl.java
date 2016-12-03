package com.txw.service.impl;

import com.txw.dao.user.UserMapper;
import com.txw.service.IUserService;
import com.txw.vo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by tangxiewen on 2016/10/12.
 */
@Service("userService")
public class UserServiceImpl implements IUserService{
    @Resource
    private UserMapper userMapper;

    public void insertUser(User user) {
        this.userMapper.insert(user);
    }
}
