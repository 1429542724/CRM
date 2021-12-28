package com.rokai.crm.workbench.dao;

import com.rokai.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityDao {

    int save(Activity activity);

    int getTotalByCondition(Map<String, Object> map);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int delete(String[] idS);

    Activity getById(String id);

    int editUpdate(Activity activity);
}
