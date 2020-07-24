package com.platform.service.system;

import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.platform.entity.system.DeviceUploadData;

import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/13.
 */

public interface DeviceUploadDataService {

    /**
     * 分页查询
     * @param params
     * @param pagerInfo
     * @return
     */
    public ServiceResult<Map<String, Object>> searchDeviceUploadDatas(Map<String, Object> params, PagerInfo pagerInfo);

    /**
     * getById
     * @param id
     */
    public ServiceResult<DeviceUploadData> getById(Long id);


    /**
     * 创建
     * @param deviceUploadData
     */
    public ServiceResult<DeviceUploadData> createDeviceUploadData(DeviceUploadData deviceUploadData);

    /**
     * 更新
     * @param deviceUploadData
     */
    public ServiceResult<DeviceUploadData> updateDeviceUploadData(DeviceUploadData deviceUploadData);

   
}
