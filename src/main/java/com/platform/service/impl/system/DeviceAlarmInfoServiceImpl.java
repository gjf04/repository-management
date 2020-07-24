package com.platform.service.impl.system;

import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.platform.dao.system.DeviceAlarmInfoDao;
import com.platform.entity.system.DeviceAlarmInfo;
import com.platform.service.system.DeviceAlarmInfoService;
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
public class DeviceAlarmInfoServiceImpl implements DeviceAlarmInfoService {
    @Autowired
    private DeviceAlarmInfoDao deviceAlarmInfoDao;
   

    @Override
    public ServiceResult<Map<String, Object>> searchDeviceAlarmInfos(Map<String, Object> params, PagerInfo pagerInfo) {
        ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 记录总数
        int rowsCount = deviceAlarmInfoDao.queryCountBy(params);
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
        List<DeviceAlarmInfo> deviceAlarmInfos = deviceAlarmInfoDao.queryListBy(params);
        map.put("data", deviceAlarmInfos);
        map.put("total", rowsCount);
        result.setResult(map);
        return result;
    }

    @Override
    public ServiceResult<DeviceAlarmInfo> getById(Long id) {
        ServiceResult<DeviceAlarmInfo> executeResult = new ServiceResult<DeviceAlarmInfo>();
        executeResult.setResult(deviceAlarmInfoDao.getById(id));
        return executeResult;
    }

    @Override
    public ServiceResult<DeviceAlarmInfo> createDeviceAlarmInfo(DeviceAlarmInfo deviceAlarmInfo) {
        ServiceResult<DeviceAlarmInfo> executeResult = new ServiceResult<DeviceAlarmInfo>();
        deviceAlarmInfo.setCreatedAt(new Date());
        deviceAlarmInfo.setUpdatedAt(new Date());
        deviceAlarmInfoDao.insert(deviceAlarmInfo);
        executeResult.setResult(deviceAlarmInfo);
        return executeResult;
    }
    @Override
    public ServiceResult<DeviceAlarmInfo> updateDeviceAlarmInfo(DeviceAlarmInfo deviceAlarmInfo) {
        ServiceResult<DeviceAlarmInfo> executeResult = new ServiceResult<DeviceAlarmInfo>();
        DeviceAlarmInfo dbDeviceAlarmInfo = deviceAlarmInfoDao.getById(deviceAlarmInfo.getId());
        if(dbDeviceAlarmInfo == null){
            executeResult.setError("", "设备报警数据不存在或已经被删除。");
            return executeResult;
        }
        deviceAlarmInfo.setUpdatedAt(new Date());
        deviceAlarmInfoDao.update(deviceAlarmInfo);
        return executeResult;
    }

    @Override
    public ServiceResult<List<DeviceAlarmInfo>> searchDeviceAlarmInfosByCondition(Map<String, Object> params) {
        ServiceResult<List<DeviceAlarmInfo>> result = new ServiceResult<List<DeviceAlarmInfo>>();
        List<DeviceAlarmInfo> deviceAlarmInfos = deviceAlarmInfoDao.queryListByCondition(params);
        result.setResult(deviceAlarmInfos);
        return result;
    }

}
