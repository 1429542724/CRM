package com.rokai.crm.workbench.web.controller;

import com.rokai.crm.settings.domain.User;
import com.rokai.crm.settings.service.UserService;
import com.rokai.crm.settings.service.imp.UserServiceImp;
import com.rokai.crm.utils.DateTimeUtil;
import com.rokai.crm.utils.PrintJson;
import com.rokai.crm.utils.ServiceFactory;
import com.rokai.crm.utils.UUIDUtil;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.domain.Contacts;
import com.rokai.crm.workbench.domain.Tran;
import com.rokai.crm.workbench.domain.TranHistory;
import com.rokai.crm.workbench.domain.TranRemark;
import com.rokai.crm.workbench.service.ContactsService;
import com.rokai.crm.workbench.service.CustomerService;
import com.rokai.crm.workbench.service.TranService;
import com.rokai.crm.workbench.service.imp.ContactsServiceImp;
import com.rokai.crm.workbench.service.imp.CustomerServiceImp;
import com.rokai.crm.workbench.service.imp.TranServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TranServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(DateTimeUtil.getSysTime()+" 进入交易模块控制器");

        String path = request.getServletPath();
        if ("/workbench/transaction/openSaveWindow.do".equals(path)){
            openSaveWindow(request,response);
        }else if ("/workbench/transaction/getCustomerName.do".equals(path)){
            getCustomerName(request,response);
        }else if ("/workbench/transaction/getContactsList.do".equals(path)){
            getContactsList(request,response);
        }else if ("/workbench/transaction/getTransactionList.do".equals(path)){
            getTransactionList(request,response);
        }else if ("/workbench/transaction/saveTransaction.do".equals(path)){
            saveTransaction(request,response);
        }else if ("/workbench/transaction/getDetailInfo.do".equals(path)){
            getDetailInfo(request,response);
        }else if ("/workbench/transaction/getTransactionHistoryInfo.do".equals(path)){
            getTransactionHistoryInfo(request,response);
        }else if ("/workbench/transaction/changeStage.do".equals(path)){
            changeStage(request,response);
        }else if ("/workbench/transaction/getRemarkInfo.do".equals(path)){
            getRemarkInfo(request,response);
        }else if ("/workbench/transaction/saveRemark.do".equals(path)){
            saveRemark(request,response);
        }else if ("/workbench/transaction/editRemark.do".equals(path)){
            editRemark(request,response);
        }else if ("/workbench/transaction/deleteRemark.do".equals(path)){
            deleteRemark(request,response);
        }else if ("/workbench/transaction/getChartsInfo.do".equals(path)){
            getChartsInfo(request,response);
        }

    }

    /**
     * 统计图表 - 获取交易统计表信息功能，
     * @param request   当前请求作用域，
     * @param response  当前响应作用域。
     */
    private void getChartsInfo(HttpServletRequest request, HttpServletResponse response) {

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImp());

        Map<String,Object> map = tranService.getChartsInfo();

        PrintJson.printJsonObj(response,map);
    }

    /**
     * 详细信息 - 删除备注信息功能，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用域对象。
     */
    private void deleteRemark(HttpServletRequest request, HttpServletResponse response) {

        String remarkId = request.getParameter("remarkId");

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImp());
        boolean flag = tranService.deleteRemark(remarkId);

        PrintJson.printJsonFlag(response,flag);
    }

    /**
     * 详细信息 - 修改备注信息功能，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用域对象。
     */
    private void editRemark(HttpServletRequest request, HttpServletResponse response) {

        String remarkId = request.getParameter("remarkId");
        String editRemark = request.getParameter("editRemark");
        String editBy = ((User)request.getSession().getAttribute("user")).getName();
        String editTime = DateTimeUtil.getSysTime();
        String editFlag = "1";

        TranRemark tranRemark = new TranRemark();
        tranRemark.setId(remarkId);
        tranRemark.setNoteContent(editRemark);
        tranRemark.setEditBy(editBy);
        tranRemark.setEditTime(editTime);
        tranRemark.setEditFlag(editFlag);

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImp());
        boolean flag = tranService.editRemark(tranRemark);

        PrintJson.printJsonFlag(response,flag);
    }

    /**
     * 详细信息 - 保存备注信息功能，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用域对象。
     */
    private void saveRemark(HttpServletRequest request, HttpServletResponse response) {

        String tranId = request.getParameter("tranId");
        String remark = request.getParameter("remark");

        TranRemark tranRemark = new TranRemark();
        tranRemark.setId(UUIDUtil.getUUID());
        tranRemark.setCreateBy(((User)request.getSession().getAttribute("user")).getName());
        tranRemark.setCreateTime(DateTimeUtil.getSysTime());
        tranRemark.setEditFlag("0");
        tranRemark.setNoteContent(remark);
        tranRemark.setTranId(tranId);

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImp());
        try {

            boolean flag = tranService.saveRemark(tranRemark);
            PrintJson.printJsonFlag(response,flag);
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();

            Map<String,Object> map = new HashMap<>();
            map.put("message",message);
            map.put("success",false);

            PrintJson.printJsonObj(response,message);
        }


    }

    /**
     * 详细信息 - 获取详细信息功能，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用域对象。
     */
    private void getRemarkInfo(HttpServletRequest request, HttpServletResponse response) {

        String tranId = request.getParameter("tranId");

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImp());
        List<TranRemark> tranRemarkList = tranService.getRemarkInfo(tranId);

        PrintJson.printJsonObj(response,tranRemarkList);

    }

    /**
     * 详细信息 - 变更阶段信息功能，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用域对象。
     */
    private void changeStage(HttpServletRequest request, HttpServletResponse response) {

        String stage = request.getParameter("stage");
        String tranId = request.getParameter("tranId");
        String money = request.getParameter("money");
        String expectedDate = request.getParameter("expectedDate");
        String editTime = DateTimeUtil.getSysTime();
        String editBy = ((User)request.getSession().getAttribute("user")).getName();

        Tran tran = new Tran();
        tran.setId(tranId);
        tran.setStage(stage);
        tran.setMoney(money);
        tran.setExpectedDate(expectedDate);
        tran.setEditTime(editTime);
        tran.setEditBy(editBy);

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setStage(stage);
        tranHistory.setMoney(money);
        tranHistory.setExpectedDate(expectedDate);
        tranHistory.setCreateTime(editTime);
        tranHistory.setCreateBy(editBy);
        tranHistory.setTranId(tranId);

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImp());
        boolean flag  = tranService.changeStage(tran,tranHistory);

        Map<String,Object> mapTwo = (Map<String, Object>) request.getServletContext().getAttribute("stageMap");
        tranHistory.setPossibility((String) mapTwo.get(stage));

        Map<String,Object> map = new HashMap<>();
        map.put("flag",flag);
        map.put("tranHistory",tranHistory);


        PrintJson.printJsonObj(response,map);

    }

    /**
     * 详细信息 - 获取交易历史信息功能，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用域对象。
     */
    private void getTransactionHistoryInfo(HttpServletRequest request, HttpServletResponse response) {

        String tranId = request.getParameter("tranId");

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImp());
        List<TranHistory> tranHistoryList = tranService.getTransactionHistoryInfo(tranId);

        Map<String,Object> map = (Map<String, Object>) this.getServletContext().getAttribute("stageMap");
        for (TranHistory tranHistory:tranHistoryList){

            String stage = tranHistory.getStage();
            String possibility = (String) map.get(stage);
            tranHistory.setPossibility(possibility);
        }

        PrintJson.printJsonObj(response,tranHistoryList);

    }

    /**
     * 详细信息 - 获取详细交易信息功能，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用域对象。
     */
    private void getDetailInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String tranId = request.getParameter("tranId");

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImp());
        Tran tran = tranService.getDetailInfo(tranId);

        request.setAttribute("tran",tran);
        request.getRequestDispatcher("/workbench/transaction/detail.jsp").forward(request,response);
    }

    /**
     * 创建交易 - 保存交易功能，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用域对象。
     */
    private void saveTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("create-transactionOwner");
        String money = request.getParameter("create-amountOfMoney");
        String name = request.getParameter("create-transactionName");
        String expectedDate = request.getParameter("create-expectedClosingDate");
        String customerName = request.getParameter("create-customerName");
        String stage = request.getParameter("create-transactionStage");
        String type = request.getParameter("create-transactionType");
        String source = request.getParameter("create-clueSource");
        String activityId = request.getParameter("hiddenActivityId");
        String contactsId = request.getParameter("hiddenContactsId");
        String createBy = ((User)request.getSession().getAttribute("user")).getName();
        String createTime = DateTimeUtil.getSysTime();
        String description = request.getParameter("create-describe");
        String contactSummary = request.getParameter("create-contactSummary");
        String nextContactTime = request.getParameter("create-nextContactTime");

        Tran tran = new Tran();
        tran.setId(id);
        tran.setOwner(owner);
        tran.setMoney(money);
        tran.setName(name);
        tran.setExpectedDate(expectedDate);
        tran.setCustomerId("");
        tran.setStage(stage);
        tran.setType(type);
        tran.setSource(source);
        tran.setActivityId(activityId);
        tran.setContactsId(contactsId);
        tran.setCreateBy(createBy);
        tran.setCreateTime(createTime);
        tran.setDescription(description);
        tran.setContactSummary(contactSummary);
        tran.setNextContactTime(nextContactTime);

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImp());
        boolean flag = tranService.saveTransaction(tran,customerName);

        if (flag){
            response.sendRedirect(request.getContextPath()+"/workbench/transaction/index.jsp");
        }

    }

    /**
     * 交易列表- 获取交易列表信息功能，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用域对象。
     */
    private void getTransactionList(HttpServletRequest request, HttpServletResponse response) {

        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String customerName = request.getParameter("customerName");
        String contactsName = request.getParameter("contactsName");
        String stage = request.getParameter("stage");
        String transactionType = request.getParameter("transactionType");
        String source = request.getParameter("source");
        Integer pagesStr = Integer.valueOf(request.getParameter("pages"));
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));

        Integer pages = (pagesStr -1) * pageNum;

        Map<String,Object> map = new HashMap<>();
        map.put("owner",owner);
        map.put("name",name);
        map.put("customerName",customerName);
        map.put("contactsName",contactsName);
        map.put("stage",stage);
        map.put("transactionType",transactionType);
        map.put("source",source);
        map.put("pages",pages);
        map.put("pageNum",pageNum);

        TranService tranService = (TranService) ServiceFactory.getService(new TranServiceImp());
        PaginationVO<Tran> tranPaginationVO =  tranService.getTransactionList(map);

        PrintJson.printJsonObj(response,tranPaginationVO);
    }

    /**
     * 创建交易 - 获取联系人信息功能，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用域对象。
     */
    private void getContactsList(HttpServletRequest request, HttpServletResponse response) {

        String contactsName = request.getParameter("contactsName");

        ContactsService contactsService = (ContactsService) ServiceFactory.getService(new ContactsServiceImp());
        List<Contacts> contactsList = contactsService.getContactsList(contactsName);

        PrintJson.printJsonObj(response,contactsList);

    }

    /**
     * 创建交易 - 获取客户名称功能，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用域对象。
     */
    private void getCustomerName(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");

        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceImp());
        List<String> customerName = customerService.getCustomerName(name);

        PrintJson.printJsonObj(response,customerName);
    }

    /**
     * 交易列表 - 打开保存交易窗口，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用域对象。
     */
    private void openSaveWindow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImp());
        List<User> userInfo = userService.getUserInfo();

        request.setAttribute("userInfo",userInfo);
        request.getRequestDispatcher("/workbench/transaction/save.jsp").forward(request,response);
    }

}
