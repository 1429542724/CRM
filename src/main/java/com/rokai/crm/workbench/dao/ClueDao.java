package com.rokai.crm.workbench.dao;

import com.rokai.crm.workbench.domain.Clue;

import java.util.List;
import java.util.Map;

public interface ClueDao {

    int saveClue(Clue clue);

    int getClueTotal(Map<String, Object> map);

    List<Clue> getAllClue(Map<String, Object> map);

    int deleteClue(String[] array);

    Clue getIdClue(String id);

    int updateClue(Clue clue);

    Clue detail(String id);
}
