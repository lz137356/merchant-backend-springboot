package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.OnlineUser;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OnlineUserHandler extends SimpleIbatisEntityHandler<OnlineUser> {

    public List<OnlineUser> findByPage(Map<String, Object> params) {
        return queryForList("findByPage", params);
    }

    public int countTotal(Map<String, Object> params) {
        return queryForObject("countTotal", params);
    }

    public void add(OnlineUser user) {
        insert(user);
    }

    public void update(OnlineUser user) {
        update("updateBySessionId", user);
    }

    public void deleteBySessionId(String sessionId) {
        delete("deleteBySessionId", sessionId);
    }

    public OnlineUser findBySessionId(String sessionId) {
        return queryForObject("findBySessionId", sessionId);
    }

    public void updateLastActiveTime(String sessionId) {
        update("updateLastActiveTime", sessionId);
    }
}
