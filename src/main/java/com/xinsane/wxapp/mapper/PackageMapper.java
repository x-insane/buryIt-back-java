package com.xinsane.wxapp.mapper;

import com.xinsane.wxapp.pojo.Package;

import java.util.List;

public interface PackageMapper {
    Package getPackageById(int id);
    List<Package> getPackagesByUserId(Integer userId);
    int deletePackage(int id);
    int createFreePackage(Package pack);
    int createFixedPackage(Package pack);
    int createPrivatePackage(Package pack);
    int modifyFreePackage(Package pack);
    int modifyFixedPackage(Package pack);
    int modifyPrivatePackage(Package pack);
}
