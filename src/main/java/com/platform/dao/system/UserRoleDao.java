package com.platform.dao.system;


import com.platform.entity.system.UserRole;
import com.platform.dao.AbstractDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by GaoJingFei on 2017/11/17.
 */
@Repository
public class UserRoleDao   extends AbstractDao {
    public List<UserRole> getByUserId(Long userId){
        return this.getSqlSession().selectList(getNamespacePrefix() + "getByUserId", userId);
    }

    public Integer insert(UserRole userRole){
        return this.getSqlSession().insert(getNamespacePrefix() + "insert", userRole);
    }

    public Integer deleteByUserId(Long userId){
        return this.getSqlSession().delete(getNamespacePrefix() + "deleteByUserId", userId);
    }
}