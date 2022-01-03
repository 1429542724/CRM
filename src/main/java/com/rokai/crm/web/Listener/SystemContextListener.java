package com.rokai.crm.web.Listener;

import com.rokai.crm.settings.domain.DicValue;
import com.rokai.crm.settings.service.DicService;
import com.rokai.crm.settings.service.imp.DicServiceImp;
import com.rokai.crm.utils.ServiceFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SystemContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("服务器缓存\"数据字典\"开始，");
        long start = System.currentTimeMillis();

        ServletContext servletContext = sce.getServletContext();
        DicService dicService = (DicService) ServiceFactory.getService(new DicServiceImp());

        Map<String, List<DicValue>> map = dicService.getDicData();
        Set<String> set = map.keySet();
        for (String key:set){
            servletContext.setAttribute(key,map.get(key));
        }

        long end = System.currentTimeMillis();
        System.out.println("服务器缓存\"数据字典\"结束，消耗时间("+(end - start) / 1000.00+")秒。");
    }

}
