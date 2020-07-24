package com.platform.service.impl.system;

import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.platform.dao.system.DeviceUploadDataDao;
import com.platform.entity.system.DeviceUploadData;
import com.platform.service.system.DeviceUploadDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/13.
 */
@Slf4j
@Service
public class DeviceUploadDataServiceImpl implements DeviceUploadDataService {
    @Autowired
    private DeviceUploadDataDao deviceUploadDataDao;
   

    @Override
    public ServiceResult<Map<String, Object>> searchDeviceUploadDatas(Map<String, Object> params, PagerInfo pagerInfo) {
        ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 记录总数
        int rowsCount = deviceUploadDataDao.queryCountBy(params);
        int start = pagerInfo.getStart();
        int size = pagerInfo.getPageSize();
        if (rowsCount > 0) {
            int totalPage = (rowsCount + size - 1) / size;// 总页数
            int pageIndex = pagerInfo.getPageIndex();// 当前页码
            if (pageIndex > totalPage) {
                // 总页数作为当前页
                start = (totalPage - 1) * size;
            }
        }
        params.put("start", start);
        params.put("size", size);
        List<DeviceUploadData> deviceUploadDatas = deviceUploadDataDao.queryListBy(params);
        map.put("data", deviceUploadDatas);
        map.put("total", rowsCount);
        result.setResult(map);
        return result;
    }

    @Override
    public ServiceResult<DeviceUploadData> getById(Long id) {
        ServiceResult<DeviceUploadData> executeResult = new ServiceResult<DeviceUploadData>();
        executeResult.setResult(deviceUploadDataDao.getById(id));
        return executeResult;
    }

    @Override
    public ServiceResult<DeviceUploadData> createDeviceUploadData(DeviceUploadData deviceUploadData) {
        ServiceResult<DeviceUploadData> executeResult = new ServiceResult<DeviceUploadData>();
        deviceUploadData.setCreatedAt(new Date());
        deviceUploadData.setUpdatedAt(new Date());
        deviceUploadDataDao.insert(deviceUploadData);
        executeResult.setResult(deviceUploadData);
        return executeResult;
    }
    @Override
    public ServiceResult<DeviceUploadData> updateDeviceUploadData(DeviceUploadData deviceUploadData) {
        ServiceResult<DeviceUploadData> executeResult = new ServiceResult<DeviceUploadData>();
        DeviceUploadData dbDeviceUploadData = deviceUploadDataDao.getById(deviceUploadData.getId());
        if(dbDeviceUploadData == null){
            executeResult.setError("", "设备状态数据不存在或已经被删除。");
            return executeResult;
        }
        deviceUploadData.setUpdatedAt(new Date());
        deviceUploadDataDao.update(deviceUploadData);
        return executeResult;
    }



}
