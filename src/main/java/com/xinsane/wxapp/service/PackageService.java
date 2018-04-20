package com.xinsane.wxapp.service;

import com.xinsane.wxapp.pojo.Package;
import com.xinsane.wxapp.service.transfer.Transfer;

public interface PackageService {
    Transfer insert(Package pack);
    Transfer modify(Package pack);
    Transfer delete(Package pack);
}
