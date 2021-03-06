package com.platform.controller;

import com.gao.common.ServiceResult;
import com.platform.entity.system.ResourceInfo;
import com.platform.entity.system.UserInfo;
import com.platform.service.system.ResourceInfoService;
import com.platform.service.system.UserInfoService;
import com.platform.util.SessionSecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by GaoJingFei on 2020/07/25.
 */

@Controller
@RequestMapping("/")
@Slf4j
public class IndexController extends AbstractController{
	@Resource
	private ResourceInfoService resourceInfoService;
	@Resource
	private UserInfoService userInfoService;

	@RequestMapping(method=RequestMethod.GET,value= {"login.html",""})
	public String login(HttpServletRequest request,Map<String, Object> modelMap)  throws Exception {

		return "login";
	}

	@RequestMapping(value = "index.html", method = { RequestMethod.GET, RequestMethod.POST })
	public String index(HttpServletRequest request,Map<String, Object> stack){
		Long userId = (Long)(request.getSession().getAttribute(SessionSecurityConstants.KEY_USER_ID));
		if (null == userId) {
			log.error("[LoginController][index] userId不存在,userId={}", userId);
			return "redirect:/login.html";
		}
		ServiceResult<UserInfo> result = userInfoService.getById(userId);
		if (!result.getSuccess() || result.getResult() == null) {
			log.error("[LoginController][index] 根据userId查不到用户信息,userId={}", userId);
			return "redirect:/login.html";
		}
		UserInfo user = result.getResult();
		List<ResourceInfo> moduleList = getResourceInfoByUserId(user.getId());
		stack.put("user", user);
		stack.put("moduleList", moduleList);
		return "index";
	}

	/**
	 * 根据userId获取左侧菜单资源
	 * */
	private List<ResourceInfo> getResourceInfoByUserId(Long userId){
		List<ResourceInfo> moduleList = new ArrayList<ResourceInfo>();
		ServiceResult<List<ResourceInfo>> resourceInfoResult = resourceInfoService.getByUserId(userId);
		if(resourceInfoResult.getSuccess() && resourceInfoResult.getResult() != null && resourceInfoResult.getResult().size() > 0){
			List<ResourceInfo> resourceInfoList = resourceInfoResult.getResult();
			Map<Long, List<ResourceInfo>> pageMap = new HashMap<Long, List<ResourceInfo>>();
			for(ResourceInfo resourceInfo : resourceInfoList){
				if(ResourceInfo.TypeEnum.MODULE.getType().equals(resourceInfo.getType())){
					moduleList.add(resourceInfo);
				}else if(ResourceInfo.TypeEnum.PAGE.getType().equals(resourceInfo.getType())){
					if(pageMap.containsKey(resourceInfo.getParentId())){
						pageMap.get(resourceInfo.getParentId()).add(resourceInfo);
					}else{
						List<ResourceInfo> pageList = new ArrayList<ResourceInfo>();
						pageList.add(resourceInfo);
						pageMap.put(resourceInfo.getParentId(), pageList);
					}
				}
			}
			for(ResourceInfo resourceInfo : moduleList){
				resourceInfo.setChildren(pageMap.get(resourceInfo.getId()));
			}
			//去掉无任何页面的模块
			cleanModuleList(moduleList);
		}
		return moduleList;
	}

	/**
	 * 去掉无任何页面的模块
	 * */
	private void cleanModuleList(List<ResourceInfo> moduleList){
		boolean repeat = false;
		for(int i = 0; i < moduleList.size(); i++){
			if(moduleList.get(i) == null){
				continue;
			}
			if(moduleList.get(i).getChildren() == null || moduleList.get(i).getChildren().size() == 0){
				moduleList.remove(i);
				repeat = true;
			}
		}
		if(repeat){
			cleanModuleList(moduleList);
		}
	}


}
