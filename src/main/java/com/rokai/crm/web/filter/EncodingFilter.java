package com.rokai.crm.web.filter;

import com.rokai.crm.utils.DateTimeUtil;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements javax.servlet.Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println(DateTimeUtil.getSysTime()+" 进入到过滤字符编码的过滤器,");

        servletRequest.setCharacterEncoding("UTF-8");

        servletResponse.setContentType("text/html;charset=utf-8");

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
