package com.rokai.crm.workbench.dao;

import com.rokai.crm.workbench.domain.Activity;
import com.rokai.crm.workbench.domain.ClueActivityRelation;
import com.rokai.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkDao {

    int getCountId(String[] array);

    int deleteRemarkArray(String[] array);

    int saveClueRemark(ClueRemark clueRemark);

    List<ClueRemark> loadClueRemark(String clueId);

    int deleteRemark(String clueRemarkId);

    int updateClueRemark(ClueRemark clueRemark);

    List<Activity> loadClueActivityRelation(String clueId);

    int deleteRelation(String relationId);

    int relation(ClueActivityRelation activityRelation);

    List<Activity> getActivityList(String dimSearchFrame);

    List<ClueRemark> getClueRemark(String clueId);

    int getDeleteRemarkAmount(String clueId);

    int deleteRemarkClueId(String clueId);
}
