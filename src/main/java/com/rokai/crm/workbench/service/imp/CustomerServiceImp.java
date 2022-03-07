package com.rokai.crm.workbench.service.imp;

import com.rokai.crm.exception.CustomerException;
import com.rokai.crm.utils.SqlSessionUtil;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.dao.CustomerDao;
import com.rokai.crm.workbench.dao.CustomerRemarkDao;
import com.rokai.crm.workbench.domain.Customer;
import com.rokai.crm.workbench.service.CustomerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerServiceImp implements CustomerService {

    CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    CustomerRemarkDao customerRemarkDao = SqlSessionUtil.getSqlSession().getMapper(CustomerRemarkDao.class);

    @Override
    public PaginationVO<Customer> getPageList(Map<String, Object> map) {

        List<Customer> customerList = customerDao.getPageList(map);

        Integer count = customerDao.getPageListCount(map);

        PaginationVO<Customer> paginationVO = new PaginationVO<>();
        paginationVO.setDataList(customerList);
        paginationVO.setTotal(count);

        return paginationVO;
    }

    @Override
    public Map<String, Object> saveCustomer(Customer customer) throws CustomerException {

        Map map = new HashMap();

        if (customer.getName() != "" & customer.getName() != null){
            Customer customerIfExist = customerDao.getCustomerIfExist(customer.getName());

            if (customerIfExist == null){
                int saveState = customerDao.createCustomer(customer);

                if (saveState != 1){
                    map.put("flag",false);
                }
                map.put("flag",true);
                map.put("message","保存成功~");
            }else {
                throw new CustomerException("error 客户已存在！");
            }
        }else {
            throw new CustomerException("error 名称不能为空！");
        }

        return map;
    }

    @Override
    public boolean removeCustomerArray(String[] customerIdArray) throws CustomerException {

        boolean flag = true;

        int removeRemarkState = customerRemarkDao.removeCustomerRemarkArray(customerIdArray);
        int removeState = customerDao.removeCustomerArray(customerIdArray);
        if (removeState < 1){
            flag = false;
            throw new CustomerException("error！ 删除客户信息失败,");
        }
        return flag;
    }

    @Override
    public List<String> getCustomerName(String name) {

        List<String> customerName = customerDao.getCustomerName(name);

        return customerName;
    }

}
