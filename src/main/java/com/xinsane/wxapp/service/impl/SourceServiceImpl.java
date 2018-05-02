package com.xinsane.wxapp.service.impl;

import com.xinsane.wxapp.mapper.PackageMapper;
import com.xinsane.wxapp.mapper.SourceMapper;
import com.xinsane.wxapp.pojo.Package;
import com.xinsane.wxapp.pojo.Source;
import com.xinsane.wxapp.service.SourceService;
import com.xinsane.wxapp.service.transfer.BaseTransfer;
import com.xinsane.wxapp.service.transfer.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SourceServiceImpl implements SourceService {

    private final SourceMapper sourceMapper;
    private final PackageMapper packageMapper;

    @Autowired
    public SourceServiceImpl(SourceMapper sourceMapper, PackageMapper packageMapper) {
        this.sourceMapper = sourceMapper;
        this.packageMapper = packageMapper;
    }

    @Override
    public Source getById(Integer id) {
        return id == null ? null : sourceMapper.getSourceById(id);
    }

    @Override
    public List<Source> getByPackageId(Integer packageId) {
        return packageId == null ? new ArrayList<>() : sourceMapper.getSourcesByPackageId(packageId);
    }

    /**
     * 检测用户有没有新增权限，即要操作的包裹是否属于该用户
     * @param userId 正在操作的用户id
     * @param packageId 用户正在操作的包裹id
     * @return 如果检测通过则返回null，否则返回Transfer
     */
    private Transfer checkInsert(Integer userId, Integer packageId) {
        if (userId == null || packageId == null)
            return new BaseTransfer().setError(400).setMsg("参数不全");
        Package pack = packageMapper.getPackageById(packageId);
        if (pack == null)
            return new BaseTransfer().setError(404).setMsg("指定包裹不存在");
        if (!pack.getUserId().equals(userId))
            return new BaseTransfer().setError(403).setMsg("只能操作自己的包裹");
        return null;
    }

    @Override
    public Transfer addText(Source source) {
        Transfer transfer = checkInsert(source.getUserId(), source.getPackageId());
        if (transfer != null)
            return transfer;
        sourceMapper.insert(source);
        sourceMapper.insertText(source);
        return new BaseTransfer();
    }

    @Override
    public Transfer addImage(Source source) {
        Transfer transfer = checkInsert(source.getUserId(), source.getPackageId());
        if (transfer != null)
            return transfer;
        sourceMapper.insert(source);
        sourceMapper.insertImage(source);
        return new BaseTransfer();
    }

    @Override
    public Transfer addVoice(Source source) {
        Transfer transfer = checkInsert(source.getUserId(), source.getPackageId());
        if (transfer != null)
            return transfer;
        sourceMapper.insert(source);
        sourceMapper.insertVoice(source);
        return new BaseTransfer();
    }

    /**
     * 检测用户有没有新增权限，即要操作的素材是否属于该用户
     * @param userId 正在操作的用户id
     * @param sourceId 用户正在操作的素材id
     * @return 如果检测通过则返回null，否则返回Transfer
     */
    private Transfer checkAccess(Integer userId, Integer sourceId, String type) {
        if (userId == null || sourceId == null)
            return new BaseTransfer().setError(400).setMsg("参数不全");
        Source source = sourceMapper.getSourceById(sourceId);
        if (source == null)
            return new BaseTransfer().setError(404).setMsg("指定素材不存在");
        if (!source.getUserId().equals(userId))
            return new BaseTransfer().setError(403).setMsg("只能操作自己的素材");
        if (type != null && !type.equals(source.getType()))
            return new BaseTransfer().setError(403).setMsg("类型不符");
        return null;
    }

    @Override
    public Transfer updateText(Source source) {
        Transfer transfer = checkAccess(source.getUserId(), source.getId(), "text");
        if (transfer != null)
            return transfer;
        sourceMapper.updateText(source);
        return new BaseTransfer();
    }

    @Override
    public Transfer updateImage(Source source) {
        Transfer transfer = checkAccess(source.getUserId(), source.getId(), "image");
        if (transfer != null)
            return transfer;
        sourceMapper.updateImage(source);
        return new BaseTransfer();
    }

    @Override
    public Transfer updateVoice(Source source) {
        Transfer transfer = checkAccess(source.getUserId(), source.getId(), "voice");
        if (transfer != null)
            return transfer;
        sourceMapper.updateVoice(source);
        return new BaseTransfer();
    }

    @Override
    public Transfer deleteSource(Source source) {
        Transfer transfer = checkAccess(source.getUserId(), source.getId(), null);
        if (transfer != null)
            return transfer;
        sourceMapper.delete(source.getId());
        return new BaseTransfer();
    }

}
