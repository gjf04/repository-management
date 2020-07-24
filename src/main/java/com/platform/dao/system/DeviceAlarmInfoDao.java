package com.platform.dao.system;

import com.platform.dao.AbstractDao;
import com.platform.entity.system.DeviceAlarmInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/17.
 */
@Repository
public class DeviceAlarmInfoDao extends AbstractDao {
    public DeviceAlarmInfo getById(Long id){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }


    public List<DeviceAlarmInfo> queryListBy(Map<String, Object> params){
        return this.getSqlSession().selectList(getNamespacePrefix()+"queryListBy", params);
    }

    public Integer queryCountBy(Map<String, Object> params){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"queryCountBy", params);
    }

    public List<DeviceAlarmInfo> queryListByCondition(Map<String, Object> params){
        return this.getSqlSession().selectList(getNamespacePrefix()+"queryListByCondition", params);
    }

    //新增
    public Integer insert(DeviceAlarmInfo deviceAlarmInfo){
        return this.getSqlSession().insert(getNamespacePrefix()+"insert", deviceAlarmInfo);
    }

    //更新
    public Integer update(DeviceAlarmInfo deviceAlarmInfo){
        return this.getSqlSession().update(getNamespacePrefix()+"update", deviceAlarmInfo);
    }

}