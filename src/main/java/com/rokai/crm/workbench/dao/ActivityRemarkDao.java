package com.rokai.crm.workbench.dao;

public interface ActivityRemarkDao {

    int getCountByIds(String[] idS);

    int deleteByIds(String[] idS);
}
