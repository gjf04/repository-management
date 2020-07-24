package com.platform.controller.system;

import com.platform.entity.system.Department;
import com.platform.entity.system.UserInfo;
import com.platform.entity.util.ClosedDepartmentTreeNodeFactory;
import com.platform.service.system.DepartmentService;
import com.platform.service.system.ResourceInfoService;
import com.platform.service.system.UserInfoService;
import com.platform.util.ButtonConstant;
import com.platform.util.HttpJsonResult;
import com.platform.util.SessionSecurityConstants;
import com.platform.util.WebUtil;
import com.gao.common.BusinessException;
import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.gao.common.util.JsonUtil;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by GaoJingFei on 2017/11/13.
 */

@Controller
@RequestMapping("/system")
@Slf4j
public class DepartmentController {
    @Resource
    private ResourceInfoService resourceInfoService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private UserInfoService userInfoService;

    @RequestMapping(value = "department.html", method = { RequestMethod.GET, RequestMethod.POST })
    public String index(HttpServletRequest request,Map<String, Object> dataMap) throws Exception {
        Long userId = (Long)(request.getSession().getAttribute(SessionSecurityConstants.KEY_USER_ID));
        if (null == userId) {
            log.error("[DepartmentController][index] userId不存在,userId={}", userId);
            return "redirect:/login.html";
        }
        Map<String, String> buttonsMap = resourceInfoService.getButtonCodeByUserId(userId);
        String showAddButton = "NO";
        String showEditButton = "NO";
        String showRemoveButton = "NO";
        if(buttonsMap.containsKey(ButtonConstant.DEPARTMENT_ADD_CODE)){
            showAddButton = "YES";
        }
        if(buttonsMap.containsKey(ButtonConstant.DEPARTMENT_EDIT_CODE)){
            showEditButton = "YES";
        }
        if(buttonsMap.containsKey(ButtonConstant.DEPARTMENT_REMOVE_CODE)){
            showRemoveButton = "YES";
        }

        dataMap.put("showAddButton", showAddButton);
        dataMap.put("showEditButton", showEditButton);
        dataMap.put("showRemoveButton", showRemoveButton);
        return "system/department_list";
    }

    @RequestMapping("/departmentList")
    public void departmentList(HttpServletRequest request, HttpServletResponse response){
        Map <String, Object> criteria = Maps.newHashMap();
        try {
            String parentId = request.getParameter("parentId");
            String name = request.getParameter("name");
            if(parentId != null && !"".equals(parentId) && !"0".equals(parentId)){
                criteria.put("parentId", parentId);
            }
            if(name != null && !"".equals(name)){
                criteria.put("name", name);
            }
            Map<String, Object> dataMap = new HashMap<String, Object>();
            PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
            ServiceResult<Map<String, Object>> serviceResult = departmentService.searchDepartments(criteria, pager);
            if(serviceResult.getSuccess()){
                Map<String, Object> map = serviceResult.getResult();
                if(map!=null&&map.size()>0){
                    List<Department> list = (List<Department>)map.get("data");
                    int total = (Integer)map.get("total");
                    dataMap.put("total", total);
                    dataMap.put("rows", list);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(JsonUtil.toJson(dataMap));
                    response.getWriter().flush();
                    response.getWriter().close();
                }
            }
        }catch (Exception e) {
            log.error("查询部门列表失败，error={},condition={}", Throwables.getStackTraceAsString(e),criteria);
            throw new BusinessException("查询部门列表失败");
        }
    }

    /**
     * 组织架构树
     * @param request
     * @return
     */
    @RequestMapping(value = "/departmentTree", method = RequestMethod.POST)
    @ResponseBody
    public Object departmentTree(HttpServletRequest request) {
//        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        List<Department> roots = null;
        roots = departmentService.getAll();
//        JSONArray departmentNodes = JSONArray.fromObject(new ClosedDepartmentTreeNodeFactory().buildTreeNodeList(roots));
        String departmentNodes = JsonUtil.toJson(new ClosedDepartmentTreeNodeFactory().buildTreeNodeList(roots));
//        jsonResult.setData(departmentNodes);
        return departmentNodes;
    }

    /**
     * 新增
     * @param request
     * @return
     */
    @RequestMapping(value = "/createDepartment", method = RequestMethod.POST)
    @ResponseBody
    public Object createDepartment(HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        String name = request.getParameter("name");
        String code = request.getParameter("code");
        String principalUserId = request.getParameter("principalUserId");
        ServiceResult<UserInfo> parentResult = userInfoService.getById(Long.parseLong(principalUserId));
        if (!parentResult.getSuccess()) {
            log.error("新增失败！");
            jsonResult.setMessage("新增失败！");
            return jsonResult;
        }
        UserInfo principalUser = parentResult.getResult();

        String parentId = request.getParameter("parentId");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        Department parent = new Department();
        parent.setId(Long.parseLong(parentId));
        Department department = new Department();
        department.setName(name);
        department.setCode(code);
        department.setPrincipalUserId(Long.parseLong(principalUserId));
        department.setPrincipalNickName(principalUser.getNickName());
        department.setParent(parent);
        department.setDescription(description);
        department.setAddress(address);
        department.setCreatedBy("system");
        department.setUpdatedBy("system");
        ServiceResult<Department> result = departmentService.createDepartment(department);
        if (!result.getSuccess()) {
            log.error("新增部门失败！");
            jsonResult.setMessage("新增部门失败！");
            return jsonResult;
        }
        jsonResult.setData(result.getSuccess());
        return jsonResult;
    }

}  