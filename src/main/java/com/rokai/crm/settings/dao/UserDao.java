package com.rokai.crm.settings.dao;

import com.rokai.crm.settings.domain.User;

import java.util.Map;

public interface UserDao {

    User login(Map<String, String> map);
}
