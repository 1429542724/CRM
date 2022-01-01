package com.rokai.crm.settings.dao;

import com.rokai.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueDao {

    List<DicValue> getDicValue(String code);
}
