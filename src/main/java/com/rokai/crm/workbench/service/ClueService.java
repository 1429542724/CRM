package com.rokai.crm.workbench.service;

import com.rokai.crm.exception.ClueException;
import com.rokai.crm.settings.domain.User;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.domain.Clue;
import com.rokai.crm.workbench.domain.ClueRemark;

import java.util.List;
import java.util.Map;

public interface ClueService {

    List<User> getUserList();

    boolean saveClue(Clue clue) throws ClueException;

    PaginationVO<Clue> pageList(Map<String, Object> map);

    boolean deleteClue(String[] array) throws ClueException;

    Map<String,Object> modifyWin(String id);

    boolean updateClue(Clue clue);

    Clue detail(String id);

    Boolean saveClueRemark(ClueRemark clueRemark);

    List<ClueRemark> loadClueRemark(String clueId);

    boolean deleteClueRemark(String clueRemarkId);

    boolean updateClueRemark(ClueRemark clueRemark);
}
