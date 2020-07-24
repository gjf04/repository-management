package com.platform.service.impl.system;

import com.platform.dao.system.RoleDao;
import com.platform.entity.system.Role;
import com.platform.service.system.RoleService;
import com.gao.common.PagerInfo;
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
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public ServiceResult<Role> getByName(String name) {
        ServiceResult<Role> result = new ServiceResult<Role>();
        result.setResult(roleDao.getByName(name));
        return result;
    }

    @Override
    public ServiceResult<Map<String, Object>> getRoleList(Map<String, Object> paramMap, PagerInfo pagerInfo) {
        ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 记录总数
        int rowsCount = roleDao.getRoleListCount(paramMap);
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
        paramMap.put("start", start);
        paramMap.put("size", size);
        List<Role> roles = roleDao.getRoleList(paramMap);
        map.put("data", roles);
        map.put("total", rowsCount);
        result.setResult(map);
        return result;
    }

    @Override
    public ServiceResult<Integer> insert(Role role) {
        ServiceResult<Integer> result = new ServiceResult<Integer>();
        result.setResult(roleDao.insert(role));

        return result;
    }

    @Override
    public List<Role> getAll() {
        return roleDao.getAll();
    }

    @Override
    public List<Role> getUserRoles(Long userId) {
        return roleDao.getUserRoles(userId);
    }
}
