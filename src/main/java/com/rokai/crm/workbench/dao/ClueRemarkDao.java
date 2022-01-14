package com.rokai.crm.workbench.dao;

import com.rokai.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    int getCountId(String[] array);

    int deleteRemark(String[] array);

    int saveClueRemark(ClueRemark clueRemark);

    List<ClueRemark> loadClueRemark(String clueId);

    int deleteRemarkD(String clueRemarkId);

    int updateClueRemark(ClueRemark clueRemark);
}
