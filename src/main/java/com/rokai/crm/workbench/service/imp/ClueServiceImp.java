package com.rokai.crm.workbench.service.imp;

import com.rokai.crm.exception.ClueException;
import com.rokai.crm.settings.dao.UserDao;
import com.rokai.crm.settings.domain.User;
import com.rokai.crm.utils.SqlSessionUtil;
import com.rokai.crm.vo.PaginationVO;
import com.rokai.crm.workbench.dao.ClueDao;
import com.rokai.crm.workbench.dao.ClueRemarkDao;
import com.rokai.crm.workbench.domain.Clue;
import com.rokai.crm.workbench.domain.ClueRemark;
import com.rokai.crm.workbench.service.ClueService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClueServiceImp implements ClueService {

    private ClueDao clueDao = SqlSessionUtil.getSqlSession().getMapper(ClueDao.class);
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);
    private ClueRemarkDao clueRemarkDao = SqlSessionUtil.getSqlSession().getMapper(ClueRemarkDao.class);

    @Override
    public List<User> getUserList() {

        List<User> userInfo = userDao.getUserInfo();
        return userInfo;
    }

    @Override
    public boolean saveClue(Clue clue) throws ClueException {

        boolean flag = true;
        if ("".equals(clue.getCompany())){
            flag = false;
            throw new ClueException("error 公司不能为空！");
        }

        if ("".equals(clue.getFullname())){
            flag = false;
            throw new ClueException("error 姓名不能为空！");
        }

        int state = clueDao.saveClue(clue);
        if (state != 1){
            flag = false;
            throw new ClueException("error 添加线索失败！");
        }

        return flag;
    }

    @Override
    public PaginationVO<Clue> pageList(Map<String, Object> map) {
        int total = clueDao.getClueTotal(map);

        List<Clue> clueList = clueDao.getAllClue(map);

        PaginationVO<Clue> vo = new PaginationVO<>();
        vo.setTotal(total);
        vo.setDataList(clueList);

        return vo;
    }

    @Override
    public boolean deleteClue(String[] array) throws ClueException{
        boolean flag = true;

        //查出需要删除的备注数量，
        int amount1 = clueRemarkDao.getCountId(array);

        //删除备注，返回受到影响的条数，
        int amount2 = clueRemarkDao.deleteRemark(array);
        if (amount2 != amount1){
            flag = false;
            throw new ClueException("error 删除线索信息失败！");
        }

        int amount3 = clueDao.deleteClue(array);
        if (amount3 != array.length){
            flag = false;
            throw new ClueException("error 删除线索信息失败！");
        }

        return flag;
    }

    @Override
    public Map<String,Object> modifyWin(String id) {

        Clue clue = clueDao.getIdClue(id);
        List<User> userInfo = userDao.getUserInfo();

        Map<String,Object> map = new HashMap<>();
        map.put("clueInfo",clue);
        map.put("userInfo",userInfo);

        return map;
    }

    @Override
    public boolean updateClue(Clue clue) {
        boolean flag = true;

        int amount = clueDao.updateClue(clue);
        if (amount < 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public Clue detail(String id) {

        Clue clue = clueDao.detail(id);
        return clue;
    }

    @Override
    public Boolean saveClueRemark(ClueRemark clueRemark) {

        Boolean flag = true;

        if ("".equals(clueRemark.getNoteContent())){
            flag = false;
            return flag;
        }

        int state = clueRemarkDao.saveClueRemark(clueRemark);
        if (state != 1){
            flag = false;
            return flag;
        }

        return flag;
    }

    @Override
    public List<ClueRemark> loadClueRemark(String clueId) {

        List<ClueRemark> clueRemarkList = clueRemarkDao.loadClueRemark(clueId);
        return clueRemarkList;
    }

    @Override
    public boolean deleteClueRemark(String clueRemarkId) {

        boolean flag = true;

        int state = clueRemarkDao.deleteRemarkD(clueRemarkId);
        if (state != 1){
            flag = false;
        }

        return flag;
    }

    @Override
    public boolean updateClueRemark(ClueRemark clueRemark) {
        boolean flag = true;

        int state = clueRemarkDao.updateClueRemark(clueRemark);
        if (state != 1){
            flag = false;
        }

        return flag;
    }

}
