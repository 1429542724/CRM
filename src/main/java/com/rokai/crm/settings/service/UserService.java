package com.rokai.crm.settings.service;

import com.rokai.crm.exception.UserException;
import com.rokai.crm.settings.domain.User;

public interface UserService {
    User login(String account, String password, String ip) throws UserException;
}
