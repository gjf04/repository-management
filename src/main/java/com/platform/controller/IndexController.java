package com.platform.controller;

import com.gao.common.ServiceResult;
import com.platform.entity.system.DeviceAlarmInfo;
import com.platform.entity.system.DeviceInfo;
import com.platform.entity.system.UserInfo;
import com.platform.service.system.DeviceAlarmInfoService;
import com.platform.service.system.DeviceInfoService;
import com.platform.service.system.ResourceInfoService;
import com.platform.service.system.UserInfoService;
import com.platform.util.ButtonConstant;
import com.platform.util.ModuleConstant;
import com.platform.util.SessionSecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
@Slf4j
public class IndexController extends AbstractController{
	@Resource
	private ResourceInfoService resourceInfoService;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private DeviceAlarmInfoService deviceAlarmInfoService;
	@Resource
	private DeviceInfoService deviceInfoService;

	@RequestMapping(method=RequestMethod.GET,value= {"login.html",""})
	public String login(HttpServletRequest request,Map<String, Object> modelMap)  throws Exception {

		return "login";
	}

	@RequestMapping(method=RequestMethod.GET,value= {"index.html",""})
	public String indexView(HttpServletRequest request,Map<String, Object> modelMap)  throws Exception {
		Long userId = (Long)(request.getSession().getAttribute(SessionSecurityConstants.KEY_USER_ID));
		if (null == userId) {
			log.error("[IndexController][indexView] userId不存在,userId={}", userId);
			return "redirect:/login.html";
		}
		Map<String, String> buttonsMap = resourceInfoService.getButtonCodeByUserId(userId);
		Map<String, String> modulesMap = resourceInfoService.getModuleCodeByUserId(userId);
		String showDepartmentModule = "NO";
		String showUserModule = "NO";
		String showRoleModule = "NO";
		String showRoleResourceModule = "NO";
		String showSetModule = "NO";
		String showElecModule = "NO";
		for(String s : buttonsMap.keySet()){
			if(s.startsWith(ModuleConstant.DEPARTMENT_MANAGEMENT)){
				showDepartmentModule = "YES";
			}
			if(s.startsWith(ModuleConstant.USER_MANAGEMENT)){
				showUserModule = "YES";
			}
			if(s.startsWith(ModuleConstant.ROLE_MANAGEMENT)){
				showRoleModule = "YES";
			}
			if(s.startsWith(ModuleConstant.ROLE_RESOURCE_MANAGEMENT)){
				showRoleResourceModule = "YES";
			}
			if(s.startsWith(ModuleConstant.SET_MANAGEMENT)){
				showSetModule = "YES";
			}
			if(s.startsWith(ModuleConstant.ELEC_MANAGEMENT)){
				showElecModule = "YES";
			}
		}

		for(String s : modulesMap.keySet()){
			if(s.equals(ModuleConstant.DEPARTMENT_MANAGEMENT)){
				showDepartmentModule = "YES";
			}
			if(s.equals(ModuleConstant.USER_MANAGEMENT)){
				showUserModule = "YES";
			}
			if(s.equals(ModuleConstant.ROLE_MANAGEMENT)){
				showRoleModule = "YES";
			}
			if(s.equals(ModuleConstant.ROLE_RESOURCE_MANAGEMENT)){
				showRoleResourceModule = "YES";
			}
			if(s.equals(ModuleConstant.SET_MANAGEMENT)){
				showSetModule = "YES";
			}
			if(s.equals(ModuleConstant.ELEC_MANAGEMENT)){
				showElecModule = "YES";
			}
		}

		modelMap.put("showDepartmentModule", showDepartmentModule);
		modelMap.put("showUserModule", showUserModule);
		modelMap.put("showRoleModule", showRoleModule);
		modelMap.put("showRoleResourceModule", showRoleResourceModule);
		modelMap.put("showSetModule", showSetModule);
		modelMap.put("showElecModule", showElecModule);
		String nickName = "";
		ServiceResult<UserInfo> parentResult = userInfoService.getById(userId);
		if (!parentResult.getSuccess()) {
			log.error("失败！");
		}else{
			UserInfo parentUser = parentResult.getResult();
			nickName = parentUser.getNickName();
		}
		
		modelMap.put("nickName",nickName);
		return "index";
	}

	@RequestMapping(method=RequestMethod.GET,value= {"firePre.html",""})
	public String firePre(HttpServletRequest request,Map<String, Object> modelMap)  throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		ServiceResult<List<DeviceAlarmInfo>> alarmInfoResult = deviceAlarmInfoService.searchDeviceAlarmInfosByCondition(params);
		if(alarmInfoResult.getSuccess() && alarmInfoResult.getResult().size() > 0){
			modelMap.put("deviceAlarmInfoList",alarmInfoResult.getResult());
		}
		return "firePre";
	}



}
