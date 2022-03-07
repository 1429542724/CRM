package com.rokai.crm.workbench.web.controller;

import com.rokai.crm.settings.domain.User;
import com.rokai.crm.utils.DateTimeUtil;
import com.rokai.crm.utils.PrintJson;
import com.rokai.crm.utils.ServiceFactory;
import com.rokai.crm.utils.UUIDUtil;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.domain.Activity;
import com.rokai.crm.workbench.domain.Clue;
import com.rokai.crm.workbench.domain.ClueRemark;
import com.rokai.crm.workbench.domain.Tran;
import com.rokai.crm.workbench.service.ActivityService;
import com.rokai.crm.workbench.service.ClueService;
import com.rokai.crm.workbench.service.imp.ActivityServiceImp;
import com.rokai.crm.workbench.service.imp.ClueServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入线索控制器");
        String path = request.getServletPath();
        if ("/workbench/clue/getUserList.do".equals(path)){
            getUserList(request,response);
        }else if ("/workbench/clue/saveClue.do".equals(path)){
            saveClue(request,response);
        }else if ("/workbench/clue/getAllClueInfo.do".equals(path)){
            pagList(request,response);
        }else if ("/workbench/clue/deleteClue.do".equals(path)){
            deleteClue(request,response);
        }else if ("/workbench/clue/modifyWin.do".equals(path)){
            modifyWin(request,response);
        }else if("/workbench/clue/updateClue.do".equals(path)){
            updateClue(request,response);
        }else if("/workbench/clue/detail.do".equals(path)){
            detail(request,response);
        }else if ("/workbench/clue/saveClueRemark.do".equals(path)){
            saveClueRemark(request,response);
        }else if ("/workbench/clue/loadClueRemark.do".equals(path)){
            loadClueRemark(request,response);
        }else if ("/workbench/clue/deleteClueRemark.do".equals(path)){
            deleteClueRemark(request,response);
        }else if ("/workbench/clue/updateClueRemark.do".equals(path)){
            updateClueRemark(request,response);
        }else if ("/workbench/clue/loadClueActivityRelation.do".equals(path)){
            loadClueActivityRelation(request,response);
        }else if ("/workbench/clue/deleteRelation.do".equals(path)){
            deleteRelation(request,response);
        }else if ("/workbench/clue/getActivity.do".equals(path)){
            getActivity(request,response);
        }else if ("/workbench/clue/relation.do".equals(path)){
            relation(request,response);
        }else if ("/workbench/clue/convert.do".equals(path)){
            convert(request,response);
        }else if ("/workbench.clue/getActivityList.do".equals(path)){
            getActivityList(request,response);
        }else if ("/workbench/clue/convertFunction.do".equals(path)){
            convertFunction(request,response);
        }

    }

    /**
     * 线索转换 - 获取线性转换信息，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void convertFunction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String clueId = request.getParameter("clueId");
        String company = request.getParameter("company");
        String fullName = request.getParameter("fullName");
        String appellation = request.getParameter("appellation");
        String createDeal = request.getParameter("createDeal");
        String owner = request.getParameter("owner");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        Map<String,Object> map = new HashMap<>();
        map.put("clueId",clueId);
        map.put("company",company);
        map.put("fullName",fullName);
        map.put("appellation",appellation);
        map.put("createDeal",createDeal);
        map.put("owner",owner);
        map.put("createBy",createBy);

        String amountOfMoney = request.getParameter("amountOfMoney");
        String tradeName = request.getParameter("tradeName");
        String expectedClosingDate = request.getParameter("expectedClosingDate");
        String stage = request.getParameter("stage");
        String activity = request.getParameter("activity");
        String hiddenActivityId = request.getParameter("hiddenActivityId");

        Tran tran = new Tran();
        tran.setId(UUIDUtil.getUUID());
        tran.setOwner(owner);
        tran.setMoney(amountOfMoney);
        tran.setName(tradeName);
        tran.setExpectedDate(expectedClosingDate);
        tran.setCustomerId("");
        tran.setStage(stage);
        tran.setType("");
        tran.setSource("");
        tran.setActivityId(hiddenActivityId);
        tran.setContactsId("");
        tran.setCreateBy(createBy);
        tran.setCreateTime("");
        tran.setDescription("");
        tran.setContactSummary("");
        tran.setNextContactTime("");

        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        boolean flag = service.convertFunction(map,tran);

        PrintJson.printJsonFlag(response,flag);
    }

    /**
     * 转换线索 - 搜索市场活动功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void getActivityList(HttpServletRequest request, HttpServletResponse response) {

        String dimSearchFrame = request.getParameter("DimSearchFrame");

        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        List<Activity> list = service.getActivityList(dimSearchFrame);

        PrintJson.printJsonObj(response,list);

    }

    /**
     * 线索详细信息数据共享转换，
     * @param request   当前request对象，
     * @param response  当前response对象。
     * @throws ServletException
     * @throws IOException
     */
    private void convert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String clueId = request.getParameter("clueId");
        String fullName = request.getParameter("fullName");
        String company = request.getParameter("company");
        String owner = request.getParameter("owner");
        String appellation = request.getParameter("appellation");
        String ownerId = request.getParameter("ownerId");

        Map<String,Object> map = new HashMap<>();
        map.put("clueId",clueId);
        map.put("fullName",fullName);
        map.put("company",company);
        map.put("owner",owner);
        map.put("appellation",appellation);
        map.put("ownerId",ownerId);

        request.setAttribute("map",map);
        request.getRequestDispatcher("/workbench/clue/convert.jsp").forward(request,response);
    }

    /**
     * 线索详细信息页市场活动关联功能，
     * @param request   当前request对象，
     * @param response  当前response对象，
     */
    private void relation(HttpServletRequest request, HttpServletResponse response) {

        String clueId = request.getParameter("clueId");
        String[] activityIds = request.getParameterValues("activityId");

        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        Map<String,Object> map = service.relation(clueId,activityIds);

        PrintJson.printJsonObj(response,map);
    }

    /**
     * 获取线索详细信息页关联市场活动信息功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void getActivity(HttpServletRequest request, HttpServletResponse response) {

        String clueId = request.getParameter("clueId");
        String dimName = request.getParameter("dimName");
        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.parseInt(pageSizeStr);

        Integer skipCount = (Integer.parseInt(pageNoStr) -1) * pageSize;

        Map<String,Object> map = new HashMap<>();
        map.put("clueId",clueId);
        map.put("dimName",dimName);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);

        ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
        Map<String,Object> rMap = service.getActivity(map);

        PrintJson.printJsonObj(response,rMap);
    }

    /**
     * 线索详细信息页市场活动删除功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void deleteRelation(HttpServletRequest request, HttpServletResponse response) {

        String relationId = request.getParameter("relationId");
        try {
            ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
            boolean flag = service.deleteRelation(relationId);

            Map<String,Object> map = new HashMap<>();
            map.put("flag",flag);
            map.put("message","删除成功~");

            PrintJson.printJsonObj(response,map);
        } catch (Exception e) {
            e.printStackTrace();

            String message = e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("flag",false);
            map.put("message",message);

            PrintJson.printJsonObj(response,map);
        }
    }

    /**
     * 加载线索详细信息市场活动关系功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void loadClueActivityRelation(HttpServletRequest request, HttpServletResponse response) {

        String clueId = request.getParameter("clueId");

        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        List<Activity> list = service.loadClueActivityRelation(clueId);

        PrintJson.printJsonObj(response,list);
    }

    /**
     * 修改线索详细信息备注功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void updateClueRemark(HttpServletRequest request, HttpServletResponse response) {

        String clueRemarkId = request.getParameter("clueRemarkId");
        String noteContent = request.getParameter("noteContent");

        ClueRemark clueRemark = new ClueRemark();
        clueRemark.setId(clueRemarkId);
        clueRemark.setNoteContent(noteContent);
        clueRemark.setEditFlag("1");
        clueRemark.setEditTime(DateTimeUtil.getSysTime());
        clueRemark.setEditBy(((User)request.getSession().getAttribute("user")).getName());

        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        boolean flag = service.updateClueRemark(clueRemark);

        PrintJson.printJsonFlag(response,flag);
    }

    /**
     * 删除线索详细信息备注功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void deleteClueRemark(HttpServletRequest request, HttpServletResponse response) {

        String clueRemarkId = request.getParameter("clueRemarkId");

        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        boolean flag = service.deleteClueRemark(clueRemarkId);

        PrintJson.printJsonFlag(response,flag);
    }

    /**
     * 加载线索详细信息备注功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void loadClueRemark(HttpServletRequest request, HttpServletResponse response) {

        String clueId = request.getParameter("clueId");

        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        List<ClueRemark> clueRemarkList = service.loadClueRemark(clueId);

        PrintJson.printJsonObj(response,clueRemarkList);
    }

    /**
     * 保存线索详细信息备注功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void saveClueRemark(HttpServletRequest request, HttpServletResponse response) {

        String remark = request.getParameter("remark");
        String clueId = request.getParameter("clueId");
        ClueRemark clueRemark = new ClueRemark();
        clueRemark.setId(UUIDUtil.getUUID());
        clueRemark.setNoteContent(remark);
        clueRemark.setCreateBy(((User) request.getSession().getAttribute("user")).getName());
        clueRemark.setCreateTime(DateTimeUtil.getSysTime());
        clueRemark.setEditFlag("0");
        clueRemark.setClueId(clueId);

        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        Boolean flag = service.saveClueRemark(clueRemark);

        PrintJson.printJsonFlag(response,flag);

    }

    /**
     * 获取详细信息页信息功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     * @throws ServletException     控制器异常处理,
     * @throws IOException      IO异常处理。
     */
    private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        Clue clue = service.detail(id);

        request.setAttribute("clue",clue);
        request.getRequestDispatcher("/workbench/clue/detail.jsp").forward(request,response);
    }

    /**
     * 更新线索信息功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void updateClue(HttpServletRequest request, HttpServletResponse response) {

        Clue clue = new Clue();
        clue.setId(request.getParameter("id"));
        clue.setOwner(request.getParameter("owner"));
        clue.setCompany(request.getParameter("company"));
        clue.setAppellation(request.getParameter("appellation"));
        clue.setFullname(request.getParameter("fullname"));
        clue.setJob(request.getParameter("job"));
        clue.setEmail(request.getParameter("email"));
        clue.setPhone(request.getParameter("phone"));
        clue.setWebsite(request.getParameter("website"));
        clue.setMphone(request.getParameter("mphone"));
        clue.setState(request.getParameter("state"));
        clue.setSource(request.getParameter("source"));
        clue.setDescription(request.getParameter("description"));
        clue.setContactSummary(request.getParameter("contactSummary"));
        clue.setNextContactTime(request.getParameter("nextContactTime"));
        clue.setAddress(request.getParameter("address"));
        String editName = ((User)request.getSession().getAttribute("user")).getName();
        clue.setEditBy(editName);
        clue.setEditTime(DateTimeUtil.getSysTime());

        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        boolean flag = service.updateClue(clue);

        PrintJson.printJsonFlag(response,flag);
    }

    /**
     * 获取修改线索窗口信息，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void modifyWin(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");
        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        Map<String,Object> map = service.modifyWin(id);

        PrintJson.printJsonObj(response,map);
    }

    /**
     * 删除线索信息功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void deleteClue(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入删除线索信息功能");
        String[] idArray = request.getParameterValues("id");

        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        try {
            boolean flag = service.deleteClueArray(idArray);
            PrintJson.printJsonFlag(response, flag);
        } catch (Exception e) {
            e.printStackTrace();

            String message = e.getMessage();
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("message", message);
            PrintJson.printJsonObj(response, map);
        }
    }

    /**
     * 获取线索信息列表功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void pagList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入查询线索列表功能");
        String fullname = request.getParameter("fullname");
        String company = request.getParameter("company");
        String phone = request.getParameter("phone");
        String source = request.getParameter("source");
        String owner = request.getParameter("owner");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String pageNoStr = request.getParameter("pageNo");
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.parseInt(pageSizeStr);

        int skipCount = (Integer.parseInt(pageNoStr) -1)* pageSize;

        Map<String,Object> map = new HashMap<>();
            map.put("pages",skipCount);
            map.put("pageSize",pageSize);
            map.put("fullname",fullname);
            map.put("company",company);
            map.put("phone",phone);
            map.put("source",source);
            map.put("owner",owner);
            map.put("mphone",mphone);
            map.put("state",state);

        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        PaginationVO<Clue> clueVO = service.pageList(map);

        PrintJson.printJsonObj(response,clueVO);
    }

    /**
     * 添加线索信息功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void saveClue(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入添加线索信息功能");
        String id = UUIDUtil.getUUID();
        String fullname = request.getParameter("fullname");
        String appellation = request.getParameter("appellation");
        String owner = request.getParameter("owner");
        String company = request.getParameter("company");
        String job = request.getParameter("job");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String website = request.getParameter("website");
        String mphone = request.getParameter("mphone");
        String state = request.getParameter("state");
        String source = request.getParameter("source");
        String createBy = ((User) request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("description");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");

        Clue clue = new Clue();
        clue.setId(id);
        clue.setFullname(fullname);
        clue.setAppellation(appellation);
        clue.setOwner(owner);
        clue.setCompany(company);
        clue.setJob(job);
        clue.setEmail(email);
        clue.setPhone(phone);
        clue.setWebsite(website);
        clue.setMphone(mphone);
        clue.setState(state);
        clue.setSource(source);
        clue.setCreateBy(createBy);
        clue.setCreateTime(createTime);
        clue.setDescription(description);
        clue.setContactSummary(contactSummary);
        clue.setNextContactTime(nextContactTime);
        clue.setAddress(address);

        ClueService service = (ClueService) ServiceFactory.getService(new ClueServiceImp());
        try {
            boolean flag = service.saveClue(clue);
            PrintJson.printJsonFlag(response,flag);
        } catch (Exception e) {
            e.printStackTrace();

            String message = e.getMessage();
            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("message",message);
            PrintJson.printJsonObj(response,map);
        }
    }

    /**
    * 获取用户信息功能，
    * @param request   当前request对象，
    * @param response  当前response对象。
    */
    private void getUserList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入获取用户信息功能");
        ClueService clueService = (ClueService) ServiceFactory.getService(new ClueServiceImp());

        List<User> userList = clueService.getUserList();

        PrintJson.printJsonObj(response,userList);
    }

}
