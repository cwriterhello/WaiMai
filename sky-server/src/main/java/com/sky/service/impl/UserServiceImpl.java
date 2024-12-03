package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    //微信接口地址
    public static final String userLogin = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    WeChatProperties weChatProperties;
    @Autowired
    UserMapper userMapper;

    @Override
    public User userlogin(UserLoginDTO userLoginDTO) {
        //获取openid
        String openid = getOpenid(userLoginDTO.getCode());
        //判断openid是否为空，如果为空抛出异常
        if (openid == null) {
            throw new LoginFailedException("登陆失败");
        }
        //判断当前用户是否是新用户
        User user = userMapper.getByOpenid(openid);

        //如果是新用户完成自动注册
        if (user == null) {
            user = User.builder()
                    .createTime(LocalDateTime.now())
                    .openid(openid)
                    .build();
            userMapper.add(user);
        }
        //返回这个用户对象
        return user;
    }


    //调用微信接口获取当前用户openid
    private String getOpenid(String code){
        Map<String, String> licence = new HashMap<>();
        licence.put("appid", weChatProperties.getAppid());
        licence.put("secret", weChatProperties.getSecret());
        licence.put("js_code", code);
        licence.put("grant_type", "authorization_code");
        //返回json格式的字符串长这样，
        // {"session_key":"************",
        // "openid":"**********"}
        String json = HttpClientUtil.doGet(userLogin, licence);
        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
