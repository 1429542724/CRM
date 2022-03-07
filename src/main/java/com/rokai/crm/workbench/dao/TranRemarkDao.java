package com.rokai.crm.workbench.dao;

import com.rokai.crm.workbench.domain.TranRemark;

import java.util.List;

public interface TranRemarkDao {

    List<TranRemark> getRemarkInfo(String tranId);

    int saveRemark(TranRemark tranRemark);

    int editRemark(TranRemark tranRemark);

    int deleteRemark(String remarkId);
}
