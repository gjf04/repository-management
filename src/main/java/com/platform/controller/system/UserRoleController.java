package com.platform.controller.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * Created by GaoJingFei on 2017/11/13.
 */

@Controller
@RequestMapping("/system")
@Slf4j
public class UserRoleController {

    @RequestMapping(value = "userRole.html", method = { RequestMethod.GET, RequestMethod.POST })
    public String index(HttpServletRequest request, Model model) throws Exception {
       
        return "system/userRole_list";
    }
    
    @RequestMapping("/userRoleList")
    public String userRoleList(HttpServletRequest request,Map<String, Object> stack){
        
        return "showUser2";  
    }  
}  