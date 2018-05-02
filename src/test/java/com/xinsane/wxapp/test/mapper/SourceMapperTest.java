package com.xinsane.wxapp.test.mapper;

import com.xinsane.wxapp.mapper.SourceMapper;
import com.xinsane.wxapp.pojo.Source;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SourceMapperTest {

    @Autowired
    private SourceMapper sourceMapper;

    @Test
    public void testInsertText() {
        Source source = new Source().setUserId(8).setType("text").setPackageId(12).setText("测试");
        int rows = sourceMapper.insert(source);
        Assert.assertEquals(1, rows);
        rows = sourceMapper.insertText(source);
        Assert.assertEquals(1, rows);
        System.out.println(source.getId());
    }

    @Test
    public void testGetSourceById() {
        Source source = sourceMapper.getSourceById(3);
        System.out.println(source);
    }

    @Test
    public void testDelete() {
        int rows = sourceMapper.delete(3);
        System.out.println(rows);
    }

    @Test
    public void testUpdateText() {
        int rows = sourceMapper.updateText(new Source().setId(3).setText("123"));
        System.out.println(rows);
    }

    @Test
    public void testGetSourcesByPackageId() {
        List<Source> sourceList = sourceMapper.getSourcesByPackageId(10);
        for (Source source : sourceList)
            System.out.println(source);
    }

}
