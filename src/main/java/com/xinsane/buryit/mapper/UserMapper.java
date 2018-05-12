package com.xinsane.buryit.mapper;

import com.xinsane.buryit.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    User getUserById(int id);
    User getUserByToken(String token);
    User getUserByOpenid(String openid);
    int registerByName(User user);
    int createUserOpenidMap(@Param("id") int id, @Param("openid") String openid);
    int modify(User user);
}
