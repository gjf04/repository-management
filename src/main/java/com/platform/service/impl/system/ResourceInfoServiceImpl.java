package com.platform.service.impl.system;

import com.platform.dao.system.ResourceInfoDao;
import com.platform.entity.system.ResourceInfo;
import com.platform.service.system.ResourceInfoService;
import com.gao.common.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/13.
 */
@Slf4j
@Service
public class ResourceInfoServiceImpl implements ResourceInfoService {
    @Autowired
    private ResourceInfoDao resourceInfoDao;

    @Override
    public List<ResourceInfo> getAll() {
        return resourceInfoDao.getAll();
    }

    @Override
    public ServiceResult<List<ResourceInfo>> getByUserId(Long userId){
        ServiceResult<List<ResourceInfo>> result = new ServiceResult<List<ResourceInfo>>();
        result.setResult(resourceInfoDao.getByUserId(userId));
        return result;
    }

    @Override
    public Map<String, String> getButtonCodeByUserId(Long userId){
        List<String> list = resourceInfoDao.getButtonCodeByUserId(userId);
        Map<String, String> map = new HashMap<String, String>();
        for(String s : list){
            map.put(s, s);
        }
        return map;
    }

    @Override
    public Map<String, String> getModuleCodeByUserId(Long userId){
        List<String> list = resourceInfoDao.getModuleCodeByUserId(userId);
        Map<String, String> map = new HashMap<String, String>();
        for(String s : list){
            map.put(s, s);
        }
        return map;
    }
}
