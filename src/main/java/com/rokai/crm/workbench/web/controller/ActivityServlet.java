package com.rokai.crm.workbench.web.controller;

import com.rokai.crm.settings.domain.User;
import com.rokai.crm.settings.service.UserService;
import com.rokai.crm.settings.service.imp.UserServiceImp;
import com.rokai.crm.utils.DateTimeUtil;
import com.rokai.crm.utils.PrintJson;
import com.rokai.crm.utils.ServiceFactory;
import com.rokai.crm.utils.UUIDUtil;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.domain.Activity;
import com.rokai.crm.workbench.domain.ActivityRemark;
import com.rokai.crm.workbench.service.ActivityService;
import com.rokai.crm.workbench.service.imp.ActivityServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到市场活动控制器");
        String path = request.getServletPath();
        if ("/workbench/activity/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if ("/workbench/activity/save.do".equals(path)){
            save(request,response);
        }else if ("/workbench/activity/pageList.do".equals(path)){
            pageList(request,response);
        }else if ("/workbench/activity/delete.do".equals(path)){
            delete(request,response);
        }else if ("/workbench/activity/edit.do".equals(path)){
            edit(request,response);
        }else if ("/workbench/activity/editUpdate.do".equals(path)){
            editUpdate(request,response);
        }else if ("/workbench/activity/detail.do".equals(path)){
            detail(request,response);
        }else if ("/workbench/activity/getRemark.do".equals(path)){
            getRemark(request,response);
        }else if ("/workbench/activity/deleteRemark.do".equals(path)){
            deleteRemark(request,response);
        }else if ("/workbench/activity/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }else if ("/workbench/activity/updateRemark.do".equals(path)){
            updateRemark(request,response);
        }

    }

    /**
     * 市场活动备注信息更新操作，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void updateRemark(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("获取修改市场活动备注操作");

        String remarkId = request.getParameter("remarkId");
        String noteContent = request.getParameter("noteContent");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editFlag = "1";

        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(remarkId);
        activityRemark.setNoteContent(noteContent);
        activityRemark.setEditTime(editTime);
        activityRemark.setEditBy(editBy);
        activityRemark.setEditFlag(editFlag);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        boolean flag = activityService.updateRemark(activityRemark);

        Map<String,Object> map = new HashMap<>();
        map.put("ac",activityRemark);
        map.put("success",flag);
        PrintJson.printJsonObj(response,map);
    }


    /**
     * 市场活动备注信息创建操作，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("获取创建市场活动备注操作");

        String remark = request.getParameter("remark");
        String activityId = request.getParameter("id");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        ActivityRemark activityRemark = new ActivityRemark();
        activityRemark.setId(id);
        activityRemark.setNoteContent(remark);
        activityRemark.setCreateTime(createTime);
        activityRemark.setCreateBy(createBy);
        activityRemark.setActivityId(activityId);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        boolean flag = activityService.saveRemark(activityRemark);

        Map<String,Object> map = new HashMap<>();
        map.put("success",flag);
        map.put("info",activityRemark);

        PrintJson.printJsonObj(response,map);
    }

    /**
     * 市场活动备注信息删除操作，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("获取市场活动备注删除操作");

        String id = request.getParameter("id");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        boolean flag = activityService.deleteRemark(id);

        PrintJson.printJsonFlag(response,flag);
    }

    /**
     * 市场活动备注信息获取操作，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void getRemark(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("获取市场活动备注操作");
        String id = request.getParameter("id");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        List<ActivityRemark> aList = activityService.getRemark(id);

        PrintJson.printJsonObj(response,aList);

    }

    /**
     * 获取市场活动详细信息操作，
     * @param request   当前request对象，
     * @param response  当前response对象。
     * @throws ServletException
     * @throws IOException
     */
    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("获取市场活动详细信息操作");

        String id = request.getParameter("id");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        Activity activity = activityService.detail(id);


        request.setAttribute("a",activity);
        request.getRequestDispatcher("/workbench/activity/detail.jsp").forward(request,response);

    }

    /**
     * 更新市场活动列表操作，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void editUpdate(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入更新市场活动信息操作");
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String describe = request.getParameter("describe");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User) request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();
        activity.setId(id);
        activity.setName(name);
        activity.setOwner(owner);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(describe);
        activity.setEditTime(editTime);
        activity.setEditBy(editBy);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        try{
            Boolean flag = activityService.editUpdate(activity);
            PrintJson.printJsonFlag(response,flag);
        }catch (Exception e){
            e.printStackTrace();
            String message = e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("message",message);
            PrintJson.printJsonObj(response,map);
        }
    }

    /**
     * 获取修改市场活动信息操作，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void edit(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("根据市场活动id，取得备注信息列表");
        String id = request.getParameter("id");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        Map<String,Object> map = activityService.edit(id);

        PrintJson.printJsonObj(response,map);
    }

    /**
     * 删除市场活动列表操作，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void delete(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行市场活动的删除操作");
        String idS[] = request.getParameterValues("id");

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        Boolean flag = activityService.delete(idS);
        PrintJson.printJsonFlag(response,flag);
    }

    /**
     * 获取市场活动列表操作，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void pageList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");
        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.parseInt(pageNoStr);

        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.parseInt(pageSizeStr);

        int skipCount = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());

        PaginationVO<Activity> vo = activityService.pageList(map);

        PrintJson.printJsonObj(response,vo);
    }

    /**
     * 创建市场活动列表操作，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void save(HttpServletRequest request, HttpServletResponse response){

        System.out.println("执行市场活动添加操作");
        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)request.getSession().getAttribute("user")).getName();

        Activity activity = new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        try {
            boolean state = activityService.save(activity);
            PrintJson.printJsonFlag(response,state);

        }catch (Exception e){
            e.printStackTrace();

            String message = e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("message",message);
            PrintJson.printJsonObj(response,map);
        }
    }

    /**
     * 获取所有用户信息操作，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("取得用户信息列表");

        UserService userInfo = (UserService) ServiceFactory.getService(new UserServiceImp());
        List<User> uList = userInfo.getUserInfo();
        PrintJson.printJsonObj(response,uList);
    }

}
