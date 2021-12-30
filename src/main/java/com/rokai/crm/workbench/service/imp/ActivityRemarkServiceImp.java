package com.rokai.crm.workbench.service.imp;

import com.rokai.crm.utils.SqlSessionUtil;
import com.rokai.crm.workbench.dao.ActivityRemarkDao;
import com.rokai.crm.workbench.domain.ActivityRemark;
import com.rokai.crm.workbench.service.ActivityRemarkService;

import java.util.List;

public class ActivityRemarkServiceImp implements ActivityRemarkService {

    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
}
