package com.platform.controller.bom;

import com.gao.common.BusinessException;
import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.gao.common.util.JsonUtil;

import com.google.common.collect.Maps;
import com.platform.entity.bom.BomMain;

import com.platform.service.bom.BomMainService;

import com.platform.service.system.ResourceInfoService;
import com.platform.util.ButtonConstant;
import com.platform.util.SessionSecurityConstants;
import com.platform.util.WebUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by GaoJingFei on 2020/07/27.
 */

@Controller
@RequestMapping("/bom")
@Slf4j
public class BomController {
    @Resource
    private ResourceInfoService resourceInfoService;
    @Resource
    private BomMainService bomMainService;

    @RequestMapping(value = "bomMain.html", method = { RequestMethod.GET, RequestMethod.POST })
    public String bomMain(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        Long userId = (Long)(request.getSession().getAttribute(SessionSecurityConstants.KEY_USER_ID));
        if (null == userId) {
            log.error("[BomController][bomMain] userId不存在,userId={}", userId);
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
        return "bom/bomMain_list";
    }

    @RequestMapping(value = { "bomMainList" })
    public void bomMainList(HttpServletRequest request, HttpServletResponse response) {
        Map <String, Object> criteria = Maps.newHashMap();
        try {
            String deviceName = request.getParameter("deviceName");
            if(deviceName != null && !"".equals(deviceName)){
                criteria.put("deviceName", deviceName);
            }
            String customerCode = request.getParameter("customerCode");
            if(customerCode != null && !"".equals(customerCode)){
                criteria.put("customerCode", customerCode);
            }
            Map<String, Object> dataMap = new HashMap<String, Object>();
            PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
            ServiceResult<Map<String, Object>> serviceResult = bomMainService.getBomMainList(criteria, pager);
            if(serviceResult.getSuccess()){
                Map<String, Object> map = serviceResult.getResult();
                if(map!=null&&map.size()>0){
                    List<BomMain> list = (List<BomMain>)map.get("data");
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
            log.error("BOM列表查询失败,error={}", e.getMessage());
            throw new BusinessException("BOM列表查询失败" + e.getMessage());
        }
    }

}  