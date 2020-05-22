package com.qf.shop.shop_service_impl.serviceimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.dao.IUserDao;
import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;


@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public ResultData<User> login(String username,String password) {

        String msg;
        int code;

        User user = userDao.login(username);

        //判断用户是否为空
        if(user!=null){
            if(user.getPassword().equals(password)){
               //登录成功
                msg="登录成功！";
                code=0;
            }else{
                //登录失败
                msg="密码错误!";
                code=1;
            }
        }else{
            //登录失败
            msg="账号错误";
            code=2;
        }

        //将查询的数据放入对象并返回
        ResultData<User> resultData=new ResultData<>(code,msg,user);
        return resultData;
    }
}
