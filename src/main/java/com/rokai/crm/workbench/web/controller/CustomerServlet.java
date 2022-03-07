package com.rokai.crm.workbench.web.controller;

import com.rokai.crm.exception.CustomerException;
import com.rokai.crm.settings.domain.User;
import com.rokai.crm.utils.DateTimeUtil;
import com.rokai.crm.utils.PrintJson;
import com.rokai.crm.utils.ServiceFactory;
import com.rokai.crm.utils.UUIDUtil;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.domain.Customer;
import com.rokai.crm.workbench.service.CustomerService;
import com.rokai.crm.workbench.service.imp.CustomerServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入客户模块控制器");
        String path = request.getServletPath();


        if ("/workbench/customer/getCustomer.do".equals(path)){
            getPageList(request,response);
        }else if ("/workbench/customer/saveCustomer.do".equals(path)){
            saveCustomer(request,response);
        }else if ("/workbench/customer/removeCustomer.do".equals(path)){
            removeCustomer(request,response);
        }

    }

    /**
     * 客户列表 - 删除客户信息功能，
     * @param request  当前请求对象，
     * @param response  当前响应对象。
     */
    private void removeCustomer(HttpServletRequest request, HttpServletResponse response) {

        String[] customerIdArray = request.getParameterValues("customerId");

        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceImp());
        Map<String,Object> map = new HashMap<>();
        try {
            boolean flag = customerService.removeCustomerArray(customerIdArray);

            map.put("flag",flag);
            map.put("message","删除成功~");

            PrintJson.printJsonObj(response,map);
        } catch (CustomerException e) {
            e.printStackTrace();
            String message = e.getMessage();

            map.put("flag",false);
            map.put("message",message);

            PrintJson.printJsonObj(response,map);
        }

    }

    /**
     * 客户列表 - 保存创建客户信息功能，
     * @param request  当前请求对象，
     * @param response  当前响应对象。
     */
    private void saveCustomer(HttpServletRequest request, HttpServletResponse response) {

        String owner = request.getParameter("owner");
        String customerName = request.getParameter("customerName");
        String website = request.getParameter("website");
        String phone = request.getParameter("phone");
        String describe = request.getParameter("describe");
        String contactSummary = request.getParameter("contactSummary");
        String nextContactTime = request.getParameter("nextContactTime");
        String address = request.getParameter("address");
        String id = UUIDUtil.getUUID();
        String createTime = DateTimeUtil.getSysTime();
        String createBy = ((User)(request.getSession().getAttribute("user"))).getName();

        Customer customer = new Customer();
        customer.setId(id);
        customer.setOwner(owner);
        customer.setName(customerName);
        customer.setWebsite(website);
        customer.setPhone(phone);
        customer.setDescription(describe);
        customer.setContactSummary(contactSummary);
        customer.setNextContactTime(nextContactTime);
        customer.setAddress(address);
        customer.setCreateTime(createTime);
        customer.setCreateBy(createBy);

        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceImp());
        try {
            Map<String,Object> saveState = customerService.saveCustomer(customer);

            PrintJson.printJsonObj(response,saveState);
        } catch (CustomerException e) {
            e.printStackTrace();
            String message = e.getMessage();

            Map map = new HashMap();
            map.put("flag",false);
            map.put("message",message);
            PrintJson.printJsonObj(response,map);
        }

    }

    /**
     * 客户列表 - 获取客户列表信息功能，
     * @param request  当前请求对象，
     * @param response  当前响应对象。
     */
    private void getPageList(HttpServletRequest request, HttpServletResponse response) {

        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String phone = request.getParameter("phone");
        String webSite = request.getParameter("webSite");
        Integer pages = Integer.valueOf(request.getParameter("pages"));
        Integer pageNum = Integer.valueOf(request.getParameter("pageNum"));

        Integer skipCount = (pages - 1) * pageNum;

        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("phone",phone);
        map.put("webSite",webSite);
        map.put("skipCount",skipCount);
        map.put("pageNum",pageNum);

        CustomerService customerService = (CustomerService) ServiceFactory.getService(new CustomerServiceImp());
        PaginationVO<Customer> customerVO = customerService.getPageList(map);

        PrintJson.printJsonObj(response,customerVO);
    }

}
