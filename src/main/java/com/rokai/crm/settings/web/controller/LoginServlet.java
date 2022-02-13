package com.rokai.crm.settings.web.controller;

import com.rokai.crm.settings.domain.User;
import com.rokai.crm.settings.service.UserService;
import com.rokai.crm.settings.service.imp.UserServiceImp;
import com.rokai.crm.utils.MD5Util;
import com.rokai.crm.utils.PrintJson;
import com.rokai.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到用户控制器");

        String path = request.getServletPath();
        if ("/settings/user/login.do".equals(path)){
            login(request,response);
        }

    }

    /**
     * 用户登陆验证功能，
     * @param request   当前request对象，
     * @param response  当前response对象。
     */
    private void login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到验证登陆操作");
        String account = request.getParameter("account");
        String password = request.getParameter("password");

        //将密码明文转换成MD5密文，
        password = MD5Util.getMD5(password);

        //获取ip地址，
        String ip = request.getRemoteAddr();

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImp());
        try{
            User user = userService.login(account,password,ip);
            request.getSession().setAttribute("user",user);
            PrintJson.printJsonFlag(response,true);

        }catch (Exception e){
            e.printStackTrace();
            String message = e.getMessage();

            Map<String,Object> map = new HashMap<>();
            map.put("success",false);
            map.put("msg",message);
            PrintJson.printJsonObj(response,map);
        }
    }

}
