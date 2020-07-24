package com.platform.controller.system;

import com.platform.entity.system.Role;
import com.platform.entity.util.TreeNode;
import com.platform.service.system.ResourceInfoService;
import com.platform.service.system.RoleService;
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
import org.apache.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
public class RoleController {
    @Resource
    private ResourceInfoService resourceInfoService;
    @Resource
    private RoleService roleService;

    @RequestMapping(value = "role.html", method = { RequestMethod.GET, RequestMethod.POST })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        Long userId = (Long)(request.getSession().getAttribute(SessionSecurityConstants.KEY_USER_ID));
        if (null == userId) {
            log.error("[DepartmentController][index] userId不存在,userId={}", userId);
            return "redirect:/login.html";
        }
        Map<String, String> buttonsMap = resourceInfoService.getButtonCodeByUserId(userId);
        String showAddButton = "NO";
        String showEditButton = "NO";
        String showRemoveButton = "NO";
        if(buttonsMap.containsKey(ButtonConstant.ROLE_ADD_CODE)){
            showAddButton = "YES";
        }
        if(buttonsMap.containsKey(ButtonConstant.ROLE_EDIT_CODE)){
            showEditButton = "YES";
        }
        if(buttonsMap.containsKey(ButtonConstant.ROLE_REMOVE_CODE)){
            showRemoveButton = "YES";
        }

        dataMap.put("showAddButton", showAddButton);
        dataMap.put("showEditButton", showEditButton);
        dataMap.put("showRemoveButton", showRemoveButton);
        return "system/role_list";
    }

    @RequestMapping(value = { "roleList" })
    public void roleList(HttpServletRequest request, HttpServletResponse response) {
        Map <String, Object> criteria = Maps.newHashMap();
        try {
            String name = request.getParameter("name");
            if(name != null && !"".equals(name)){
                criteria.put("name", name);
            }
            Map<String, Object> dataMap = new HashMap<String, Object>();
            PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
            ServiceResult<Map<String, Object>> serviceResult = roleService.getRoleList(criteria, pager);
            if(serviceResult.getSuccess()){
                Map<String, Object> map = serviceResult.getResult();
                if(map!=null&&map.size()>0){
                    List<Role> list = (List<Role>)map.get("data");
                    int total = (Integer)map.get("total");
                    dataMap.put("total", total);
                    dataMap.put("rows", list);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(JsonUtil.toJson(dataMap));
                    response.getWriter().flush();
                    response.getWriter().close();
                }
            }
        } catch (IOException e) {
            log.error("角色列表查询失败,error={}", e.getMessage());
            throw new BusinessException("角色列表查询失败" + e.getMessage());
        }
    }

    @RequestMapping(value = { "saveRole" }, method = { RequestMethod.POST })
    @ResponseBody
    public HttpJsonResult<Object> saveRole(@RequestParam(required = false) String code,
                                                   @RequestParam(required = false) String name,
                                                   @RequestParam(required = false) String description,
                                                   HttpServletRequest request, HttpServletResponse response) {
        HttpJsonResult<Object> result = new HttpJsonResult<Object>();

        ServiceResult<Role> seResult = roleService.getByName(name.trim());
        if(seResult.getSuccess() && seResult != null && seResult.getResult() != null) {
            log.error("该角色名称已存在");
            throw new BusinessException("该角色名称已存在");
        }
        Role role = new Role();
        role.setCode(code);
        role.setName(name);
        role.setDescription(description);
        role.setCreatedBy((String)(request.getSession().getAttribute(SessionSecurityConstants.KEY_USER_NICK_NAME)));
        role.setUpdatedBy((String)(request.getSession().getAttribute(SessionSecurityConstants.KEY_USER_NICK_NAME)));

        try {
            ServiceResult<Integer> roleResult = roleService.insert(role);
            if (!roleResult.getSuccess()) {
                result.setMessage("保存失败，请稍后重试！");
            }
        } catch (Exception e) {
            result.setMessage("保存失败，请稍后重试！");
            log.error("保存失败，请稍后重试！error={}" + Throwables.getStackTraceAsString(e));
        }
        return result;
    }

    /**
     * 角色下拉菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "/searchRoleCombo", method = RequestMethod.POST)
    @ResponseBody
    public Object searchRoleCombo(HttpServletRequest request) {
        List<TreeNode> nodeList = new ArrayList<TreeNode>();
        List<Role> roleList = roleService.getAll();
        for (Role role : roleList) {
            TreeNode node = new TreeNode();
            node.setId(String.valueOf(role.getId()));
            node.setText(role.getName());
            node.setState("open");
            nodeList.add(node);
        }
        //JSONArray roleNodes = JSONArray.fromObject(nodeList);
        String roleNodes = JsonUtil.toJson(nodeList);
        return roleNodes;
    }
}  