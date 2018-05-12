package com.xinsane.buryit.service.impl;

import com.xinsane.buryit.mapper.PackageMapper;
import com.xinsane.buryit.pojo.Package;
import com.xinsane.buryit.service.EventService;
import com.xinsane.buryit.service.NotificationService;
import com.xinsane.buryit.service.PackageService;
import com.xinsane.buryit.service.transfer.BaseTransfer;
import com.xinsane.buryit.service.transfer.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PackageServiceImpl implements PackageService {

    private final PackageMapper packageMapper;
    private final EventService eventService;
    private final NotificationService notificationService;

    @Autowired
    public PackageServiceImpl(PackageMapper packageMapper, EventService eventService, NotificationService notificationService) {
        this.packageMapper = packageMapper;
        this.eventService = eventService;
        this.notificationService = notificationService;
    }

    @Override
    public List<Package> listOwnPackages(Integer userId) {
        if (userId == null)
            return new ArrayList<>();
        return packageMapper.listOwnPackages(userId);
    }

    @Override
    public List<Package> listWatchPackages(Integer userId) {
        if (userId == null)
            return new ArrayList<>();
        return packageMapper.listWatchPackages(userId);
    }

    @Override
    public Transfer insert(Package pack) {
        int rows = packageMapper.insert(pack);
        if (rows != 1)
            return new BaseTransfer().setError(500).setMsg("创建失败");
        return new BaseTransfer();
    }

    @Override
    public Transfer modify(Package pack) {
        Package src = packageMapper.getPackageById(pack.getId());
        if (src == null)
            return new BaseTransfer().setError(404).setMsg("该包裹不存在");
        if (!src.getUserId().equals(pack.getUserId()) || src.getType() != null)
            return new BaseTransfer().setError(403).setMsg("你不能修改此包裹");
        int rows = packageMapper.modify(pack);
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
        int rows = packageMapper.delete(pack.getId());
        if (rows != 1)
            return new BaseTransfer().setError(400).setMsg("删除失败");
        if (!eventService.addDeleteEvent(src))
            return new BaseTransfer().setError(400).setMsg("写入事件失败");
        return new BaseTransfer();
    }

    @Override
    public Transfer access(Package pack) {
        Package src = packageMapper.getPackageById(pack.getId());
        if (src == null)
            return new BaseTransfer().setError(404).setMsg("该包裹不存在");
        if (src.getPassword() != null && !src.getPassword().equals(pack.getPassword()))
            return new BaseTransfer().setError(403).setMsg("密码错误");
        if (1 != packageMapper.accessContent(pack.getId(), pack.getUserId()))
            return new BaseTransfer().setError(400).setMsg("无法获取访问权");
        return new BaseTransfer();
    }

    @Override
    public Transfer bury(Package pack) {
        Package src = packageMapper.getPackageById(pack.getId());
        if (src == null)
            return new BaseTransfer().setError(404).setMsg("该包裹不存在");
        if (src.getType() != null)
            return new BaseTransfer().setError(403).setMsg("不能重复埋下自己的包裹哦");
        if (!src.getUserId().equals(pack.getUserId()))
            return new BaseTransfer().setError(403).setMsg("只能埋自己的包裹哦");
        if (pack.getDeep() == null)
            pack.setDeep(0);
        int rows;
        switch (pack.getType()) {
            case "free":
                rows = packageMapper.buryFreePackage(pack);
                if (rows != 1 || !eventService.addBuryEvent(pack))
                    return new BaseTransfer().setError(400).setMsg("埋下或写入事件失败");
                break;
            case "fixed":
                rows = packageMapper.buryFixedPackage(pack);
                break;
            case "private":
                rows = packageMapper.buryPrivatePackage(pack);
                break;
            default:
                return new BaseTransfer().setError(401).setMsg("无法识别的包裹类型");
        }
        if (rows != 1)
            return new BaseTransfer().setError(400).setMsg("埋下失败");
        return new BaseTransfer();
    }

    @Override
    public List<Package> queryWithCircle(Package pack, Integer distance) {
        if (pack.getLatitude() == null || pack.getLongitude() == null)
            return new ArrayList<>();
        if (distance == null)
            distance = 100;
        if (pack.getDeep() == null)
            pack.setDeep(0);
        return packageMapper.queryWithCircle(pack, distance);
    }

    @Scheduled(fixedDelay = 60000)
    private void scheduledFreePackageReset() {
        List<Package> packages = packageMapper.getOverdueFreePackages();
        int rows = packageMapper.resetOverdueFreePackages();
        if (rows != 0)
            System.out.println("reset " + rows + " overdue packages.");
        for (Package pack : packages) {
            if (!eventService.addOverdueEvent(pack))
                System.err.println("fail to reset overdue package{id=" + pack.getId() + "}");
        }
    }

    @Override
    public Transfer dig(Package pack) {
        Package src = packageMapper.getPackageById(pack.getId());
        if (src == null)
            return new BaseTransfer().setError(404).setMsg("不存在该包裹");
        if (!src.getUuid().equals(pack.getUuid()))
            return new BaseTransfer().setError(403).setMsg("包裹识别码错误");
        if (src.getPublishTime() == null || src.getFreeDeadline() != null)
            return new BaseTransfer().setError(404).setMsg("该包裹未被埋下");
        if (packageMapper.isWatching(pack.getId(), pack.getUserId()))
            return new BaseTransfer().setError(403).setMsg("你已经挖过该包裹了");
        switch (src.getType()) {
            case "free":
                if (!src.getUserId().equals(pack.getUserId())) { // 非创建者挖出
                    if (1 != packageMapper.addWatcher(pack.getId(), pack.getUserId()))
                        return new BaseTransfer().setError(400).setMsg("无法追踪此包裹");
                    if (src.getPassword() == null) {
                        if (1 != packageMapper.accessContent(pack.getId(), pack.getUserId()))
                            return new BaseTransfer().setError(400).setMsg("无法获取包裹访问权");
                    }
                    // 添加计时任务，规定时间后未埋下会自动回收
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(new Date());
                    calendar.add(Calendar.MINUTE, src.getCycleTime());
                    pack.setFreeDeadline(calendar.getTime());
                    if (1 != packageMapper.scheduledFreePackageReset(pack))
                        return new BaseTransfer().setError(400).setMsg("无法添加定时任务");
                } else { // 创建者挖出
                    if (1 != packageMapper.clearPosition(src))
                        return new BaseTransfer().setError(400).setMsg("无法清除位置信息");
                }
                if (1 != packageMapper.changeOwner(pack))
                    return new BaseTransfer().setError(400).setMsg("无法取得包裹的所有权");
                if (!eventService.addDigEvent(src, pack.getUserId()))
                    return new BaseTransfer().setError(400).setMsg("无法添加事件");
                break;
            case "fixed":
                if (!src.getUserId().equals(pack.getUserId())) {
                    if (1 != packageMapper.addWatcher(pack.getId(), pack.getUserId()))
                        return new BaseTransfer().setError(400).setMsg("无法追踪此包裹");
                    if (src.getPassword() == null) {
                        if (1 != packageMapper.accessContent(pack.getId(), pack.getUserId()))
                            return new BaseTransfer().setError(400).setMsg("无法获取包裹访问权");
                    }
                    if (1 != packageMapper.increaseFixedOpenNum(pack.getId()))
                        return new BaseTransfer().setError(400).setMsg("无法修改挖出次数");
                    if (src.getPackageOpenNum() >= src.getPackageTotalNum()) {
                        if (1 != packageMapper.clearPosition(src))
                            return new BaseTransfer().setError(400).setMsg("无法清除位置信息");
                        return new BaseTransfer().setError(404).setMsg("不存在该包裹");
                    }
                } else {
                    if (1 != packageMapper.clearPosition(src))
                        return new BaseTransfer().setError(400).setMsg("无法清除位置信息");
                }
                break;
            case "private":
                if (!src.getUserId().equals(pack.getUserId()))
                    return new BaseTransfer().setError(404).setMsg("不存在该包裹");
                if (1 != packageMapper.clearPosition(src))
                    return new BaseTransfer().setError(400).setMsg("无法清除位置信息");
                break;
            default:
                return new BaseTransfer().setError(404).setMsg("不存在该包裹");
        }
        return new BaseTransfer();
    }

    @Override
    public Transfer rebury(Package pack) {
        int rows = packageMapper.reburyFreePackage(pack);
        if (rows != 1)
            return new BaseTransfer().setError(403).setMsg("重新埋下失败");
        if (!eventService.addReburyEvent(pack, pack.getUserId()))
            return new BaseTransfer().setError(400).setMsg("写入事件失败");
        return new BaseTransfer();
    }
}
