package com.rokai.crm.workbench.service.imp;

import com.rokai.crm.exception.ActivityException;
import com.rokai.crm.settings.dao.UserDao;
import com.rokai.crm.settings.domain.User;
import com.rokai.crm.utils.SqlSessionUtil;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.dao.ActivityDao;
import com.rokai.crm.workbench.dao.ActivityRemarkDao;
import com.rokai.crm.workbench.domain.Activity;
import com.rokai.crm.workbench.domain.ActivityRemark;
import com.rokai.crm.workbench.service.ActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServiceImp implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);
    private ActivityRemarkDao activityRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ActivityRemarkDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

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

    @Override
    public Map<String, Object> edit(String id) {
        List<User> uList = userDao.getUserInfo();

        Activity a = activityDao.getById(id);

        Map<String,Object> map = new HashMap<>();
        map.put("uList",uList);
        map.put("a",a);
        return map;
    }

    @Override
    public Boolean editUpdate(Activity activity) throws ActivityException {
        boolean flag = true;

        if ("".equals(activity.getName())){
            flag = false;
            throw new ActivityException("修改市场活动失败！");
        }
        int state = activityDao.editUpdate(activity);
        if (state != 1){
            flag = false;
            throw new ActivityException("修改市场活动失败！");
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity activity = activityDao.detail(id);
        return activity;
    }

    @Override
    public List<ActivityRemark> getRemark(String id) {
        List<ActivityRemark> aList = activityRemarkDao.getRemark(id);
        return aList;
    }

    @Override
    public boolean deleteRemark(String id) {
        boolean flag = true;

        int amount = activityRemarkDao.deleteRemark(id);
        if (amount != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark activityRemark) {
        boolean flag = true;

        if ("".equals(activityRemark.getNoteContent())){
            flag = false;
            return flag;
        }

        int amount = activityRemarkDao.saveRemark(activityRemark);
        if (amount != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean updateRemark(ActivityRemark activityRemark) {
        boolean flag = true;

        if ("".equals(activityRemark.getNoteContent())){
            flag = false;
            return flag;
        }

        int state = activityRemarkDao.updateRemark(activityRemark);
        if (state != 1){
            flag = false;
        }
        return flag;
    }

    @Override
    public Map<String, Object> getActivity(Map<String,Object> map) {

        List<Activity> list = activityDao.getActivity(map);
        int total = activityDao.getActivityCount(map);

        Map<String, Object> rMap = new HashMap<>();
        if (list == null){
            rMap.put("success",false);
            rMap.put("total",0);

            return rMap;
        }else {
            rMap.put("success",true);
            rMap.put("activityList",list);
            rMap.put("total",total);
        }

        return rMap;
    }
}
