package com.platform.controller.system;

import com.gao.common.BusinessException;
import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.gao.common.util.JsonUtil;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.platform.entity.system.UserDepartment;
import com.platform.entity.system.DeviceInfo;
import com.platform.entity.system.UserRole;
import com.platform.entity.util.TreeNode;
import com.platform.service.system.ResourceInfoService;
import com.platform.service.system.UserDepartmentService;
import com.platform.service.system.DeviceInfoService;
import com.platform.service.system.UserRoleService;
import com.platform.util.*;
import jxl.biff.DisplayFormat;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import lombok.extern.slf4j.Slf4j;
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
public class DeviceInfoController {

    @Resource
    private ResourceInfoService resourceInfoService;
    @Resource
    private DeviceInfoService deviceInfoService;
    @Resource
    private UserDepartmentService userDepartmentService;
    @Resource
    private UserRoleService userRoleService;

    @RequestMapping(value = "device.html", method = { RequestMethod.GET, RequestMethod.POST })
    public String index(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        Long userId = (Long)(request.getSession().getAttribute(SessionSecurityConstants.KEY_USER_ID));
        if (null == userId) {
            log.error("[DeviceInfoController][index] userId不存在,userId={}", userId);
            return "redirect:/login.html";
        }
        Map<String, String> buttonsMap = resourceInfoService.getButtonCodeByUserId(userId);

        String showAddButton = "NO";

        if(buttonsMap.containsKey(ButtonConstant.DEVICE_ADD_CODE)){
            showAddButton = "YES";
        }

        dataMap.put("showAddButton", showAddButton);
        return "system/device_list";
    }

    @RequestMapping("/deviceInfoList")
    public void deviceInfoList(HttpServletRequest request, HttpServletResponse response){
        Map <String, Object> criteria = Maps.newHashMap();
        try {
            String serialNo = request.getParameter("serialNo");
            String departmentId = request.getParameter("departmentId");
            if(serialNo != null && !"".equals(serialNo)){
                criteria.put("serialNo", serialNo);
            }
            if(departmentId != null && !"0".equals(departmentId)){
                criteria.put("departmentId", departmentId);
            }
            Map<String, Object> dataMap = new HashMap<String, Object>();
            PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
            ServiceResult<Map<String, Object>> serviceResult = deviceInfoService.searchDeviceInfos(criteria, pager);
            if(serviceResult.getSuccess()){
                Map<String, Object> map = serviceResult.getResult();
                if(map!=null&&map.size()>0){
                    List<DeviceInfo> list = (List<DeviceInfo>)map.get("data");
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
            log.error("查询设备列表失败，error={},condition={}", Throwables.getStackTraceAsString(e),criteria);
            throw new BusinessException("查询设备列表失败");
        }
    }

    /**
     * 新增
     * @param request
     * @return
     */
    @RequestMapping(value = "/createDevice", method = RequestMethod.POST)
    @ResponseBody
    public Object createDevice(HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        String type = request.getParameter("type");
        String serialNo = request.getParameter("serialNo");
        String name = request.getParameter("name");
        String ip = request.getParameter("ip");
        String simNo = request.getParameter("simNo");
        String version = request.getParameter("version");

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setType(type);
        deviceInfo.setSerialNo(serialNo);
        deviceInfo.setName(name);
        deviceInfo.setIp(ip);
        deviceInfo.setSimNo(simNo);
        deviceInfo.setVersion(version);
        deviceInfo.setStatus(DeviceInfo.StatusEnum.ENABLE.getStatus());
        deviceInfo.setCreatedBy("system");
        deviceInfo.setUpdatedBy("system");
        String departmentId = request.getParameter("departmentId");
        deviceInfo.setDepartmentId(Long.parseLong(departmentId));
        ServiceResult<DeviceInfo> result = deviceInfoService.createDeviceInfo(deviceInfo);
        if (!result.getSuccess()) {
            log.error("新增设备失败！");
            jsonResult.setMessage("新增设备失败！");
            return jsonResult;
        }

        jsonResult.setData(result.getSuccess());
        return jsonResult;
    }

    /**
     * 更新
     * @param request
     * @return
     */
    @RequestMapping(value = "/updateDevice", method = RequestMethod.POST)
    @ResponseBody
    public Object updateDevice(HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        String userId = request.getParameter("userId");
        String departmentId = request.getParameter("departmentId");
        String roleId = request.getParameter("roleId");
        String parentId = request.getParameter("parentId");
        ServiceResult<DeviceInfo> parentResult = deviceInfoService.getById(Long.parseLong(parentId));
        if (!parentResult.getSuccess()) {
            log.error("修改失败！");
            jsonResult.setMessage("修改失败！");
            return jsonResult;
        }

        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setId(Long.parseLong(userId));
        deviceInfo.setParentId(Long.parseLong(parentId));
        ServiceResult<DeviceInfo> result = deviceInfoService.updateDeviceInfo(deviceInfo);
        if (!result.getSuccess()) {
            log.error("修改失败！");
            jsonResult.setMessage("修改失败！");
            return jsonResult;
        }

        jsonResult.setData(result.getSuccess());
        return jsonResult;
    }

    @RequestMapping(method=RequestMethod.GET,value= {"overview.html",""})
    public String overview(HttpServletRequest request,Map<String, Object> modelMap)  throws Exception {
        String serialNo = request.getParameter("serialNo");
        log.info("[IndexController][overview] serialNo={}", serialNo);
        ServiceResult<DeviceInfo> result = deviceInfoService.getBySerialNo(serialNo);
        modelMap.put("deviceInfo",result.getResult());
        return "overview";
    }


}  