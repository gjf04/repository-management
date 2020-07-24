package com.platform.service.impl.system;

import com.platform.dao.system.DepartmentDao;
import com.platform.entity.system.Department;
import com.platform.entity.util.ComboBox;
import com.platform.service.system.DepartmentService;
import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by GaoJingFei on 2017/11/13.
 */
@Slf4j
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentDao departmentDao;

    @Override
    public ServiceResult<Map<String, Object>> searchDepartments(Map<String, Object> params, PagerInfo pagerInfo) {
        ServiceResult<Map<String, Object>> result = new ServiceResult<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        // 记录总数
        int rowsCount = departmentDao.queryCountBy(params);
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
        params.put("parentId", params.get("parentId"));
        List<Department> departments = departmentDao.queryListBy(params);
        map.put("data", departments);
        map.put("total", rowsCount);
        result.setResult(map);
        return result;
    }

    @Override
    public Department searchDepartmentByUserId(Long userId) {
        return departmentDao.searchDepartmentByUserId(userId);
    }

    @Override
    public ServiceResult<Department> createDepartment(Department department) {
        ServiceResult<Department> executeResult = new ServiceResult<Department>();
        if(isCodeExisted(department, executeResult) || isNameExisted(department, executeResult)){
            return executeResult;
        }
        department.setCreatedAt(new Date());
        department.setUpdatedAt(new Date());
        departmentDao.insert(department);
        executeResult.setResult(department);
        return executeResult;
    }
    @Override
    public ServiceResult<Department> updateDepartment(Department department) {
        ServiceResult<Department> executeResult = new ServiceResult<Department>();
        Department dbDepartment = departmentDao.getById(department.getId());
        if(dbDepartment == null){
            executeResult.setMessage("该部门不存在或已经被删除。");
            return executeResult;
        }
        // code重复检查
        if(!StringUtils.trim(department.getCode()).equals(dbDepartment.getCode())){
            if(isCodeExisted(department, executeResult)){
                return executeResult;
            }
        }
        // 名称重名检查
        if(!StringUtils.trim(department.getName()).equals(dbDepartment.getName())){
            if(isNameExisted(department, executeResult)){
                return executeResult;
            }
        }
        dbDepartment.setCode(StringUtils.trim(department.getCode()));
        dbDepartment.setName(StringUtils.trim(department.getName()));
        dbDepartment.setPrincipalUserId(department.getPrincipalUserId());
        dbDepartment.setPrincipalNickName(StringUtils.trim(department.getPrincipalNickName()));
        dbDepartment.setUpdatedBy(department.getUpdatedBy());
        dbDepartment.setUpdatedAt(new Date());
        departmentDao.update(dbDepartment);
        return executeResult;
    }
    private boolean isNameExisted(Department department, ServiceResult<Department> executeResult) {
        Department existedNameDepartment = departmentDao.getDepartmentByName(StringUtils.trim(department.getName()));
        if(existedNameDepartment != null){
            executeResult.setMessage("已经存在名称为[" + department.getName() + "]的部门。");
            return true;
        }
        return false;
    }
    private boolean isCodeExisted(Department department, ServiceResult<Department> executeResult) {
        Map<String,Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put("columnName", "code");
        parameterMap.put("columnValue", StringUtils.trim(department.getCode()));
        Department existedCodeDepartment = departmentDao.getDepartmentByCode(StringUtils.trim(department.getCode()));
        if(existedCodeDepartment != null){
            executeResult.setMessage("已经存在code为[" + department.getCode() + "]的部门。");
            return true;
        }
        return false;
    }
    @Override
    public ServiceResult<Department> deleteDepartment(Long departmentId) {
        ServiceResult<Department> executeResult = new ServiceResult<Department>();
        //是否关联用户
        Department department = departmentDao.getById(departmentId);
        if(department == null){
            return executeResult;
        }
//        List<User> users = userDao.getDepartmentUsers(departmentId);
//        if(!users.isEmpty()){
//            executeResult.addErrorMessage("该部门下还关联了"+users.size()+"个员工，不能删除。");
//            return executeResult;
//        }
//        //是否关联子节点
//        List<Department> children = getChilden(departmentId);
//        if(!children.isEmpty()){
//            executeResult.addErrorMessage("该部门下还设置了"+children.size()+"个下属部门，不能删除。");
//            return executeResult;
//        }
//        departmentDao.delete(departmentId);
        return executeResult;
    }

    @Override
    public List<Department> getUserDepartments(Long userId) {
        return departmentDao.getUserDepartments(userId);
    }
    @Override
    public Department getDepartmentById(Long departmentId) {
        return departmentDao.getById(departmentId);
    }
    @Override
    public List<Department> getChilden(Long departmentId) {
        return departmentDao.getChildren(departmentId);
    }
    @Override
    public List<Department> getRoot() {
        return departmentDao.getRoots();
    }
    @Override
    public List<Department> getAll() {
        return departmentDao.getAll();
    }

    /**
     * 获取部门中相关的下拉列表方法
     * @param type
     *
     */
    @Override
    public List<ComboBox> getDepartmentCombox(String type) {
        return convertToComboBox(departmentDao.getDepartmentCombox(type));
    }

    public List<Department> getDepartmentComboxByParentCode (String parentCode) {
        List<Department> returnList = new ArrayList<Department>();
        if(!StringUtils.isEmpty(parentCode)) {
            Department dep = departmentDao.getDepartmentByCode(parentCode);
            returnList = departmentDao.getChildren(dep.getId());
        }
        Department department = new Department();
        department.setCode("");
        department.setName("--请选择--");
        returnList.add(0,department);
        return returnList;
    }

    @Override
    public Department getDepartmentByUser(String name) {
        return departmentDao.getDepartmentByUser(name);
    }

    @Override
    public Department getDepartmentByCode(String code) {
        return departmentDao.getDepartmentByCode(code);
    }

    @Override
    public String getNameByCode(String code) {
        return departmentDao.getNameByCode(code);
    }

    @Override
    public String getIdByCode(String code) {
        return departmentDao.getIdByCode(code);
    }

    //将department转为combobox
    private static List<ComboBox> convertToComboBox(List<Department> list){
        List<ComboBox> cbList = new ArrayList<ComboBox>();
        for(Department d : list){
            ComboBox cb = new ComboBox();
            cb.setId(d.getCode());
            cb.setText(d.getName());
            cbList.add(cb);
        }
        ComboBox empty = new ComboBox();
        empty.setId("");
        empty.setText("--请选择--");
        cbList.add(0,empty);
        return cbList;
    }

}
