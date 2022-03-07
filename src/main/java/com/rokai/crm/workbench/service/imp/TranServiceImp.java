package com.rokai.crm.workbench.service.imp;

import com.rokai.crm.utils.SqlSessionUtil;
import com.rokai.crm.utils.UUIDUtil;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.dao.CustomerDao;
import com.rokai.crm.workbench.dao.TranDao;
import com.rokai.crm.workbench.dao.TranHistoryDao;
import com.rokai.crm.workbench.dao.TranRemarkDao;
import com.rokai.crm.workbench.domain.Customer;
import com.rokai.crm.workbench.domain.Tran;
import com.rokai.crm.workbench.domain.TranHistory;
import com.rokai.crm.workbench.domain.TranRemark;
import com.rokai.crm.workbench.service.TranService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TranServiceImp implements TranService {

    TranDao tranDao = SqlSessionUtil.getSqlSession().getMapper(TranDao.class);
    TranHistoryDao tranHistoryDao = SqlSessionUtil.getSqlSession().getMapper(TranHistoryDao.class);
    CustomerDao customerDao = SqlSessionUtil.getSqlSession().getMapper(CustomerDao.class);
    TranRemarkDao tranRemarkDao = SqlSessionUtil.getSqlSession().getMapper(TranRemarkDao.class);

    @Override
    public PaginationVO<Tran> getTransactionList(Map<String, Object> map) {

        PaginationVO<Tran> tranPaginationVO = new PaginationVO<>();

        List<Tran> tranList = tranDao.getTransactionList(map);
        tranPaginationVO.setDataList(tranList);

        int total = tranDao.getTransactionCount(map);
        tranPaginationVO.setTotal(total);

        return tranPaginationVO;
    }

    @Override
    public boolean saveTransaction(Tran tran, String customerName) {

        boolean flag = true;

        Customer customerIfExist = customerDao.getCustomerIfExist(customerName);
        String customerId = null;
        if (customerIfExist != null){
            customerId = customerIfExist.getId();
        }

        Customer customer = null;
        if (customerIfExist == null){
            customer = new Customer();

            customer.setId(UUIDUtil.getUUID());
            customerId = customer.getId();
            customer.setOwner(tran.getOwner());
            customer.setName(customerName);
            customer.setWebsite("");
            customer.setPhone("");
            customer.setCreateBy(tran.getCreateBy());
            customer.setCreateTime(tran.getCreateTime());
            customer.setContactSummary(tran.getContactSummary());
            customer.setNextContactTime(tran.getNextContactTime());
            customer.setDescription(tran.getDescription());
            customer.setAddress("");

            int createCustomerState = customerDao.createCustomer(customer);
            if (createCustomerState != 1){
                flag = false;
            }
        }

        tran.setCustomerId(customerId);
        int createTranState = tranDao.createTran(tran);
        if (createTranState != 1){
            flag = false;
        }

        TranHistory tranHistory = new TranHistory();
        tranHistory.setId(UUIDUtil.getUUID());
        tranHistory.setTranId(tran.getId());
        tranHistory.setCreateBy(tran.getCreateBy());
        tranHistory.setCreateTime(tran.getCreateTime());
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());

        int createHistoryState = tranHistoryDao.createHistory(tranHistory);
        if (createHistoryState != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Tran getDetailInfo(String tranId) {

        Tran tran = tranDao.getDetailInfo(tranId);
        return tran;
    }

    @Override
    public List<TranHistory> getTransactionHistoryInfo(String tranId) {

        List<TranHistory> tranHistoryList = tranHistoryDao.getTransactionHistoryInfo(tranId);

        return tranHistoryList;
    }

    @Override
    public boolean changeStage(Tran tran,TranHistory tranHistory) {

        boolean flag = true;

        int changeStageState = tranDao.changeStage(tran);
        if (changeStageState != 1){
            flag = false;
        }

        int createHistoryState = tranHistoryDao.createHistory(tranHistory);
        if (createHistoryState != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public List<TranRemark> getRemarkInfo(String tranId) {

        List<TranRemark> tranRemarkList = tranRemarkDao.getRemarkInfo(tranId);

        return tranRemarkList;
    }

    @Override
    public boolean saveRemark(TranRemark tranRemark) throws Exception {

        boolean flag = true;

        int saveState = tranRemarkDao.saveRemark(tranRemark);
        if (saveState != 1){
            throw new Exception("error 保存备注失败！");
        }

        return flag;
    }

    @Override
    public boolean editRemark(TranRemark tranRemark) {

        boolean flag = true;

        int editRemarkState = tranRemarkDao.editRemark(tranRemark);
        if (editRemarkState != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean deleteRemark(String remarkId) {

        boolean flag = true;

        int deleteState = tranRemarkDao.deleteRemark(remarkId);
        if (deleteState != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Map<String, Object> getChartsInfo() {

        Map<String,Object> map = new HashMap<>();

        List<Map<String,Object>> mapList =  tranDao.getChartsInfo();
        map.put("dataList",mapList);

        int total = tranDao.getChartsCount();
        map.put("total",total);

        return map;
    }

}
