package com.platform.service.system;
import com.platform.entity.system.UserDepartment;
import com.gao.common.ServiceResult;

/**
 * Created by GaoJingFei on 2017/11/13.
 */

public interface UserDepartmentService {
    /**
     * 创建
     * */
    public ServiceResult<Boolean> createUserDepartment(UserDepartment userDepartment);
}
