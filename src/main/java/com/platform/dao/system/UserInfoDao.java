package com.platform.dao.system;

import com.platform.entity.system.UserInfo;
import com.platform.dao.AbstractDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/17.
 */
@Repository
public class UserInfoDao extends AbstractDao {
    public UserInfo getById(Long id){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }

    public UserInfo getByUserName(String userName){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getByUserName", userName);
    }

    public List<UserInfo> queryListBy(Map<String, Object> params){
        return this.getSqlSession().selectList(getNamespacePrefix()+"queryListBy", params);
    }

    public Integer queryCountBy(Map<String, Object> params){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"queryCountBy", params);
    }

    //新增
    public Integer insert(UserInfo userInfo){
        return this.getSqlSession().insert(getNamespacePrefix()+"insert", userInfo);
    }

    //更新
    public Integer update(UserInfo userInfo){
        return this.getSqlSession().update(getNamespacePrefix()+"update", userInfo);
    }

    public List<UserInfo> getAll(){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getAll");
    }

    public List<UserInfo> getUserByRoleId(Long roleId){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getUserByRoleId", roleId);
    }
}