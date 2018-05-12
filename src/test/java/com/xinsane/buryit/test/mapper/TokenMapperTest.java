package com.xinsane.buryit.test.mapper;

import com.xinsane.buryit.mapper.TokenMapper;
import com.xinsane.buryit.pojo.Token;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class TokenMapperTest {

    @Autowired
    private TokenMapper tokenMapper;

    @Test
    public void testInsert() {
        Token token = new Token().setToken("4df59104acd745ec5619116adb20ca98").setOpenid("584").setSessionKey("545685");
        int rows = tokenMapper.insert(token);
        Assert.assertEquals(1, rows);
        System.out.println(token);
    }

}
