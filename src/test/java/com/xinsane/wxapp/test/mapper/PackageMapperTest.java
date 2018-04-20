package com.xinsane.wxapp.test.mapper;

import com.xinsane.wxapp.mapper.PackageMapper;
import com.xinsane.wxapp.pojo.Package;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class PackageMapperTest {

    @Autowired
    private PackageMapper packageMapper;

    @Test
    public void testGetPackageById() {
        Package pack = packageMapper.getPackageById(3);
        System.out.println(pack);
    }

    @Test
    public void testCreateFreePackage() {
        Package pack = new Package().setType("free")
                .setTitle("测试")
                .setUserId(9)
                .setRealMode(true)
                .setCycleTime(36000)
                .setSender("潜行在黑'暗中的人");
        packageMapper.createFreePackage(pack);
        System.out.println(pack);
    }

    @Test
    public void testModifyFreePackage() {
        Package pack = new Package().setId(3)
                .setTitle("测试")
                .setUserId(19) // 测试是否无效
                .setRealMode(false)
                .setCycleTime(3601)
                .setReceiver("潜行在黑暗中的人'");
        int rows = packageMapper.modifyFreePackage(pack);
        Assert.assertEquals(1, rows);
    }

    @Test
    public void testCreateFixedPackage() {
        Package pack = new Package().setType("fixed")
                .setTitle("测试")
                .setUserId(9)
                .setRealMode(true)
                .setPackageTotalNum(10)
                .setDescription("这里是一\"些说明性的文字");
        packageMapper.createFixedPackage(pack);
        System.out.println(pack);
    }

    @Test
    public void testCreatePrivatePackage() {
        Package pack = new Package().setType("private")
                .setTitle("测试")
                .setUserId(9)
                .setRealMode(true)
                .setReceiver("接收者")
                .setPackageTotalNum(15);
        packageMapper.createPrivatePackage(pack);
        System.out.println(pack);
    }

    @Test
    public void testDeletePackage() {
        int rows = packageMapper.deletePackage(12);
        System.out.println(rows);
    }

}
