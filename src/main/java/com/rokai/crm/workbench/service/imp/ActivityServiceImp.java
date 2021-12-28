package com.rokai.crm.workbench.service.imp;

import com.rokai.crm.exception.ActivityException;
import com.rokai.crm.settings.domain.User;
import com.rokai.crm.utils.SqlSessionUtil;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.dao.ActivityDao;
import com.rokai.crm.workbench.dao.ActivityRemarkDao;
import com.rokai.crm.workbench.domain.Activity;
import com.rokai.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImp implements ActivityService {

    ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);

    @Override
    public List<User> getUserInfo() {
        List<User> userInfo = activityDao.getUserInfo();
        return userInfo;
    }

    @Override
    public boolean save(Activity activity) throws ActivityException {
        boolean state = true;

        if ("".equals(activity.getName())){
            state = false;
            throw new ActivityException("创建市场活动失败！");
        }

        int count = activityDao.save(activity);
        if (count != 1){
            state = false;
            throw new ActivityException("创建市场活动失败！");
        }
        return state;
    }

    @Override
    public PaginationVO pageList(Map<String, Object> map) {
        int total = activityDao.getTotalByCondition(map);

        List<Activity> dataList = activityDao.getActivityListByCondition(map);

        PaginationVO<Activity> vo = new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        return vo;
    }

    @Override
    public Boolean delete(String[] idS) {

        boolean flag = true;

        //查询出需要删除的备注的数量，
        int amount1 = activityRemarkDao.getCountByIds(idS);

        //删除备注，返回受到影响的条数（实际删除的数量）
        int amount2 = activityRemarkDao.deleteByIds(idS);

        if (amount1!=amount2){
            flag = false;
        }

        //删除市场活动
        int amount3 = activityDao.delete(idS);

        if (amount3 != idS.length){
            flag = false;
        }

        return flag;
    }
}
