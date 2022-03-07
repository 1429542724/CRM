package com.rokai.crm.workbench.service;

import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.domain.Tran;
import com.rokai.crm.workbench.domain.TranHistory;
import com.rokai.crm.workbench.domain.TranRemark;

import java.util.List;
import java.util.Map;

public interface TranService {

    PaginationVO<Tran> getTransactionList(Map<String, Object> map);

    boolean saveTransaction(Tran tran, String customerName);

    Tran getDetailInfo(String tranId);

    List<TranHistory> getTransactionHistoryInfo(String tranId);

    boolean changeStage(Tran tran,TranHistory tranHistory);

    List<TranRemark> getRemarkInfo(String tranId);

    boolean saveRemark(TranRemark tranRemark) throws Exception;

    boolean editRemark(TranRemark tranRemark);

    boolean deleteRemark(String remarkId);

    Map<String, Object> getChartsInfo();

}
