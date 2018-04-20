package com.xinsane.wxapp.service.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xinsane.util.HttpApiUtil;
import com.xinsane.wxapp.config.WechatConfig;
import com.xinsane.wxapp.mapper.TokenMapper;
import com.xinsane.wxapp.mapper.UserMapper;
import com.xinsane.wxapp.pojo.Token;
import com.xinsane.wxapp.pojo.User;
import com.xinsane.wxapp.service.UserService;
import com.xinsane.wxapp.service.transfer.TokenTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final WechatConfig wechat;
    private final UserMapper userMapper;
    private final TokenMapper tokenMapper;

    @Autowired
    public UserServiceImpl(WechatConfig wechat, UserMapper userMapper, TokenMapper tokenMapper) {
        this.wechat = wechat;
        this.userMapper = userMapper;
        this.tokenMapper = tokenMapper;
    }

    @Override
    public TokenTransfer loginByWxCode(String code) {
        String data = HttpApiUtil.get("https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + wechat.getAppid() + "&" +
                "secret=" + wechat.getSecret() + "&" +
                "js_code=" + code + "&" +
                "grant_type=authorization_code", "UTF-8");
        JsonObject result = new JsonParser().parse(data).getAsJsonObject();
        if (result.has("errcode"))
            return new TokenTransfer().setError(101).setMsg("login by wxcode fail: " + result.get("errmsg").getAsString());
        String openid = result.get("openid").getAsString();
        String sessionKey = result.get("session_key").getAsString();
        User user = userMapper.getUserByOpenid(openid);
        if (user == null) {
            user = new User().setName(openid);
            if (userMapper.registerByName(user) != 1 ||
                    userMapper.createUserOpenidMap(user.getId(), openid) != 1)
                return new TokenTransfer().setError(102).setMsg("login by wxcode fail: can not create user.");
        }
        String token = UUID.randomUUID().toString().replace("-", "");
        Token tokenObj = new Token().setToken(token).setOpenid(openid).setSessionKey(sessionKey);
        if (tokenMapper.insert(tokenObj) != 1)
            return new TokenTransfer().setError(103).setMsg("login by wxcode fail: can not save token.");
        return new TokenTransfer().setToken(token);
    }

    @Override
    public User getUserByToken(String token) {
        return userMapper.getUserByToken(token);
    }
}
