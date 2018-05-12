package com.xinsane.buryit.mapper;

import com.xinsane.buryit.pojo.Package;
import com.xinsane.buryit.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PackageMapper {
    Package getPackageById(int id);
    List<Package> listOwnPackages(Integer userId);
    List<Package> listWatchPackages(Integer userId);


    int insert(Package pack);
    int modify(Package pack);
    int delete(int id);

    int clearPosition(Package pack);
    int changeOwner(Package pack);
    int increaseFixedOpenNum(Integer packageId);

    List<User> listWatchers(Integer packageId);
    int addWatcher(@Param("packageId") Integer packageId,
                   @Param("userId") Integer userId);
    boolean isWatching(@Param("packageId") Integer packageId,
                       @Param("userId") Integer userId);
    int accessContent(@Param("packageId") Integer packageId,
                      @Param("userId") Integer userId);
    boolean canAccessContent(@Param("packageId") Integer packageId,
                             @Param("userId") Integer userId);

    int buryFreePackage(Package pack);
    int buryFixedPackage(Package pack);
    int buryPrivatePackage(Package pack);

    int reburyFreePackage(Package pack);

    /**
     * 根据圆心和半径查询范围内所有可见包裹
     * @param pack 包裹信息
     * @param distance 半径，为0时表示精确查询，单位米
     * @return 符合条件的包裹列表
     */
    List<Package> queryWithCircle(@Param("pack") Package pack,
                                  @Param("distance") Integer distance);

    int scheduledFreePackageReset(Package pack);
    List<Package> getOverdueFreePackages();
    int resetOverdueFreePackages();
}
