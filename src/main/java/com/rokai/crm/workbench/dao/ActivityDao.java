package com.rokai.crm.workbench.dao;

import com.rokai.crm.settings.domain.User;
import com.rokai.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {

    List<User> getUserInfo();

    int save(Activity activity);

    int getTotalByCondition(Map<String, Object> map);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int delete(String[] idS);
}
