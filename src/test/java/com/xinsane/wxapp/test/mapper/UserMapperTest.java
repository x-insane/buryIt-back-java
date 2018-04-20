package com.xinsane.wxapp.test.mapper;

import com.xinsane.wxapp.mapper.UserMapper;
import com.xinsane.wxapp.pojo.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGetUserById() {
        User user = userMapper.getUserById(8);
        System.out.println(user);
    }

    @Test
    public void testGetUserByToken() {
        User user = userMapper.getUserByToken("0ac7bc609ad04314ab1863d159eb3ddf");
        System.out.println(user);
    }

    @Test
    public void testGetUserByOpenid() {
        User user = userMapper.getUserByOpenid("oYkI65EUrPMMghoB8L8sYq-H5IKc");
        System.out.println(user);
    }

    @Test
    public void testInsertByName() {
        User user = new User().setName("xinsane");
        int rows = userMapper.registerByName(user);
        Assert.assertEquals(1, rows);
        System.out.println(user);
    }

    @Test
    public void testCreateUserOpenidMap() {
        int rows = userMapper.createUserOpenidMap(9, "oYkI65EUrPMMghoB8L8sYq-H5IKc");
        Assert.assertEquals(1, rows);
    }

}
