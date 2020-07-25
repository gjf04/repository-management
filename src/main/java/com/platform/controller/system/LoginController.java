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

/**
 *
 * Created by GaoJingFei on 2020/07/25.
 */

@Controller
@Slf4j
public class LoginController {  
    @Resource  
    private UserInfoService userInfoService;
    
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


}  