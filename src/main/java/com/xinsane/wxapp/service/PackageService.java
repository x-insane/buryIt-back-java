package com.xinsane.wxapp.service;

import com.xinsane.wxapp.pojo.Package;
import com.xinsane.wxapp.service.transfer.Transfer;

import java.util.List;

public interface PackageService {
    Transfer insert(Package pack);
    Transfer modify(Package pack);
    Transfer delete(Package pack);

    /**
     * 访问指定包裹
     * @param userId 用户id
     * @param packageId 包裹id
     * @return 返回指定包裹，如果包裹不存在或用户没有权限则返回null
     */
    Package accessPackage(Integer userId, Integer packageId);

    /**
     * 列出用户创建的所有包裹
     * @param userId 用户id
     * @return 返回用户创建的包裹列表
     */
    List<Package> listOwnPackages(Integer userId);
}
