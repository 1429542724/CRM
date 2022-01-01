package com.rokai.crm.settings.service.imp;

import com.rokai.crm.settings.dao.DicTypeDao;
import com.rokai.crm.settings.dao.DicValueDao;
import com.rokai.crm.settings.domain.DicType;
import com.rokai.crm.settings.domain.DicValue;
import com.rokai.crm.settings.service.DicService;
import com.rokai.crm.utils.SqlSessionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DicServiceImp implements DicService {

    private DicValueDao dicValueDao = SqlSessionUtil.getSqlSession().getMapper(DicValueDao.class);
    private DicTypeDao dicTypeDao = SqlSessionUtil.getSqlSession().getMapper(DicTypeDao.class);

    @Override
    public Map<String, List<DicValue>> getDicData() {
        Map<String,List<DicValue>> map = new HashMap<>();

        List<DicType> dicTypeList = dicTypeDao.getDicType();

        for (DicType dt:dicTypeList){
            String code = dt.getCode();

            List<DicValue> dicValueList = dicValueDao.getDicValue(code);

            map.put(code,dicValueList);
        }

        return map;
    }

}
