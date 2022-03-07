package com.rokai.crm.workbench.service.imp;

import com.rokai.crm.exception.ClueException;
import com.rokai.crm.settings.dao.UserDao;
import com.rokai.crm.settings.domain.User;
import com.rokai.crm.utils.DateTimeUtil;
import com.rokai.crm.utils.SqlSessionUtil;
import com.rokai.crm.utils.UUIDUtil;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.dao.*;
import com.rokai.crm.workbench.domain.*;
import com.rokai.crm.workbench.service.ClueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueServiceImp implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);
    private CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    private ContactsDao contactsDao = SqlSessionUtil.getSqlSession().getMapper(ContactsDao.class);
    private ContactsRemarkDao contactsRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ContactsRemarkDao.class);
    private ClueActivityRelationDao clueActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ClueActivityRelationDao.class);
    private ContactsActivityRelationDao contactsActivityRelationDao = SqlSessionUtil.getSqlSession().getMapper(ContactsActivityRelationDao.class);
    private TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    private TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);

    @Override
    public List<User> getUserList() {

        List<User> userInfo = userDao.getUserInfo();
        return userInfo;
    }

    @Override
    public boolean saveClue(Clue clue) throws ClueException {

        boolean flag = true;
        if ("".equals(clue.getCompany())){
            flag = false;
            throw new ClueException("error 公司不能为空！");
        }

        if ("".equals(clue.getFullname())){
            flag = false;
            throw new ClueException("error 姓名不能为空！");
        }

        int state = clueDao.saveClue(clue);
        if (state != 1){
            flag = false;
            throw new ClueException("error 添加线索失败！");
        }

        return flag;
    }

    @Override
    public PaginationVO<Clue> pageList(Map<String, Object> map) {
        int total = clueDao.getClueTotal(map);

        List<Clue> clueList = clueDao.getAllClue(map);

        PaginationVO<Clue> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(clueList);

        return vo;
    }

    @Override
    public boolean deleteClueArray(String[] array) throws ClueException{
        boolean flag = true;

        //查出需要删除的备注数量，
        int amount1 = clueRemarkDao.getCountId(array);

        //删除备注，返回受到影响的条数，
        int amount2 = clueRemarkDao.deleteRemarkArray(array);
        if (amount2 != amount1){
            flag = false;
            throw new ClueException("error 删除线索信息失败！");
        }

        int amount3 = clueDao.deleteClueArray(array);
        if (amount3 != array.length){
            flag = false;
            throw new ClueException("error 删除线索信息失败！");
        }

        return flag;
    }

    @Override
    public Map<String,Object> modifyWin(String id) {

        Clue clue = clueDao.getIdClue(id);
        List<User> userInfo = userDao.getUserInfo();

        Map<String,Object> map = new HashMap<>();
        map.put("clueInfo",clue);
        map.put("userInfo",userInfo);

        return map;
    }

    @Override
    public boolean updateClue(Clue clue) {
        boolean flag = true;

        int amount = clueDao.updateClue(clue);
        if (amount < 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Clue detail(String id) {

        Clue clue = clueDao.detail(id);
        return clue;
    }

    @Override
    public Boolean saveClueRemark(ClueRemark clueRemark) {

        Boolean flag = true;

        if ("".equals(clueRemark.getNoteContent())){
            flag = false;
            return flag;
        }

        int state = clueRemarkDao.saveClueRemark(clueRemark);
        if (state != 1){
            flag = false;
            return flag;
        }

        return flag;
    }

    @Override
    public List<ClueRemark> loadClueRemark(String clueId) {

        List<ClueRemark> clueRemarkList = clueRemarkDao.loadClueRemark(clueId);
        return clueRemarkList;
    }

    @Override
    public boolean deleteClueRemark(String clueRemarkId) {

        boolean flag = true;

        int state = clueRemarkDao.deleteRemark(clueRemarkId);
        if (state != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean updateClueRemark(ClueRemark clueRemark) {
        boolean flag = true;

        int state = clueRemarkDao.updateClueRemark(clueRemark);
        if (state != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public List<Activity> loadClueActivityRelation(String clueId) {

        List<Activity> list = clueRemarkDao.loadClueActivityRelation(clueId);

        return list;
    }

    @Override
    public boolean deleteRelation(String relationId) throws Exception {

        boolean flag = true;

        int state = clueRemarkDao.deleteRelation(relationId);
        if (state != 1){
            flag = false;
            throw new ClueException("删除失败！");
        }

        return flag;
    }

    @Override
    public Map<String, Object> relation(String clueId,String[] activityIds) {

        Map<String,Object> map = new HashMap<>();

        ClueActivityRelation activityRelation = null;
        for (String aId:activityIds){
            activityRelation = new ClueActivityRelation();
            activityRelation.setId(UUIDUtil.getUUID());
            activityRelation.setClueId(clueId);
            activityRelation.setActivityId(aId);

            int state = clueRemarkDao.relation(activityRelation);
            if (state != 1){
                map.put("success",false);
                map.put("message","关联市场活动失败！");
                return map;
            }
        }

        map.put("success",true);
        map.put("message","关联市场活动成功~");
        return map;
    }

    @Override
    public List<Activity> getActivityList(String dimSearchFrame) {

        List<Activity> list = clueRemarkDao.getActivityList(dimSearchFrame);

        return list;
    }

    @Override
    public boolean convertFunction(Map<String, Object> map,Tran tran) {

        boolean flag = true;
        String createTime = DateTimeUtil.getSysTime();
        String owner = (String) map.get("owner");
        String createBy = (String) map.get("createBy");

        /**
         * 1.获取到线索id，通过线索id获取到线索对象。（线索对象中封装了线索的信息）
         */
        Clue clue = clueDao.getIdClue((String) map.get("clueId"));

        /**
         * 2.通过线索对象提取客户信息，当该客户不存在的时候，新建客户。（根据公司名称精准匹配，判断该客户是否存在！）
         */
        Customer customerInfo = customerDao.getCustomerIfExist(clue.getCompany());

        String customerId = null;
        if (customerInfo != null){
            customerId = customerInfo.getId();
        }

        Customer customer = null;
        if (customerInfo == null){
            customer = new Customer();
            customer.setId(UUIDUtil.getUUID());
            customerId = customer.getId();
            customer.setOwner(owner);
            customer.setName((String) map.get("company"));
            customer.setWebsite(clue.getWebsite());
            customer.setPhone(clue.getPhone());
            customer.setCreateBy(clue.getCreateBy());
            customer.setCreateTime(createTime);
            customer.setContactSummary(clue.getContactSummary());
            customer.setNextContactTime(clue.getNextContactTime());
            customer.setDescription(clue.getDescription());
            customer.setAddress(clue.getAddress());

            int createCustomerState = customerDao.createCustomer(customer);
            if (createCustomerState != 1){
                flag = false;
                throw new RuntimeException("Error 创建客户失败！");
            }
        }

        /**
         * 3.通过线索对象提取联系人信息，保存联系人。
         */
        Contacts contacts = new Contacts();
        contacts.setId(UUIDUtil.getUUID());
        contacts.setOwner(clue.getOwner());
        contacts.setSource(clue.getSource());
        contacts.setCustomerId(customerId);
        contacts.setFullname(clue.getFullname());
        contacts.setAppellation(clue.getAppellation());
        contacts.setEmail(clue.getEmail());
        contacts.setMphone(clue.getMphone());
        contacts.setJob(clue.getJob());
        contacts.setBirth("");
        contacts.setCreateBy(createBy);
        contacts.setCreateTime(createTime);
        contacts.setDescription(clue.getDescription());
        contacts.setContactSummary(clue.getContactSummary());
        contacts.setNextContactTime(clue.getNextContactTime());
        contacts.setAddress(clue.getAddress());

        int createContactsState = contactsDao.createContacts(contacts);
        if (createContactsState != 1){
            flag = false;
            throw new RuntimeException("Error 创建联系人失败！");
        }

        /**
         * 4.线索备注转换到客户备注以及联系人备注，
         */
        List<ClueRemark> clueRemarkList = clueRemarkDao.getClueRemark((String)map.get("clueId"));

        ContactsRemark contactsRemark = null;
        for (ClueRemark clueRemark:clueRemarkList){
            contactsRemark = new ContactsRemark();
            contactsRemark.setId(UUIDUtil.getUUID());
            contactsRemark.setNoteContent(clueRemark.getNoteContent());
            contactsRemark.setCreateBy(clueRemark.getCreateBy());
            contactsRemark.setCreateTime(createTime);
            contactsRemark.setEditFlag(clueRemark.getEditFlag());
            contactsRemark.setContactsId(contacts.getId());

            int createRemarkState = contactsRemarkDao.createRemark(contactsRemark);
            if (createRemarkState != 1){
                flag = false;
                throw new RuntimeException("Error 线索备注转换失败！");
            }
        }

        /**
         * 5."线索市场活动的关系" 转换到 "联系人和市场活动的关系"，
         */
        List<Activity> activityList = clueActivityRelationDao.getClueActivityRelation((String) map.get("clueId"));

        ContactsActivityRelation contactsActivityRelation = null;
        for (Activity activity:activityList){
            contactsActivityRelation = new ContactsActivityRelation();
            contactsActivityRelation.setId(UUIDUtil.getUUID());
            contactsActivityRelation.setContactsId(contacts.getId());
            contactsActivityRelation.setActivityId(activity.getId());

            int createRelationState = contactsActivityRelationDao.createRelation(contactsActivityRelation);
            if (createRelationState != 1){
                flag = false;
                throw new RuntimeException("Error 线索市场活动的关系转换失败！");
            }
        }

        /**
         * 6.如果有创建交易请求，创建一条交易。
         */
        String createDeal = (String) map.get("createDeal");
        if ("1".equals(createDeal)){
            tran.setCustomerId(customerId);
            tran.setContactsId(contacts.getId());
            tran.setCreateTime(createTime);

            int createTranState = tranDao.createTran(tran);
            if (createTranState != 1){
                flag = false;
                throw new RuntimeException("Error 创建交易失败！");
            }else {

                /**
                 * 7.如果创建了交易，则创建一条该交易的交易历史记录。
                 */

                TranHistory tranHistory = new TranHistory();

                tranHistory.setId(UUIDUtil.getUUID());
                tranHistory.setStage(tran.getStage());
                tranHistory.setMoney(tran.getMoney());
                tranHistory.setExpectedDate(tran.getExpectedDate());
                tranHistory.setCreateTime(createTime);
                tranHistory.setCreateBy(createBy);
                tranHistory.setTranId(tran.getId());

                int createHistoryState = tranHistoryDao.createHistory(tranHistory);
                if (createHistoryState != 1){
                    flag = false;
                    throw new RuntimeException("Error 创建交易历史记录失败！");
                }
            }
        }

        /**
         * 8.删除线索备注，
         */
        int deleteRemarkAmount = clueRemarkDao.getDeleteRemarkAmount((String) map.get("clueId"));
        int deleteRemarkArrayState = clueRemarkDao.deleteRemarkClueId((String) map.get("clueId"));

        if (deleteRemarkAmount != deleteRemarkArrayState){
            flag = false;
            throw new RuntimeException("Error 删除线索备注失败！");
        }

        /**
         * 9.删除线索和市场活动的关系，
         */
        int deleteRelationAmount = clueActivityRelationDao.getDeleteRelationAmount((String) map.get("clueId"));
        int deleteRelationState = clueActivityRelationDao.deleteRelation((String) map.get("clueId"));

        if (deleteRelationAmount != deleteRelationState){
            flag = false;
            throw new RuntimeException("Error 删除线索和市场活动的关系失败！");
        }

        /**
         * 10.删除线索
         */
        int clueId = clueDao.deleteClue((String) map.get("clueId"));
        if (clueId != 1){
            flag = false;
            throw new RuntimeException("Error 删除线索失败！");
        }

        return flag;
    }

}
