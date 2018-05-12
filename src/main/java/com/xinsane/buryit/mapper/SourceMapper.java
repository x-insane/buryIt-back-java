package com.xinsane.buryit.mapper;

import com.xinsane.buryit.pojo.Source;

import java.util.List;

public interface SourceMapper {
    int insert(Source source);
    int insertText(Source source);
    int insertImage(Source source);
    int insertVoice(Source source);
    int updateText(Source source);
    int updateImage(Source source);
    int updateVoice(Source source);
    int delete(Integer id);

    /**
     * 根据素材id获取单条素材
     * @param id 素材id
     * @return 存在则返回素材，不存在返回null
     */
    Source getSourceById(Integer id);

    /**
     * 根据包裹id获取包裹内所有素材
     * @param packageId 包裹id
     * @return 返回包裹内的所有素材
     */
    List<Source> getSourcesByPackageId(Integer packageId);
}
