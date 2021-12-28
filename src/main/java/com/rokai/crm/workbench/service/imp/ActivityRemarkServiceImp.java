package com.rokai.crm.workbench.service.imp;

import com.rokai.crm.utils.SqlSessionUtil;
import com.rokai.crm.workbench.dao.ActivityRemarkDao;
import com.rokai.crm.workbench.service.ActivityRemarkService;

public class ActivityRemarkServiceImp implements ActivityRemarkService {
    ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
}
