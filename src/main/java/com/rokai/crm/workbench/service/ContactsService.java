package com.rokai.crm.workbench.service;

import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.domain.Contacts;

import java.util.List;
import java.util.Map;

public interface ContactsService {

    List<Contacts> getContactsList(String contactsName);

    PaginationVO<Contacts> getContactsPage(Map<String, Object> map);

}
