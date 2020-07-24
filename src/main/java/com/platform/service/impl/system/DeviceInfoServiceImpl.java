package com.platform.service.impl.system;

import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.platform.dao.system.DeviceInfoDao;
import com.platform.entity.system.DeviceInfo;
import com.platform.service.system.DeviceInfoService;
import com.platform.util.PasswordUtil;
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
public class DeviceInfoServiceImpl implements DeviceInfoService {
    @Autowired
    private DeviceInfoDao deviceInfoDao;
   

    @Override
    public ServiceResult<Map<String, Object>> searchDeviceInfos(Map<String, Object> params, PagerInfo pagerInfo) {
        ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 记录总数
        int rowsCount = deviceInfoDao.queryCountBy(params);
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
        List<DeviceInfo> deviceInfos = deviceInfoDao.queryListBy(params);
        map.put("data", deviceInfos);
        map.put("total", rowsCount);
        result.setResult(map);
        return result;
    }

    @Override
    public ServiceResult<DeviceInfo> getById(Long id) {
        ServiceResult<DeviceInfo> executeResult = new ServiceResult<DeviceInfo>();
        executeResult.setResult(deviceInfoDao.getById(id));
        return executeResult;
    }

    @Override
    public ServiceResult<DeviceInfo> getBySerialNo(String serialNo) {
        ServiceResult<DeviceInfo> executeResult = new ServiceResult<DeviceInfo>();
        executeResult.setResult(deviceInfoDao.getBySerialNo(serialNo));
        return executeResult;
    }

    @Override
    public ServiceResult<DeviceInfo> createDeviceInfo(DeviceInfo deviceInfo) {
        ServiceResult<DeviceInfo> executeResult = new ServiceResult<DeviceInfo>();
        deviceInfo.setCreatedAt(new Date());
        deviceInfo.setUpdatedAt(new Date());
        deviceInfoDao.insert(deviceInfo);
        executeResult.setResult(deviceInfo);
        return executeResult;
    }
    @Override
    public ServiceResult<DeviceInfo> updateDeviceInfo(DeviceInfo deviceInfo) {
        ServiceResult<DeviceInfo> executeResult = new ServiceResult<DeviceInfo>();
        DeviceInfo dbDeviceInfo = deviceInfoDao.getById(deviceInfo.getId());
        if(dbDeviceInfo == null){
            executeResult.setError("", "该设备不存在或已经被删除。");
            return executeResult;
        }
        deviceInfo.setUpdatedAt(new Date());
        deviceInfoDao.update(deviceInfo);
        return executeResult;
    }



}
