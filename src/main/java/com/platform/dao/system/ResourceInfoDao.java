package com.platform.dao.system;

import com.platform.entity.system.ResourceInfo;
import com.platform.dao.AbstractDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by GaoJingFei on 2017/11/17.
 */
@Repository
public class ResourceInfoDao extends AbstractDao {
    /**
     * 获取所有
     *
     */
    public List<ResourceInfo> getAll(){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getAll");
    }

    public List<ResourceInfo> getByUserId(Long userId){
        return this.getSqlSession().selectList(getNamespacePrefix() + "getByUserId", userId);
    }

    public List<String> getButtonCodeByUserId(Long userId){
        return this.getSqlSession().selectList(getNamespacePrefix() + "getButtonCodeByUserId", userId);
    }

    public List<String> getModuleCodeByUserId(Long userId){
        return this.getSqlSession().selectList(getNamespacePrefix() + "getModuleCodeByUserId", userId);
    }
}