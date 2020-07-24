package com.platform.dao.system;

import com.platform.dao.AbstractDao;
import com.platform.entity.system.DeviceUploadData;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/17.
 */
@Repository
public class DeviceUploadDataDao extends AbstractDao {
    public DeviceUploadData getById(Long id){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }


    public List<DeviceUploadData> queryListBy(Map<String, Object> params){
        return this.getSqlSession().selectList(getNamespacePrefix()+"queryListBy", params);
    }

    public Integer queryCountBy(Map<String, Object> params){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"queryCountBy", params);
    }

    //新增
    public Integer insert(DeviceUploadData deviceUploadData){
        return this.getSqlSession().insert(getNamespacePrefix()+"insert", deviceUploadData);
    }

    //更新
    public Integer update(DeviceUploadData deviceUploadData){
        return this.getSqlSession().update(getNamespacePrefix()+"update", deviceUploadData);
    }

}