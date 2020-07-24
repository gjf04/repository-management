package com.platform.service.impl.system;

import com.platform.dao.system.UserRoleDao;
import com.platform.entity.system.UserRole;
import com.platform.service.system.UserRoleService;
import com.gao.common.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by GaoJingFei on 2017/11/13.
 */
@Slf4j
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public ServiceResult<List<UserRole>> getByUserId(Long userId){
        ServiceResult<List<UserRole>> result = new ServiceResult<List<UserRole>>();
        result.setResult(userRoleDao.getByUserId(userId));
        return result;
    }

    @Override
    public ServiceResult<Boolean> createUserRole(UserRole userRole){
        ServiceResult<Boolean> result = new ServiceResult<Boolean>();
        userRoleDao.deleteByUserId(userRole.getUserId());
        Integer count = userRoleDao.insert(userRole);
        if(count <= 0){
            result.setResult(false);
            result.setError("", "保存失败");
            return result;
        }
        result.setResult(true);
        return result;
    }
}
