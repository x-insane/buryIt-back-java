package com.xinsane.wxapp.service;

import com.sun.istack.internal.NotNull;
import com.xinsane.wxapp.pojo.User;
import com.xinsane.wxapp.service.transfer.TokenTransfer;

public interface UserService {
    TokenTransfer loginByWxCode(@NotNull String code);
    User getUserByToken(String token);
}
