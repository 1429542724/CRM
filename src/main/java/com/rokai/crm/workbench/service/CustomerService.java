package com.rokai.crm.workbench.service;

import com.rokai.crm.exception.CustomerException;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    PaginationVO<Customer> getPageList(Map<String, Object> map);

    Map<String, Object> saveCustomer(Customer customer) throws CustomerException;

    boolean removeCustomerArray(String[] customerIdArray) throws CustomerException;

    List<String> getCustomerName(String name);

}
