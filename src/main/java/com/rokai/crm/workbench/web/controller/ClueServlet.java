package com.rokai.crm.workbench.web.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClueServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入线索控制器");
        String path = request.getServletPath();
        if ("/workbench/clue/xxx.do".equals(path)){
            //xxx(request,response);
        }/*else if ("/workbench/clue/xxx.do".equals(path)){

        }*/

    }

}
