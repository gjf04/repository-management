package com.platform.service.system;

import com.platform.entity.system.UserRole;
import com.gao.common.ServiceResult;

import java.util.List;

/**
 * Created by GaoJingFei on 2017/11/13.
 */

public interface UserRoleService {
    /**
     * 根据userId获取
     * */
    public ServiceResult<List<UserRole>> getByUserId(Long userId);
    /**
     * 创建
     * */
    public ServiceResult<Boolean> createUserRole(UserRole userRole);
}
