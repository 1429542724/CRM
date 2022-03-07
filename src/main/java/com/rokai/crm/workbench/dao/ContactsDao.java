package com.rokai.crm.workbench.dao;

import com.rokai.crm.workbench.domain.Contacts;

import java.util.List;
import java.util.Map;

public interface ContactsDao {

    int createContacts(Contacts contacts);

    List<Contacts> getContactsList(String contactsName);

    List<Contacts> getContactsPageList(Map<String, Object> map);

    int getContactsPageCount(Map<String, Object> map);
}
