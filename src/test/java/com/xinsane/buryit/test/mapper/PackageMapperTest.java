package com.xinsane.buryit.test.mapper;

import com.xinsane.buryit.mapper.PackageMapper;
import com.xinsane.buryit.pojo.Package;
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
    public void testDeletePackage() {
        int rows = packageMapper.delete(12);
        System.out.println(rows);
    }

    @Test
    public void testIsWatching() {
        System.out.println(packageMapper.isWatching(3, 8));
    }

}
