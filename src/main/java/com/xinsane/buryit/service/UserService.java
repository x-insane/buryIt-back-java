package com.xinsane.buryit.service;

import com.sun.istack.internal.NotNull;
import com.xinsane.buryit.pojo.User;
import com.xinsane.buryit.service.transfer.TokenTransfer;
import com.xinsane.buryit.service.transfer.Transfer;

public interface UserService {
    User getUserByToken(@NotNull String token);
    TokenTransfer loginByWxCode(@NotNull String code);

    /**
     * 通过旧密码修改新密码
     * @param userId 要修改的用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 通用Service层返回信息接口
     */
    Transfer modifyPasswordByOldPassword(int userId, @NotNull String oldPassword, @NotNull String newPassword);

    /**
     * 修改除用户名和密码外的用户信息
     * @param user 要修改的信息，须包含要修改的用户id，不能修改name和password
     * @return 通用Service层返回信息接口
     */
    Transfer modifyUserInfo(@NotNull User user);
}
