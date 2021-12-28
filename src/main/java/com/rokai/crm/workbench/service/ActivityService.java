package com.rokai.crm.workbench.service;

import com.rokai.crm.exception.ActivityException;
import com.rokai.crm.settings.domain.User;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    List<User> getUserInfo();

    boolean save(Activity activity) throws ActivityException;

    PaginationVO pageList(Map<String, Object> map);

    Boolean delete(String[] idS);
}
