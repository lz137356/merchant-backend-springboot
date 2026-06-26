package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.LoginLog;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class LoginLogHandler extends SimpleIbatisEntityHandler<LoginLog> {


    public List<LoginLog> findByPage(Map map){
        return queryForList("findByPage",map);
    }
    public int countTotal(Map map){
        return queryForObject("countTotal",map);
    }

    public void add(LoginLog log){
        insert(log);
    }
}
