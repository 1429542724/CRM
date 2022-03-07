package com.rokai.crm.workbench.service.imp;

import com.rokai.crm.utils.SqlSessionUtil;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.dao.ContactsDao;
import com.rokai.crm.workbench.domain.Contacts;
import com.rokai.crm.workbench.service.ContactsService;

import java.util.List;
import java.util.Map;

public class ContactsServiceImp implements ContactsService {

    ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);

    @Override
    public List<Contacts> getContactsList(String contactsName) {

        List<Contacts> contactsList = contactsDao.getContactsList(contactsName);

        return contactsList;
    }

    @Override
    public PaginationVO<Contacts> getContactsPage(Map<String, Object> map) {

        PaginationVO<Contacts> paginationVO = new PaginationVO<>();

        List<Contacts> contactsList = contactsDao.getContactsPageList(map);
        paginationVO.setDataList(contactsList);

        int amount = contactsDao.getContactsPageCount(map);
        paginationVO.setTotal(amount);

        return paginationVO;
    }


}
