package com.platform.service.impl.system;

import com.platform.dao.system.UserDepartmentDao;
import com.platform.entity.system.UserDepartment;
import com.platform.service.system.UserDepartmentService;
import com.gao.common.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by GaoJingFei on 2017/11/13.
 */
@Slf4j
@Service
public class UserDepartmentServiceImpl implements UserDepartmentService {
    @Autowired
    private UserDepartmentDao userDepartmentDao;
    @Override
    public ServiceResult<Boolean> createUserDepartment(UserDepartment userDepartment){
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        userDepartmentDao.deleteByUserId(userDepartment.getUserId());
        Integer count = userDepartmentDao.insert(userDepartment);
        if(count <= 0){
            result.setResult(false);
            result.setError("", "保存失败");
            return result;
        }
        result.setResult(true);
        return result;
    }
}
