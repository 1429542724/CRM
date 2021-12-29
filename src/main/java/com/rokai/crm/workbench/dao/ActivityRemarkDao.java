package com.rokai.crm.workbench.dao;

import com.rokai.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkDao {

    int getCountByIds(String[] idS);

    int deleteByIds(String[] idS);

    List<ActivityRemark> getRemark(String id);

    int deleteRemark(String id);

    int saveRemark(ActivityRemark activityRemark);
}
