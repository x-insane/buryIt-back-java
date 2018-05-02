package com.xinsane.wxapp.service;

import com.xinsane.wxapp.pojo.Source;
import com.xinsane.wxapp.service.transfer.Transfer;

import java.util.List;

public interface SourceService {
    Transfer addText(Source source);
    Transfer addImage(Source source);
    Transfer addVoice(Source source);
    Transfer updateText(Source source);
    Transfer updateImage(Source source);
    Transfer updateVoice(Source source);
    Transfer deleteSource(Source source);
    Source getById(Integer id);
    List<Source> getByPackageId(Integer packageId);
}
