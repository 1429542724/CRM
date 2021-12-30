package com.rokai.crm.settings.service.imp;

import com.rokai.crm.settings.dao.DicValueDao;
import com.rokai.crm.settings.service.DicValueService;
import com.rokai.crm.utils.SqlSessionUtil;

public class DicValueServiceImp implements DicValueService {

    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);
}
