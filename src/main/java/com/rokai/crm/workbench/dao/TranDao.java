package com.rokai.crm.workbench.dao;

import com.rokai.crm.workbench.domain.Tran;
import com.rokai.crm.workbench.domain.TranHistory;

import java.util.List;
import java.util.Map;

public interface TranDao {

    int createTran(Tran tran);

    List<Tran> getTransactionList(Map<String, Object> map);

    int getTransactionCount(Map<String, Object> map);

    Tran getDetailInfo(String tranId);

    int changeStage(Tran tran);

    List<Map<String, Object>> getChartsInfo();

    int getChartsCount();

}
