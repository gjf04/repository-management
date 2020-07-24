package com.platform.dao.system;

import com.platform.dao.AbstractDao;
import com.platform.entity.system.DeviceInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/17.
 */
@Repository
public class DeviceInfoDao extends AbstractDao {
    public DeviceInfo getById(Long id){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }

    public DeviceInfo getBySerialNo(String serialNo){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getBySerialNo", serialNo);
    }


    public List<DeviceInfo> queryListBy(Map<String, Object> params){
        return this.getSqlSession().selectList(getNamespacePrefix()+"queryListBy", params);
    }

    public Integer queryCountBy(Map<String, Object> params){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"queryCountBy", params);
    }

    //新增
    public Integer insert(DeviceInfo deviceInfo){
        return this.getSqlSession().insert(getNamespacePrefix()+"insert", deviceInfo);
    }

    //更新
    public Integer update(DeviceInfo deviceInfo){
        return this.getSqlSession().update(getNamespacePrefix()+"update", deviceInfo);
    }

}