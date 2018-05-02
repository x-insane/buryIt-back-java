package com.xinsane.wxapp.service.impl;

import com.xinsane.wxapp.mapper.PackageMapper;
import com.xinsane.wxapp.pojo.Package;
import com.xinsane.wxapp.service.PackageService;
import com.xinsane.wxapp.service.transfer.BaseTransfer;
import com.xinsane.wxapp.service.transfer.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {

    private final PackageMapper packageMapper;

    @Autowired
    public PackageServiceImpl(PackageMapper packageMapper) {
        this.packageMapper = packageMapper;
    }

    @Override
    public Package accessPackage(Integer userId, Integer packageId) {
        if (userId == null || packageId == null)
            return null;
        Package pack = packageMapper.getPackageById(packageId);
        if (pack == null)
            return null;
        if (pack.getUserId().equals(userId)) {
            // TODO: 检查包裹属性，所有者当前是否能访问其内容等
            return pack;
        }
        // TODO: 检查该用户是否为该包裹的关注者，关注着可以查看部分内容
        return null;
    }

    @Override
    public List<Package> listOwnPackages(Integer userId) {
        if (userId == null)
            return new ArrayList<>();
        List<Package> packages = packageMapper.getPackagesByUserId(userId);
        for (Package pack : packages) {
            pack.setLatitude(null).setLongitude(null);
            // TODO: 处理包裹信息隐藏等
        }
        return packages;
    }

    @Override
    public Transfer insert(Package pack) {
        int rows;
        switch (pack.getType()) {
            case "free":
                if (pack.getCycleTime() == null)
                    return new BaseTransfer().setError(102).setMsg("参数错误，请检查循环周期");
                rows = packageMapper.createFreePackage(pack);
                break;
            case "fixed":
                if (pack.getPackageTotalNum() == null)
                    return new BaseTransfer().setError(102).setMsg("参数错误，请检查包裹数量");
                rows = packageMapper.createFixedPackage(pack);
                break;
            case "private":
                rows = packageMapper.createPrivatePackage(pack);
                break;
            default:
                return new BaseTransfer().setError(101).setMsg("参数错误，请检查包裹类型");
        }
        if (rows != 1)
            return new BaseTransfer().setError(500).setMsg("can not create package.");
        return new BaseTransfer();
    }

    @Override
    public Transfer modify(Package pack) {
        Package src = packageMapper.getPackageById(pack.getId());
        if (src == null)
            return new BaseTransfer().setError(404).setMsg("该包裹不存在");
        if (!src.getUserId().equals(pack.getUserId()) || src.getPublishTime() != null)
            return new BaseTransfer().setError(403).setMsg("你不能修改此包裹");
        int rows = 0;
        switch (src.getType()) {
            case "free":
                rows = packageMapper.modifyFreePackage(pack);
                break;
            case "fixed":
                rows = packageMapper.modifyFixedPackage(pack);
                break;
            case "private":
                rows = packageMapper.modifyPrivatePackage(pack);
                break;
        }
        if (rows != 1)
            return new BaseTransfer().setError(400).setMsg("修改失败");
        return new BaseTransfer();
    }

    @Override
    public Transfer delete(Package pack) {
        Package src = packageMapper.getPackageById(pack.getId());
        if (src == null)
            return new BaseTransfer().setError(404).setMsg("该包裹不存在");
        if (!src.getUserId().equals(pack.getUserId()))
            return new BaseTransfer().setError(403).setMsg("只能删除自己的包裹哦");
        int rows = packageMapper.deletePackage(pack.getId());
        if (rows != 1)
            return new BaseTransfer().setError(400).setMsg("删除失败");
        // TODO：这里添加后续处理事件（通知所有关注者等）
        return new BaseTransfer();
    }

}
