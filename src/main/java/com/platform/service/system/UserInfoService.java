package com.platform.service.system;

import com.platform.entity.system.UserInfo;
import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/13.
 */

public interface UserInfoService {

    /**
     * 分页查询
     * @param params
     * @param pagerInfo
     * @return
     */
    public ServiceResult<Map<String, Object>> searchUserInfos(Map<String, Object> params, PagerInfo pagerInfo);

    /**
     * getById
     * @param id
     */
    public ServiceResult<UserInfo> getById(Long id);


    /**
     * 创建
     * @param userInfo
     */
    public ServiceResult<UserInfo> createUserInfo(UserInfo userInfo);

    /**
     * 更新
     * @param userInfo
     */
    public ServiceResult<UserInfo> updateUserInfo(UserInfo userInfo);

    /**
     * 登录
     * @param userName
     * @param password
     */
    public ServiceResult<UserInfo> login(String userName, String password,
                                         String ipAddress);

    /**
     * 查询所有用户
     * @return
     */
    public ServiceResult<List<UserInfo>> getAll();

    /**
     * 查询特定角色的用户
     * @return
     */
    public ServiceResult<List<UserInfo>> getUserByRoleId(Long roleId);
}
