package com.xinsane.buryit.service;

import com.xinsane.buryit.pojo.Source;
import com.xinsane.buryit.service.transfer.Transfer;

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
    Transfer accessPackage(Integer packageId, Integer userId);
    List<Source> getByPackageId(Integer packageId);
}
