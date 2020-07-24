package com.platform.service.system;

import com.platform.entity.system.Department;
import com.platform.entity.util.ComboBox;
import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/13.
 */

public interface DepartmentService {
    /**
     * 分页查询部门信息
     * @param params
     * @param pagerInfo
     * @return
     */
    public ServiceResult<Map<String, Object>> searchDepartments(Map<String, Object> params, PagerInfo pagerInfo);
    /**
     * 根据用户编码查询所在部门
     */
    public Department searchDepartmentByUserId(Long userId);
    /**
     * 创建部门
     * @param department
     */
    public ServiceResult<Department> createDepartment(Department department);

    /**
     * 更新部门信息
     * @param department
     */
    public ServiceResult<Department> updateDepartment(Department department);

    /**
     * 删除部门
     * @param departmentId
     * @return
     */
    public ServiceResult<Department> deleteDepartment(Long departmentId);
    /**
     * 根据code获取部门名称
     *
     * @author zhaobing
     * @since 2014-10-23
     * @param code
     * @return
     */
    public String getNameByCode(String code);

    /**
     * 通过id获取部门信息
     * @param departmentId
     * @return
     */
    public Department getDepartmentById(Long departmentId);

    /**
     * 根据部门编码获取部门
     * @param code
     * @return
     */
    public Department getDepartmentByCode(String code);
    /**
     * 根据code获取部门id
     *
     * @author GaoJingFei
     * @since 2014-11-3
     * @param code
     * @return
     */
    public String getIdByCode(String code);

    /**
     * 获取部门的子部门
     * @param departmentId
     * @return
     */
    public List<Department> getChilden(Long departmentId);

    /**
     * 获取系统根部门
     * @return
     */
    public List<Department> getRoot();

    /**
     * 获取系统中所有的部门列表
     * @return
     */
    public List<Department> getAll();

    /**
     * 获取用户所在的部门列表
     * @param userId
     * @return
     */
    public List<Department> getUserDepartments(Long userId);
    /**
     * 获取部门中相关的下拉列表方法
     * @param type
     *
     */
    public List<ComboBox> getDepartmentCombox(String type);

    /**
     * 根据用户获取所在的部门
     *
     * @author zhaobing
     * @since 2014-10-29
     * @param name
     * @return
     */
    public Department getDepartmentByUser(String name);

    public List<Department> getDepartmentComboxByParentCode(String parentCode);


}
