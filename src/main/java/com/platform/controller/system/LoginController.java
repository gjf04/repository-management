package com.platform.controller.system;

import com.platform.entity.system.ResourceInfo;
import com.platform.entity.system.UserInfo;
import com.platform.service.system.ResourceInfoService;
import com.platform.service.system.UserInfoService;
import com.platform.util.HttpJsonResult;
import com.platform.util.SessionSecurityConstants;
import com.gao.common.ServiceResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class LoginController {  
    @Resource  
    private UserInfoService userInfoService;

    @Resource
    private ResourceInfoService resourceInfoService;
    
    @RequestMapping(value = "/loginCommit", method = { RequestMethod.POST })
	@ResponseBody
	public Object loginCommit(HttpServletRequest request,
			HttpServletResponse response){
    	HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        ServiceResult<UserInfo> result = userInfoService.login(userName, password, "");
        if (!result.getSuccess()) {
            log.error(result.getMessage());
            jsonResult.setMessage(result.getMessage());
            jsonResult.setData(false);
            return jsonResult;
        }
        UserInfo user = result.getResult();
        request.getSession().setAttribute(SessionSecurityConstants.KEY_USER_ID, user.getId());
        request.getSession().setAttribute(SessionSecurityConstants.KEY_USER_NAME, user.getUserName());
        request.getSession().setAttribute(SessionSecurityConstants.KEY_USER_NICK_NAME, user.getNickName());

        jsonResult.setData(result.getSuccess());
        return jsonResult;
    }

    @RequestMapping(value = "/logoutCommit", method = { RequestMethod.POST, RequestMethod.GET })
    @ResponseBody
    public Object logoutCommit(HttpServletRequest request,
                              HttpServletResponse response){
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        HttpSession session = request.getSession();
       if(session.getAttribute(SessionSecurityConstants.KEY_USER_ID) != null){
           session.removeAttribute(SessionSecurityConstants.KEY_USER_ID);
           session.removeAttribute(SessionSecurityConstants.KEY_USER_NAME);
           session.removeAttribute(SessionSecurityConstants.KEY_USER_NICK_NAME);
       }
        jsonResult.setData(true);
        return jsonResult;
    }
    
//    @RequestMapping("/index.html")
//    public String index(HttpServletRequest request,Map<String, Object> stack){
//        Long userId = (Long)(request.getSession().getAttribute(SessionSecurityConstants.KEY_USER_ID));
//        if (null == userId) {
//            log.error("[LoginController][index] userId不存在,userId={}", userId);
//            return "redirect:/login.html";
//        }
//        ServiceResult<UserInfo> result = userInfoService.getById(userId);
//        if (!result.getSuccess() || result.getResult() == null) {
//            log.error("[LoginController][index] 根据userId查不到用户信息,userId={}", userId);
//            return "redirect:/login.html";
//        }
//        UserInfo user = result.getResult();
//        List<ResourceInfo> moduleList = getResourceInfoByUserId(user.getId());
//        stack.put("user", user);
//        stack.put("moduleList", moduleList);
//        return "index";
//    }

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
            for(int i = 0; i < moduleList.size(); i++){
                if(moduleList.get(i).getChildren() == null || moduleList.get(i).getChildren().size() == 0){
                    moduleList.remove(i);
                }
            }
        }
        return moduleList;
    }
}  