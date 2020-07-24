package com.platform.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.platform.entity.system.UserDepartment;
import com.platform.entity.system.UserInfo;
import com.platform.entity.system.UserRole;
import com.platform.entity.util.TreeNode;
import com.platform.service.system.ResourceInfoService;
import com.platform.service.system.UserDepartmentService;
import com.platform.service.system.UserInfoService;
import com.platform.service.system.UserRoleService;

import com.gao.common.BusinessException;
import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.gao.common.util.JsonUtil;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.platform.util.*;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.LogManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * Created by GaoJingFei on 2017/11/13.
 */

@Controller
@RequestMapping("/system")
@Slf4j
public class UserInfoController {

    @Resource
    private ResourceInfoService resourceInfoService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserDepartmentService userDepartmentService;
    @Resource
    private UserRoleService userRoleService;

    @RequestMapping(value = "user.html", method = { RequestMethod.GET, RequestMethod.POST })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        Long userId = (Long)(request.getSession().getAttribute(SessionSecurityConstants.KEY_USER_ID));
        if (null == userId) {
            log.error("[UserInfoController][index] userId不存在,userId={}", userId);
            return "redirect:/login.html";
        }
        Map<String, String> buttonsMap = resourceInfoService.getButtonCodeByUserId(userId);
        String showResetPasswordButton = "NO";
        String showAddButton = "NO";
        String showEditButton = "NO";
        String showAuditButton = "NO";
        if(buttonsMap.containsKey(ButtonConstant.USER_RESET_PASSWORD_CODE)){
            showResetPasswordButton = "YES";
        }
        if(buttonsMap.containsKey(ButtonConstant.USER_ADD_CODE)){
            showAddButton = "YES";
        }
        if(buttonsMap.containsKey(ButtonConstant.USER_EDIT_CODE)){
            showEditButton = "YES";
        }
        if(buttonsMap.containsKey(ButtonConstant.USER_ADUIT_CODE)){
            showAuditButton = "YES";
        }
        dataMap.put("showResetPasswordButton", showResetPasswordButton);
        dataMap.put("showAddButton", showAddButton);
        dataMap.put("showEditButton", showEditButton);
        dataMap.put("showAuditButton", showAuditButton);
        return "system/user_list";
    }

    @RequestMapping("/userInfoList")
    public void userInfoList(HttpServletRequest request, HttpServletResponse response){
        Map <String, Object> criteria = Maps.newHashMap();
        try {
            String nickName = request.getParameter("nickName");
            String departmentId = request.getParameter("departmentId");
            if(nickName != null && !"".equals(nickName)){
                criteria.put("nickName", nickName);
            }
            if(departmentId != null && !"0".equals(departmentId)){
                criteria.put("departmentId", departmentId);
            }
            Map<String, Object> dataMap = new HashMap<String, Object>();
            PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
            ServiceResult<Map<String, Object>> serviceResult = userInfoService.searchUserInfos(criteria, pager);
            if(serviceResult.getSuccess()){
                Map<String, Object> map = serviceResult.getResult();
                if(map!=null&&map.size()>0){
                    List<UserInfo> list = (List<UserInfo>)map.get("data");
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
            log.error("查询用户列表失败，error={},condition={}", Throwables.getStackTraceAsString(e),criteria);
            throw new BusinessException("查询用户列表失败");
        }
    }

    /**
     * 新增
     * @param request
     * @return
     */
    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public Object createUser(HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String nickName = request.getParameter("nickName");
        String sex = request.getParameter("sex");
        String birthday = request.getParameter("birthday");
        String identityNo = request.getParameter("identityNo");
        String address = request.getParameter("address");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        userInfo.setPassword(PasswordUtil.encrypt(password));
        //userInfo.setPassword(password);
        userInfo.setStatus(1);
        userInfo.setMobile(mobile);
        userInfo.setEmail(email);
        userInfo.setNickName(nickName);
        userInfo.setSex(sex);
        if(birthday != null && !"".equals(birthday)){
            userInfo.setBirthday(DateUtil.parse(DateUtil.format5, birthday));
        }
        userInfo.setIdentityNo(identityNo);
        userInfo.setAddress(address);
        userInfo.setCreatedBy("system");
        userInfo.setUpdatedBy("system");
        String departmentId = request.getParameter("departmentId");
        String roleId = request.getParameter("roleId");
        ServiceResult<UserInfo> result = userInfoService.createUserInfo(userInfo);
        if (!result.getSuccess()) {
            log.error("新增用户失败！");
            jsonResult.setMessage("新增用户失败！");
            return jsonResult;
        }
        Long userId = result.getResult().getId();
        //保存用户部门信息
        UserDepartment userDepartment = new UserDepartment();
        userDepartment.setUserId(userId);
        userDepartment.setDepartmentId(Long.parseLong(departmentId));
        userDepartmentService.createUserDepartment(userDepartment);
        //保存用户角色信息
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        userRole.setRoleId(Long.parseLong(roleId));
        userRoleService.createUserRole(userRole);
        jsonResult.setData(result.getSuccess());
        return jsonResult;
    }

    /**
     * 更新
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public Object updateUser(HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        String userId = request.getParameter("userId");
        String departmentId = request.getParameter("departmentId");
        String roleId = request.getParameter("roleId");
        String parentId = request.getParameter("parentId");
        ServiceResult<UserInfo> parentResult = userInfoService.getById(Long.parseLong(parentId));
        if (!parentResult.getSuccess()) {
            log.error("修改失败！");
            jsonResult.setMessage("修改失败！");
            return jsonResult;
        }
        UserInfo parentUser = parentResult.getResult();

        UserInfo userInfo = new UserInfo();
        userInfo.setId(Long.parseLong(userId));
        userInfo.setParentId(Long.parseLong(parentId));
        userInfo.setParentNickName(parentUser.getNickName());
        ServiceResult<UserInfo> result = userInfoService.updateUserInfo(userInfo);
        if (!result.getSuccess()) {
            log.error("修改失败！");
            jsonResult.setMessage("修改失败！");
            return jsonResult;
        }
        //保存用户部门信息
        UserDepartment userDepartment = new UserDepartment();
        userDepartment.setUserId(Long.parseLong(userId));
        userDepartment.setDepartmentId(Long.parseLong(departmentId));
        userDepartmentService.createUserDepartment(userDepartment);
        //保存用户角色信息
        UserRole userRole = new UserRole();
        userRole.setUserId(Long.parseLong(userId));
        userRole.setRoleId(Long.parseLong(roleId));
        userRoleService.createUserRole(userRole);
        jsonResult.setData(result.getSuccess());
        return jsonResult;
    }

    /**
     * 审核
     * @param request
     * @return
     */
    @RequestMapping(value = "/auditUser", method = RequestMethod.POST)
    @ResponseBody
    public Object auditUser(HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        String userId = request.getParameter("userId");
        String departmentId = request.getParameter("departmentId");
        String roleId = request.getParameter("roleId");
        String parentId = request.getParameter("parentId");
        ServiceResult<UserInfo> parentResult = userInfoService.getById(Long.parseLong(parentId));
        if (!parentResult.getSuccess()) {
            log.error("修改失败！");
            jsonResult.setMessage("修改失败！");
            return jsonResult;
        }
        UserInfo parentUser = parentResult.getResult();

        UserInfo userInfo = new UserInfo();
        userInfo.setId(Long.parseLong(userId));
        userInfo.setParentId(Long.parseLong(parentId));
        userInfo.setParentNickName(parentUser.getNickName());
        userInfo.setStatus(1);
        ServiceResult<UserInfo> result = userInfoService.updateUserInfo(userInfo);
        if (!result.getSuccess()) {
            log.error("审核通过失败！");
            jsonResult.setMessage("审核通过失败！");
            return jsonResult;
        }
        //保存用户部门信息
        UserDepartment userDepartment = new UserDepartment();
        userDepartment.setUserId(Long.parseLong(userId));
        userDepartment.setDepartmentId(Long.parseLong(departmentId));
        userDepartmentService.createUserDepartment(userDepartment);
        //保存用户角色信息
        UserRole userRole = new UserRole();
        userRole.setUserId(Long.parseLong(userId));
        userRole.setRoleId(Long.parseLong(roleId));
        userRoleService.createUserRole(userRole);
        jsonResult.setData(result.getSuccess());
        return jsonResult;
    }

    /**
     * 重置密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    @ResponseBody
    public Object resetPassword(HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        String userId = request.getParameter("userId");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        if(newPassword == null || confirmPassword == null || "".equals(newPassword) || "".equals(confirmPassword)){
            jsonResult.setMessage("新密码和确认密码输入有误，请检查！");
            return jsonResult;
        }
        if(!newPassword.equals(confirmPassword)){
            jsonResult.setMessage("新密码和确认密码不一致，请检查！");
            return jsonResult;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(Long.parseLong(userId));
        userInfo.setPassword(PasswordUtil.encrypt(newPassword));
        //userInfo.setPassword(newPassword);
        ServiceResult<UserInfo> result = userInfoService.updateUserInfo(userInfo);
        if (!result.getSuccess()) {
            log.error("重置密码失败！");
            jsonResult.setMessage("重置密码失败！");
            return jsonResult;
        }
        jsonResult.setData(result.getSuccess());
        return jsonResult;
    }

    /**
     * 用户 列表导出Excel
     * @param request
     * @param response
     */
    @RequestMapping(value = { "/exportUserList" })
    public void exportUserList(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<String, Object>();

        String nickName = request.getParameter("nickName");
        if(nickName != null && !"".equals(nickName)){
            //参数加入params里
            params.put("nickName", nickName);
        }
        PagerInfo pager = new PagerInfo(5000, 1);

        ServiceResult<Map<String, Object>> serviceResult = userInfoService.searchUserInfos(params, pager);
        if(!serviceResult.getSuccess()){
            log.error("查询列表发生异常！");
            return;
        }
        Map<String, Object> map = serviceResult.getResult();
        List<UserInfo> list = new ArrayList<UserInfo>();
        if(map != null && map.size() > 0){
            list = (List<UserInfo>)map.get("data");
        }
        final List<UserInfo> result = list;

        String fileName = "用户列表" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String sheetName = "数据导出";
        String[] sheetHead = new String[] { "用户名", "真实姓名",  "性别", "手机号", "部门", "职务", "使用状态" };

        try {
            ExcelExportUtil.exportEntity(request, response, fileName, sheetName, sheetHead,
                    new ExcelCallbackInterfaceUtil() {

                        @Override
                        public void setExcelBodyTotal(OutputStream os, WritableSheet sheet, int temp)
                                throws Exception {
                            setExcelBodyTotalForUserList(sheet, temp, result);
                        }

                    });
        } catch (Exception e) {
            log.error("用户列表导出失败！", e);
            e.printStackTrace();
        }
    }

    /**
     * 导出用户列表的具体数据，实现回调函数
     * @param sheet
     * @param temp 行号
     * @param list 传入需要导出的 list
     */
    private void setExcelBodyTotalForUserList(WritableSheet sheet, int temp, List<UserInfo> list) throws Exception {
        WritableFont font = new WritableFont(WritableFont.ARIAL, 9, WritableFont.NO_BOLD, false,
                UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
        WritableCellFormat textFormat = new WritableCellFormat(font);
        textFormat.setAlignment(Alignment.CENTRE);
        textFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

        DisplayFormat displayFormat = NumberFormats.INTEGER;
        WritableCellFormat numberFormat = new WritableCellFormat(font, displayFormat);
        numberFormat.setAlignment(jxl.format.Alignment.RIGHT);
        numberFormat.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        for (UserInfo userInfo : list) {
            //jxl.write.Number(列号,行号 ,内容 )
            // "用户名", "真实姓名", "性别", "手机号", "部门", "职务","使用状态"
            sheet.setColumnView(0, 25);
            sheet.addCell(new Label(0, temp, CommUtil.getStringValue(userInfo.getUserName()), textFormat));

            sheet.setColumnView(1, 25);
            sheet.addCell(new Label(1, temp, CommUtil.getStringValue(userInfo.getNickName()),textFormat));

            sheet.setColumnView(2, 25);
            sheet.addCell(new Label(2, temp, CommUtil.getStringValue(userInfo.getSex()),textFormat));

            sheet.setColumnView(3, 25);
            sheet.addCell(new Label(3, temp, CommUtil.getStringValue(userInfo.getMobile()),textFormat));

            sheet.setColumnView(4, 25);
            sheet.addCell(new Label(4, temp, CommUtil.getStringValue(userInfo.getDepartmentName()),textFormat));

            sheet.setColumnView(5, 25);
            sheet.addCell(new Label(5, temp, CommUtil.getStringValue(userInfo.getRoleName()),textFormat));

            //状态转化为名称 '1': '启用',  "2": '待审核'
            String status = CommUtil.getStringValue(userInfo.getStatus());
            if ("1".equals(status)) {
                status = "启用";
            } else if ("0".equals(status)) {
                status = "待审核";
            }
            sheet.setColumnView(6, 25);
            sheet.addCell(new Label(6, temp, CommUtil.getStringValue(status),textFormat));

            temp++;
        }
    }

    /**
     * 用户下拉菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "/searchParentUserCombo", method = RequestMethod.POST)
    @ResponseBody
    public Object searchRoleCombo(HttpServletRequest request) {
        List<TreeNode> nodeList = new ArrayList<TreeNode>();
        ServiceResult<List<UserInfo>> result = userInfoService.getAll();
        if(!result.getSuccess()){
            log.error("查询所有用户列表发生异常！");
            return null;
        }
        List<UserInfo> userList = result.getResult();
        for (UserInfo userInfo : userList) {
            TreeNode node = new TreeNode();
            node.setId(String.valueOf(userInfo.getId()));
            node.setText(userInfo.getNickName());
            node.setState("open");
            nodeList.add(node);
        }
//        JSONArray roleNodes = JSONArray.fromObject(nodeList);
        String userNodes = JsonUtil.toJson(nodeList);
        return userNodes;
    }

    /**
     * 特定角色的用户下拉菜单
     * @param request
     * @return
     */
    @RequestMapping(value = "/searchUserByRoleIdCombo", method = RequestMethod.POST)
    @ResponseBody
    public Object searchUserByRoleIdCombo(HttpServletRequest request) {
        List<TreeNode> nodeList = new ArrayList<TreeNode>();
        String roleId = request.getParameter("roleId");
        if(roleId == null || "".equals(roleId)){
            return JsonUtil.toJson(nodeList);
        }

        ServiceResult<List<UserInfo>> result = userInfoService.getUserByRoleId(Long.parseLong(roleId));
        if(!result.getSuccess()){
            log.error("查询特定角色的用户列表发生异常！");
            return null;
        }
        List<UserInfo> userList = result.getResult();
        for (UserInfo userInfo : userList) {
            TreeNode node = new TreeNode();
            node.setId(String.valueOf(userInfo.getId()));
            node.setText(userInfo.getNickName());
            node.setState("open");
            nodeList.add(node);
        }
        return JsonUtil.toJson(nodeList);
    }

}  