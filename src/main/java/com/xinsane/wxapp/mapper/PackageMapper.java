package com.xinsane.wxapp.mapper;

import com.xinsane.wxapp.pojo.Package;

public interface PackageMapper {
    Package getPackageById(int id);
    int deletePackage(int id);
    int createFreePackage(Package pack);
    int createFixedPackage(Package pack);
    int createPrivatePackage(Package pack);
    int modifyFreePackage(Package pack);
    int modifyFixedPackage(Package pack);
    int modifyPrivatePackage(Package pack);
}
