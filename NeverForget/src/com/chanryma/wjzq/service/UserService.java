package com.chanryma.wjzq.service;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.chanryma.wjzq.domain.user.User;
import com.chanryma.wjzq.domain.user.UserRepository;
import com.chanryma.wjzq.exception.ServiceException;
import com.chanryma.wjzq.factory.BeanFactory;
import com.chanryma.wjzq.model.ConfigEntity;
import com.chanryma.wjzq.util.ConfigUtil;
import com.chanryma.wjzq.util.GsonUtil;
import com.chanryma.wjzq.util.LogUtil;
import com.chanryma.wjzq.util.StringUtil;
import com.github.kevinsawicki.http.HttpRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class UserService {
    private UserRepository userRepository = (UserRepository) BeanFactory.getInstance().getBean("userRepository");

    public String login(HttpServletRequest request) throws ServiceException {
        String sessionCode = request.getParameter("sessionCode");
        String nickName = request.getParameter("nickName");
        if (StringUtil.isEmpty(sessionCode) || StringUtil.isEmpty(nickName)) {
            LogUtil.debug("sessionCode=" + sessionCode + ", nickName=" + nickName);
            throw new ServiceException("敢问阁下从哪来？好像不太认识你呀。");
        }

        String uuid = UUID.randomUUID().toString();
        String openId = sessionCode2UserInfo(sessionCode);
        User user = new User(openId, nickName);
        user.setSessionCode(uuid);
        userRepository.saveUser(user);

        return uuid;
    }

    private String sessionCode2UserInfo(String sessionCode) throws ServiceException {
        String appId = ConfigUtil.get(ConfigEntity.KEY_APP_ID);
        String appSecrete = ConfigUtil.get(ConfigEntity.KEY_APP_SECRETE);
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        HttpRequest request = HttpRequest.get(url, true, "appId", appId, "secret", appSecrete, "js_code", sessionCode, "grant_type", "authorization_code");

        String response = request.body();
        LogUtil.debug(response);
        JsonObject json = GsonUtil.json2JsonObject(response);
        JsonElement errorCode = json.get("errcode");
        if (errorCode != null) {
            throw new ServiceException("啊哦，登录失败了，再试一次吧。 [" + errorCode.getAsString() + "]");
        }

        JsonElement openId = json.get("openid");
        if (openId == null) {
            LogUtil.info("WeiXin server responsds ok, but without openid.");
            throw new ServiceException("哎，人生总是有意外……");
        }

        return openId.getAsString();
    }
}
