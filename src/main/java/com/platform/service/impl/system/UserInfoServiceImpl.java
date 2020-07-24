package com.platform.service.impl.system;

import com.platform.dao.system.UserInfoDao;
import com.platform.entity.system.UserInfo;
import com.platform.service.system.UserInfoService;
import com.platform.util.PasswordUtil;
import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
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
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoDao userInfoDao;
   

    @Override
    public ServiceResult<Map<String, Object>> searchUserInfos(Map<String, Object> params, PagerInfo pagerInfo) {
        ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 记录总数
        int rowsCount = userInfoDao.queryCountBy(params);
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
        List<UserInfo> userInfos = userInfoDao.queryListBy(params);
        map.put("data", userInfos);
        map.put("total", rowsCount);
        result.setResult(map);
        return result;
    }

    @Override
    public ServiceResult<UserInfo> getById(Long id) {
        ServiceResult<UserInfo> executeResult = new ServiceResult<UserInfo>();
        executeResult.setResult(userInfoDao.getById(id));
        return executeResult;
    }

    @Override
    public ServiceResult<UserInfo> createUserInfo(UserInfo userInfo) {
        ServiceResult<UserInfo> executeResult = new ServiceResult<UserInfo>();
        UserInfo dbUserInfo = userInfoDao.getByUserName(userInfo.getUserName());
        if(dbUserInfo != null){
            executeResult.setError("", "该用户名已存在。");
            return executeResult;
        }
        userInfo.setCreatedAt(new Date());
        userInfo.setUpdatedAt(new Date());
        userInfoDao.insert(userInfo);
        executeResult.setResult(userInfo);
        return executeResult;
    }
    @Override
    public ServiceResult<UserInfo> updateUserInfo(UserInfo userInfo) {
        ServiceResult<UserInfo> executeResult = new ServiceResult<UserInfo>();
        UserInfo dbUserInfo = userInfoDao.getById(userInfo.getId());
        if(dbUserInfo == null){
            executeResult.setError("", "该用户不存在或已经被删除。");
            return executeResult;
        }
        dbUserInfo.setParentId(userInfo.getParentId());
        dbUserInfo.setParentNickName(userInfo.getParentNickName());
        dbUserInfo.setStatus(userInfo.getStatus());
        dbUserInfo.setPassword(userInfo.getPassword());
        dbUserInfo.setUpdatedBy(userInfo.getUpdatedBy());
        dbUserInfo.setUpdatedAt(new Date());
        userInfoDao.update(dbUserInfo);
        return executeResult;
    }

    @Override
    public ServiceResult<UserInfo> login(String userName, String password,
                                     String ipAddress) {
        ServiceResult<UserInfo> executeResult = new ServiceResult<UserInfo>();
        UserInfo userInfo = userInfoDao.getByUserName(userName);
        final String errorMsg = "用户名或密码错误";

        // 用户名和密码以及用户状态是否匹配
        if (userInfo == null) {
            executeResult.setError("", errorMsg);
            return executeResult;
        }
        if(UserInfo.StatusEnum.INIT.getStatus().equals(userInfo.getStatus())){
            executeResult.setError("", "用户处于审核状态，请审核通过后登录");
            return executeResult;
        }
        if (!userInfo.getPassword().equals(PasswordUtil.encrypt(password))) {
            executeResult.setError("", errorMsg);
            return executeResult;
        }
        executeResult.setResult(userInfo);
        return executeResult;
    }

    @Override
    public ServiceResult<List<UserInfo>> getAll() {
        ServiceResult<List<UserInfo>> executeResult = new ServiceResult<List<UserInfo>>();
        executeResult.setResult(userInfoDao.getAll());
        return executeResult;
    }

    @Override
    public ServiceResult<List<UserInfo>> getUserByRoleId(Long roleId) {
        ServiceResult<List<UserInfo>> executeResult = new ServiceResult<List<UserInfo>>();
        executeResult.setResult(userInfoDao.getUserByRoleId(roleId));
        return executeResult;
    }
}
