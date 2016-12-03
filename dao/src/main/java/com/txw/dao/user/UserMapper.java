package com.txw.dao.user;

import com.txw.vo.user.User;
import org.springframework.stereotype.Repository;


public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);
}