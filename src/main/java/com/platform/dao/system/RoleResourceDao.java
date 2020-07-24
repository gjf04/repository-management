package com.platform.dao.system;

import com.platform.entity.system.RoleResource;
import com.platform.dao.AbstractDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by GaoJingFei on 2017/11/17.
 */
@Repository
public class RoleResourceDao   extends AbstractDao {
	

	public List<RoleResource> selectAllByRoleId(Long roleId){
		return this.getSqlSession().selectList(getNamespacePrefix() + "selectAllByRoleId", roleId);
	}
	
    public Integer insert(RoleResource roleResource){
        return this.getSqlSession().insert(getNamespacePrefix() + "insert", roleResource);
    }
    
    public Integer deleteByRoleId(Long roleId){
    	return this.getSqlSession().delete(getNamespacePrefix() + "deleteByRoleId", roleId);
    }

    public List<RoleResource> getByRoleIdAndResourceId(RoleResource roleResource){
        return this.getSqlSession().selectList(getNamespacePrefix() + "getByRoleIdAndResourceId", roleResource);
    }
}