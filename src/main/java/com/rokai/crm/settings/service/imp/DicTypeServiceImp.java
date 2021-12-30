package com.rokai.crm.settings.service.imp;

import com.rokai.crm.settings.dao.DicTypeDao;
import com.rokai.crm.settings.service.DicTypeService;
import com.rokai.crm.utils.SqlSessionUtil;

public class DicTypeServiceImp implements DicTypeService {

    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);
}
