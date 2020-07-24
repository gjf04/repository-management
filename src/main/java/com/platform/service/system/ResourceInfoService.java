package com.platform.service.system;

import com.platform.entity.system.ResourceInfo;
import com.gao.common.ServiceResult;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/13.
 */

public interface ResourceInfoService {
    /**
     * 获取系统中所有
     * @return
     */
    public List<ResourceInfo> getAll();

    /**
     * 根据userId获取
     * */
    public ServiceResult<List<ResourceInfo>> getByUserId(Long userId);

    /**
     * 根据userId获取按钮code
     * */
    public Map<String, String> getButtonCodeByUserId(Long userId);

    /**
     * 根据userId获取模块code
     * */
    public Map<String, String> getModuleCodeByUserId(Long userId);
}
