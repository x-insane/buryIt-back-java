package com.xinsane.buryit.service;

import com.xinsane.buryit.pojo.Package;
import com.xinsane.buryit.service.transfer.Transfer;

import java.util.List;

public interface PackageService {
    Transfer insert(Package pack);
    Transfer modify(Package pack);
    Transfer delete(Package pack);

    /**
     * 通过密码取得包裹内容的访问权
     * @param pack 传入的信息
     * @return error=403表示需要密码或密码不正确
     *         error=0表示成功获得访问权
     */
    Transfer access(Package pack);

    /**
     * 列出用户创建的所有包裹
     * @param userId 用户id
     * @return 返回用户创建的包裹列表
     */
    List<Package> listOwnPackages(Integer userId);

    /**
     * 列出用户创建的所有包裹
     * @param userId 用户id
     * @return 返回用户挖出的包裹列表
     */
    List<Package> listWatchPackages(Integer userId);

    /**
     * 根据圆心和半径查询范围内所有可见包裹
     * @param pack 包裹信息
     * @param distance 半径，为0时表示精确查询，单位米
     * @return 符合条件的包裹列表
     */
    List<Package> queryWithCircle(Package pack, Integer distance);

    Transfer bury(Package pack);
    Transfer dig(Package pack);
    Transfer rebury(Package pack);
}
