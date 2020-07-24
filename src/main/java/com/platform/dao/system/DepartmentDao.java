package com.platform.dao.system;

import com.platform.entity.system.Department;
import com.platform.entity.system.UserDepartment;
import com.platform.dao.AbstractDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by GaoJingFei on 2017/11/17.
 */
@Repository
public class DepartmentDao extends AbstractDao {
    public Department getById(Long id){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"getById", id);
    }

    public List<Department> queryListBy(Map<String, Object> params){
        return this.getSqlSession().selectList(getNamespacePrefix()+"queryListBy", params);
    }

    public Integer queryCountBy(Map<String, Object> params){
        return this.getSqlSession().selectOne(getNamespacePrefix()+"queryCountBy", params);
    }

    //新增
    public Integer insert(Department department){
        return this.getSqlSession().insert(getNamespacePrefix()+"insert", department);
    }

    //更新
    public Integer update(Department department){
        return this.getSqlSession().update(getNamespacePrefix()+"update", department);
    }

    /**
     * 获取所有部门
     *
     */
    public List<Department> getAll(){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getAll");
    }

    /**
     * 查询子部门
     * @param departmentId
     * @return
     */
    public List<Department> getChildren(Long departmentId){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getChildren", departmentId);
    }

    /**
     * 获取根节点部门 信息列表
     * @return
     */
    public List<Department> getRoots(){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getRoots");
    }

    /**
     * 获取用户所在的部门信息
     * @param userId
     * @return
     */
    public List<Department> getUserDepartments(Long userId){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getUserDepartments", userId);
    }

    /**
     * 删除用户关联的部门信息
     * @param userId
     */
    public Integer deleteUserDepartments(Long userId){
        return this.getSqlSession().update(getNamespacePrefix() + "update", userId);
    }

    /**
     * 将用户添加到部门中
     * @param userDepartment
     */
    public Integer addUserToDepartment(UserDepartment userDepartment){
        return this.getSqlSession().insert(getNamespacePrefix()+"addUserToDepartment", userDepartment);
    }

    /**
     * 获取部门中相关的下拉列表方法
     * @param type
     *
     */
    public List<Department> getDepartmentCombox(String type){
        return this.getSqlSession().selectList(getNamespacePrefix()+"getDepartmentCombox", type);
    }

    /**
     * 根据名称查询
     * @param name
     * @return
     */
    public Department getDepartmentByName(String name){
        return this.getSqlSession().selectOne(getNamespacePrefix() + "getDepartmentByName", name);
    }
    /**
     * 根据部门编码获取部门
     * @param code
     * @return
     */
    public Department getDepartmentByCode(String code){
        return this.getSqlSession().selectOne(getNamespacePrefix() + "getDepartmentByCode", code);
    }
    /**
     * 根据用户查询部门
     * @param userId
     * @return
     */
    public Department searchDepartmentByUserId(Long userId){
        return this.getSqlSession().selectOne(getNamespacePrefix() + "searchDepartmentByUserId", userId);
    }
    /**
     * 根据code获取部门名称
     * @param code
     * @return
     */
    public String getNameByCode(String code){
        return this.getSqlSession().selectOne(getNamespacePrefix() + "getNameByCode", code);
    }
    /**
     * 根据code获取部门id
     *
     * @author GaoJingFei
     * @since 2014-11-3
     * @param code
     * @return
     */
    public String getIdByCode(String code){
        return this.getSqlSession().selectOne(getNamespacePrefix() + "getIdByCode", code);
    }
    /**
     * 根据用户获取所在的部门
     * @author GaoJingFei
     * @since 2014-11-3
     * @param name
     * @return
     */
    public Department getDepartmentByUser(String name){
        return this.getSqlSession().selectOne(getNamespacePrefix() + "getDepartmentByUser", name);
    }
}