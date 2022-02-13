package com.rokai.workbench.test;

import com.rokai.crm.exception.ActivityException;
import com.rokai.crm.utils.ServiceFactory;
import com.rokai.crm.utils.UUIDUtil;
import com.rokai.crm.workbench.domain.Activity;
import com.rokai.crm.workbench.service.ActivityService;
import com.rokai.crm.workbench.service.imp.ActivityServiceImp;
import org.junit.Assert;
import org.junit.Test;

public class TestActivity {

    @Test
    public void testSaveActivity() {

        Activity activity = new Activity();
        activity.setId(UUIDUtil.getUUID());
        activity.setName("Test");

        try {
            ActivityService service = (ActivityService) ServiceFactory.getService(new ActivityServiceImp());
            boolean flag = false;
            flag = service.save(activity);
//        boolean flag = false;
            System.out.println("预取结果:\t"+flag);
            Assert.assertEquals(flag,true);
        } catch (ActivityException e) {
            e.printStackTrace();
        }

    }

}
