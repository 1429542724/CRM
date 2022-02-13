package com.rokai.crm.workbench.dao;

import com.rokai.crm.workbench.domain.Customer;

public interface CustomerDao {

    Customer getCustomerIfExist(String company);

    int createCustomer(Customer customer);
}
