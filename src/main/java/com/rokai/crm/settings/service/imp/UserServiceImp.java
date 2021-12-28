package com.rokai.crm.settings.service.imp;

import com.rokai.crm.exception.UserException;
import com.rokai.crm.settings.dao.UserDao;
import com.rokai.crm.settings.domain.User;
import com.rokai.crm.settings.service.UserService;
import com.rokai.crm.utils.DateTimeUtil;
import com.rokai.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImp implements UserService {
    UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String account, String password, String ip) throws UserException {

        Map<String,String> map = new HashMap<>();
        map.put("account",account);
        map.put("password",password);

        User user = userDao.login(map);
        if (user == null){
            throw new UserException("账号&密码错误！");
        }

        String expireTime = user.getExpireTime();
        String sysTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(sysTime) < 0){
            throw new UserException("账号已失效！");
        }

        String lockState = user.getLockState();
        if ("0".equals(lockState)){
            throw new UserException("账号已锁定，请联系管理员。");
        }

        String allowIps = user.getAllowIps();
        if (!allowIps.contains(ip)){
            throw new UserException("IP地址受限！");
        }

        return user;
    }

    @Override
    public List<User> getUserInfo() {
        List<User> userInfo = userDao.getUserInfo();
        return userInfo;
    }
}
