package com.lxtx.pay.handler;

import com.lxtx.pay.pojo.PayType;
import com.lxtx.pay.pojo.User;
import com.qlzf.commons.handler.SimpleIbatisEntityHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserHandler extends SimpleIbatisEntityHandler<User> {

public  User login(User user){
    return queryForObject("login",user);
}

public  List<User> queryAllByRole(int i){
    return queryForList("queryAllByRole",i);
}
    public int deleteuser(int userId){
        int delete = super.delete(userId);
        return delete;
    }

}
