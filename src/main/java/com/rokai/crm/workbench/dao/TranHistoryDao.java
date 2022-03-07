package com.rokai.crm.workbench.dao;

import com.rokai.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryDao {

    int createHistory(TranHistory tranHistory);

    List<TranHistory> getTransactionHistoryInfo(String tranId);
}
