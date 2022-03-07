package com.rokai.crm.workbench.dao;

import com.rokai.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerDao {

    Customer getCustomerIfExist(String company);

    int createCustomer(Customer customer);

    List<Customer> getPageList(Map<String, Object> map);

    Integer getPageListCount(Map<String, Object> map);

    int removeCustomerArray(String[] customerIdArray);

    List<String> getCustomerName(String name);
}
