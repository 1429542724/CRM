package com.rokai.crm.settings.service;

import com.rokai.crm.exception.UserException;
import com.rokai.crm.settings.domain.User;

import java.util.List;

public interface UserService {
    User login(String account, String password, String ip) throws UserException;

    List<User> getUserInfo();
}
