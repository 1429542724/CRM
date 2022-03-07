package com.rokai.crm.workbench.web.controller;

import com.rokai.crm.utils.DateTimeUtil;
import com.rokai.crm.utils.PrintJson;
import com.rokai.crm.utils.ServiceFactory;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.domain.Contacts;
import com.rokai.crm.workbench.service.ContactsService;
import com.rokai.crm.workbench.service.imp.ContactsServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ContactsServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println(DateTimeUtil.getSysTime()+" 进入联系人模块控制器");
        String path = request.getServletPath();

        if ("/workbench/contacts/getContactsPage.do".equals(path)){
            getContactsPage(request,response);
        }

    }

    /**
     * 联系人列表 - 获取联系人信息功能，
     * @param request   当前请求作用域对象，
     * @param response  当前响应作用于对象。
     */
    private void getContactsPage(HttpServletRequest request, HttpServletResponse response) {

        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String customerName = request.getParameter("customerName");
        String source = request.getParameter("source");
        String birth = request.getParameter("birth");
        Integer pagesStr = Integer.valueOf(request.getParameter("pages"));
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));

        Integer pages = (pagesStr -1) * Integer.valueOf(pageNum);

        Map<String,Object> map = new HashMap<>();
        map.put("owner",owner);
        map.put("name",name);
        map.put("customerName",customerName);
        map.put("source",source);
        map.put("birth",birth);
        map.put("pages",pages);
        map.put("pageNum",pageNum);

        ContactsService contactsService = (ContactsService) ServiceFactory.getService(new ContactsServiceImp());
        PaginationVO<Contacts> paginationVO = contactsService.getContactsPage(map);

        PrintJson.printJsonObj(response,paginationVO);

    }


}
