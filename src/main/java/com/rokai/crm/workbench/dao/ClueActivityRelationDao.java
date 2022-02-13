package com.rokai.crm.workbench.dao;

import com.rokai.crm.workbench.domain.Activity;

import java.util.List;

public interface ClueActivityRelationDao {

    List<Activity> getClueActivityRelation(String clueId);

    int deleteRelation(String clueId);

    int getDeleteRelationAmount(String clueId);
}
