package com.platform.service.system;

import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.platform.entity.system.DeviceAlarmInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/13.
 */

public interface DeviceAlarmInfoService {

    /**
     * 分页查询
     * @param params
     * @param pagerInfo
     * @return
     */
    public ServiceResult<Map<String, Object>> searchDeviceAlarmInfos(Map<String, Object> params, PagerInfo pagerInfo);

    /**
     * getById
     * @param id
     */
    public ServiceResult<DeviceAlarmInfo> getById(Long id);


    /**
     * 创建
     * @param deviceAlarmInfo
     */
    public ServiceResult<DeviceAlarmInfo> createDeviceAlarmInfo(DeviceAlarmInfo deviceAlarmInfo);

    /**
     * 更新
     * @param deviceAlarmInfo
     */
    public ServiceResult<DeviceAlarmInfo> updateDeviceAlarmInfo(DeviceAlarmInfo deviceAlarmInfo);

    /**
     * 查询
     * @param params
     * @return
     */
    public ServiceResult<List<DeviceAlarmInfo>> searchDeviceAlarmInfosByCondition(Map<String, Object> params);

   
}
