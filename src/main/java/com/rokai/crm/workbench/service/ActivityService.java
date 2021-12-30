package com.rokai.crm.workbench.service;

import com.rokai.crm.exception.ActivityException;
import com.rokai.crm.settings.domain.User;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.domain.Activity;
import com.rokai.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    boolean save(Activity activity) throws ActivityException;

    PaginationVO pageList(Map<String, Object> map);

    Boolean delete(String[] idS);

    Map<String, Object> edit(String id);

    Boolean editUpdate(Activity activity) throws ActivityException;

    Activity detail(String id);

    List<ActivityRemark> getRemark(String id);

    boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark activityRemark);

    boolean updateRemark(ActivityRemark activityRemark);
}
