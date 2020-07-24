package com.platform.dao.system;


import com.platform.entity.system.UserDepartment;
import com.platform.dao.AbstractDao;
import org.springframework.stereotype.Repository;

/**
 * Created by GaoJingFei on 2017/11/17.
 */
@Repository
public class UserDepartmentDao   extends AbstractDao {
    public Integer insert(UserDepartment userDepartment){
        return this.getSqlSession().insert(getNamespacePrefix() + "insert", userDepartment);
    }

    public Integer deleteByUserId(Long userId){
        return this.getSqlSession().delete(getNamespacePrefix() + "deleteByUserId", userId);
    }
}