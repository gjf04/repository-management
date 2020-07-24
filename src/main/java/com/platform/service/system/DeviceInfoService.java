package com.platform.service.system;

import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.platform.entity.system.DeviceInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/13.
 */

public interface DeviceInfoService {

    /**
     * 分页查询
     * @param params
     * @param pagerInfo
     * @return
     */
    public ServiceResult<Map<String, Object>> searchDeviceInfos(Map<String, Object> params, PagerInfo pagerInfo);

    /**
     * getById
     * @param id
     */
    public ServiceResult<DeviceInfo> getById(Long id);

    /**
     * getBySerialNo
     * @param serialNo
     */
    public ServiceResult<DeviceInfo> getBySerialNo(String serialNo);


    /**
     * 创建
     * @param deviceInfo
     */
    public ServiceResult<DeviceInfo> createDeviceInfo(DeviceInfo deviceInfo);

    /**
     * 更新
     * @param deviceInfo
     */
    public ServiceResult<DeviceInfo> updateDeviceInfo(DeviceInfo deviceInfo);

   
}
