package com.platform.dao.system;


import com.platform.entity.system.Role;
import com.platform.dao.AbstractDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/17.
 */
@Repository
public class RoleDao   extends AbstractDao {

    public Role getByName(String name){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getByName", name);
    }

    public List<Role> getRoleList(Map<String, Object> paramMap){
        return this.getSqlSession().selectList(getNamespacePrefix() + "getRoleList", paramMap);
    }

    public Integer getRoleListCount(Map<String, Object> params){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getRoleListCount", params);
    }

    public Integer insert(Role role){
        return this.getSqlSession().insert(getNamespacePrefix() + "insert", role);
    }

    /**
     * 获取所有
     *
     */
    public List<Role> getAll(){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getAll");
    }

    /**
     * 获取用户的角色信息
     * @param userId
     * @return
     */
    public List<Role> getUserRoles(Long userId){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getUserRoles", userId);
    }
}