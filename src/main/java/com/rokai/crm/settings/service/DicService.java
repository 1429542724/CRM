package com.rokai.crm.settings.service;

import com.rokai.crm.settings.domain.DicValue;

import java.util.List;
import java.util.Map;

public interface DicService {

    Map<String, List<DicValue>> getDicData();

}
