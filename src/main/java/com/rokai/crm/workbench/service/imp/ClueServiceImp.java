package com.rokai.crm.workbench.service.imp;

import com.rokai.crm.utils.SqlSessionUtil;
import com.rokai.crm.workbench.dao.ClueDao;
import com.rokai.crm.workbench.service.ClueService;

public class ClueServiceImp implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
}
