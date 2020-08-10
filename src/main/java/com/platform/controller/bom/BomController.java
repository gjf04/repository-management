package com.platform.controller.bom;

import com.alibaba.fastjson.JSONArray;
import com.gao.common.BusinessException;
import com.gao.common.PagerInfo;
import com.gao.common.ServiceResult;
import com.gao.common.util.JsonUtil;

import com.gao.common.util.StringUtil;
import com.google.common.collect.Maps;
import com.platform.controller.AbstractController;
import com.platform.entity.BaseEntity;
import com.platform.entity.bom.BomDeliveryDetail;
import com.platform.entity.bom.BomMain;

import com.platform.entity.bom.BomSub;
import com.platform.entity.system.UserDepartment;
import com.platform.entity.system.UserInfo;
import com.platform.entity.system.UserRole;
import com.platform.service.bom.BomDeliveryDetailService;
import com.platform.service.bom.BomMainService;

import com.platform.service.bom.BomSubService;
import com.platform.service.system.ResourceInfoService;
import com.platform.util.*;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.io.InputStream;
import java.util.*;

/**
 *
 * Created by GaoJingFei on 2020/07/27.
 */

@Controller
@RequestMapping("/bom")
@Slf4j
public class BomController extends AbstractController {
    @Resource
    private ResourceInfoService resourceInfoService;
    @Resource
    private BomMainService bomMainService;
    @Resource
    private BomSubService bomSubService;
    @Resource
    private BomDeliveryDetailService bomDeliveryDetailService;

    @RequestMapping(value = "bomMain.html", method = { RequestMethod.GET, RequestMethod.POST })
    public String bomMain(HttpServletRequest request, Map<String, Object> dataMap) throws Exception {
        Long userId = getCurrentUserId(request);
        if (null == userId) {
            log.error("[BomController][bomMain] userId不存在,userId={}", userId);
            return "redirect:/login.html";
        }
        Map<String, String> buttonsMap = resourceInfoService.getButtonCodeByUserId(userId);
        String showAddButton = "NO";
        String showEditButton = "NO";
        String showRemoveButton = "NO";
        if(buttonsMap.containsKey(ButtonConstant.BOM_ADD_CODE)){
            showAddButton = "YES";
        }
        if(buttonsMap.containsKey(ButtonConstant.BOM_EDIT_CODE)){
            showEditButton = "YES";
        }
        if(buttonsMap.containsKey(ButtonConstant.BOM_REMOVE_CODE)){
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
            String createdAtStart = request.getParameter("createdAtStart");
            if(createdAtStart != null && !"".equals(createdAtStart)){
                criteria.put("createdAtStart", CommUtil.getStringToDate(createdAtStart + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            }
            String createdAtEnd = request.getParameter("createdAtEnd");
            if(createdAtEnd != null && !"".equals(createdAtEnd)){
                criteria.put("createdAtEnd", CommUtil.getStringToDate(createdAtEnd + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
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

    @RequestMapping(value = { "bomSubList" })
    public void bomSubList(HttpServletRequest request, HttpServletResponse response) {
        Map <String, Object> criteria = Maps.newHashMap();
        try {
            String bomId = request.getParameter("bomId");
            if(bomId != null && !"".equals(bomId)){
                criteria.put("bomId", Long.parseLong(bomId));
            }
            Map<String, Object> dataMap = new HashMap<String, Object>();
            PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
            ServiceResult<Map<String, Object>> serviceResult = bomSubService.getBomSubList(criteria, pager);
            if(serviceResult.getSuccess()){
                Map<String, Object> map = serviceResult.getResult();
                if(map!=null&&map.size()>0){
                    List<BomSub> list = (List<BomSub>)map.get("data");
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
            log.error("BOM零配件列表查询失败,error={}", e.getMessage());
            throw new BusinessException("BOM零配件列表查询失败" + e.getMessage());
        }
    }


    /**
     * BOM导入
     */
    @RequestMapping(value =  "importBomMain" )
    @ResponseBody
    public Object importCustomer(HttpServletRequest request,
                               HttpServletResponse response) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        // 转型为MultipartHttpRequest
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        // 获得文件
        MultipartFile file = multipartRequest.getFile("file");
        if (file == null) {
            log.error("没有选择导入文件，请选择导入文件后再点击导入操作！");
            jsonResult.setMessage("没有选择导入文件，请选择导入文件后再点击导入操作！");
            return jsonResult;
        }

        try {

            List<String[]> list = this.readExcel(file.getInputStream(), 1);

            //验证是否有数据
            if (list.size() <= 1) {
                jsonResult.setMessage("很抱歉！你导入的Excel没有数据记录，请重新整理导入！");
                return jsonResult;
            }

            String nickName = getCurrentUserNickName(request);

            BomMain bomMain = new BomMain();
            //客户编码 PM担当
            String[] lineData3 = list.get(2);//表格第3行
            bomMain.setCustomerCode(StringUtil.nullSafeString(lineData3[1]));
            bomMain.setPmName(StringUtil.nullSafeString(lineData3[3]));
            //机种名称 ME担当
            String[] lineData4 = list.get(3);//表格第4行
            bomMain.setDeviceName(StringUtil.nullSafeString(lineData4[1]));
            bomMain.setMeName(StringUtil.nullSafeString(lineData4[3]));
            //物料交期 制作日期
            String[] lineData5 = list.get(4);//表格第5行
            bomMain.setDeliveryDate(StringUtil.nullSafeString(lineData5[1]));
            bomMain.setProductionDate(StringUtil.nullSafeString(lineData5[3]));
            //总数量
            String[] lineData6 = list.get(5);//表格第6行
            String numStr = StringUtil.nullSafeString(lineData6[5]);
            if("".equals(numStr) || !CommUtil.canToInt(numStr)){
                numStr = "1";
            }
            bomMain.setNum(Integer.parseInt(numStr));
            bomMain.setStatus(BomMain.StatusEnum.UNCLOSED.getStatus());
            bomMain.setCreatedBy(nickName);
            bomMain.setUpdatedBy(nickName);
            ServiceResult<BomMain> insetBomMainResult = bomMainService.insert(bomMain);
            if(!insetBomMainResult.getSuccess()){
                jsonResult.setMessage("很抱歉！BOM导入时数据保存失败，请重新导入！");
                return jsonResult;
            }
            bomMain.setId(insetBomMainResult.getResult().getId());

            List<BomSub> bomSubList = new ArrayList<BomSub>();
            for( int i=8 ; i < list.size() ; i++){
                String[] str = list.get(i);
                String serialNo = StringUtil.nullSafeString(str[0]);
                if("".equals(serialNo) || str.length < 12){
                    continue;
                }
                String name = StringUtil.nullSafeString(str[1]);
                String brand = StringUtil.nullSafeString(str[2]);
                String specifications = StringUtil.nullSafeString(str[3]);
                String unit = StringUtil.nullSafeString(str[4]);
                String singleAmount = StringUtil.nullSafeString(str[5]);
                //String totalAmount = StringUtil.nullSafeString(str[6]);
                String stockAmount = StringUtil.nullSafeString(str[7]);
                String stockUpAmount = StringUtil.nullSafeString(str[8]);
                String purchaseAmount = StringUtil.nullSafeString(str[9]);
                String deliveryDate = StringUtil.nullSafeString(str[10]);
                String remark = StringUtil.nullSafeString(str[11]);

                if (StringUtil.isEmpty(name, true)) {
                    //jsonResult.setMessage("很抱歉！你导入的Excel数据,第"+ (i+1) +"行数据 零配件名称不能为空! 请核查后重新导入！");
                    continue;
                }

                BomSub bomSub = new BomSub();
                bomSub.setBomId(bomMain.getId());
                bomSub.setSerialNo(serialNo);
                bomSub.setName(name);
                bomSub.setBrand(brand);
                bomSub.setSpecifications(specifications);
                bomSub.setUnit(unit);
                if(!"".equals(singleAmount) && CommUtil.canToInt(singleAmount)){
                    bomSub.setSingleAmount(Integer.parseInt(singleAmount));
                }else{
                    bomSub.setSingleAmount(1);
                }
                bomSub.setTotalAmount(bomSub.getSingleAmount() * bomMain.getNum());
                if(!"".equals(stockAmount) && CommUtil.canToInt(stockAmount)){
                    bomSub.setStockAmount(Integer.parseInt(stockAmount));
                }
                if(!"".equals(stockUpAmount) && CommUtil.canToInt(stockUpAmount)){
                    bomSub.setStockUpAmount(Integer.parseInt(stockUpAmount));
                }
                if(!"".equals(purchaseAmount) && CommUtil.canToInt(purchaseAmount)){
                    bomSub.setPurchaseAmount(Integer.parseInt(purchaseAmount));
                }
                bomSub.setDeliveryDate(deliveryDate);
                bomSub.setDeliveryAmount(0);
                bomSub.setCreatedBy(nickName);
                bomSub.setUpdatedBy(nickName);
                bomSub.setRemark(remark);
                bomSubList.add(bomSub);

            }
            if(bomSubList.size() > 0){
                ServiceResult<Integer> batchInsertResult = bomSubService.batchInsert(bomSubList);
                if(!batchInsertResult.getSuccess()){
                    log.error("导入失败");
                    jsonResult.setMessage(batchInsertResult.getMessage());
                    return jsonResult;
                }
            }
            jsonResult.setData(true);
            return jsonResult;

        }catch (Exception e) {
            e.printStackTrace();
            log.error("导入失败", e);
            jsonResult.setMessage(e.getMessage());
            return jsonResult;
        }

    }

    /**
     *
     * @param stream 读取文件对象
     * @param rowNum 从第几行开始读，如果有一行表头则从第二行开始读
     */
    private List<String[]> readExcel(InputStream stream, int rowNum) throws BiffException,
            IOException {
        // 创建一个list 用来存储读取的内容
        List<String[]> list = new ArrayList<String[]>();
        Workbook rwb = null;
        Cell cell = null;
        // 创建输入流
        //        InputStream stream = new FileInputStream(excelFile);
        // 获取Excel文件对象
        rwb = Workbook.getWorkbook(stream);
        // 获取文件的指定工作表 默认的第一个
        Sheet sheet = rwb.getSheet(0);
        // 行数(表头的目录不需要，从1开始)
        for (int i = rowNum - 1; i < sheet.getRows(); i++) {
            // 创建一个数组 用来存储每一列的值
            String[] str = new String[sheet.getColumns()];
            // 列数
            for (int j = 0; j < sheet.getColumns(); j++) {
                // 获取第i行，第j列的值
                cell = sheet.getCell(j, i);
                str[j] = cell.getContents();
            }
            // 把刚获取的列存入list
            list.add(str);
        }
        rwb.close();
        stream.close();
        // 返回值集合
        return list;
    }

    /**
     * 结案
     * @param request
     * @return
     */
    @RequestMapping(value = "/closeBom", method = RequestMethod.POST)
    @ResponseBody
    public Object closeBom(HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        String id = request.getParameter("id");
        BomMain dbBomMain = bomMainService.getById(Long.parseLong(id));
        if (dbBomMain == null) {
            log.error("[BomController][closeBom]BOM不存在，结案失败！bomId={}", id);
            jsonResult.setMessage("BOM不存在，结案失败！");
            return jsonResult;
        }
        if (BomMain.StatusEnum.CLOSED.getStatus().equals(dbBomMain.getStatus())) {
            log.error("[BomController][closeBom]该BOM已结案，操作失败！bomId={}", id);
            jsonResult.setMessage("该BOM已结案，操作失败！");
            return jsonResult;
        }
        String nickName = getCurrentUserNickName(request);
        BomMain bomMain = new BomMain();
        bomMain.setId(dbBomMain.getId());
        bomMain.setStatus(BomMain.StatusEnum.CLOSED.getStatus());
        bomMain.setClosedAt(new Date());
        bomMain.setClosedBy(nickName);
        bomMain.setUpdatedBy(nickName);
        ServiceResult<Boolean> result = bomMainService.updateBomMain(bomMain);
        if (!result.getSuccess()) {
            log.error("[BomController][closeBom]结案失败！bomId={}", id);
            jsonResult.setMessage("结案失败！");
            return jsonResult;
        }
        jsonResult.setData(result.getSuccess());
        return jsonResult;
    }

    /**
     * 删除
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteBom", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteBom(HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        String id = request.getParameter("id");
        BomMain dbBomMain = bomMainService.getById(Long.parseLong(id));
        if (dbBomMain == null) {
            log.error("[BomController][deleteBom]BOM不存在，操作失败！bomId={}", id);
            jsonResult.setMessage("BOM不存在，操作失败！");
            return jsonResult;
        }
        if (BomMain.StatusEnum.CLOSED.getStatus().equals(dbBomMain.getStatus())) {
            log.error("[BomController][deleteBom]该BOM已结案，操作失败！bomId={}", id);
            jsonResult.setMessage("该BOM已结案，操作失败！");
            return jsonResult;
        }
        String nickName = getCurrentUserNickName(request);
        BomMain bomMain = new BomMain();
        bomMain.setId(dbBomMain.getId());
        bomMain.setIsDelete(BomMain.IsDeleteEnum.YES.getIsDelete());
        bomMain.setUpdatedBy(nickName);
        ServiceResult<Boolean> result = bomMainService.updateBomMain(bomMain);
        if (!result.getSuccess()) {
            log.error("[BomController][deleteBom]操作失败！bomId={}", id);
            jsonResult.setMessage("操作失败！");
            return jsonResult;
        }
        ServiceResult<Integer> subResult = bomSubService.deleteByBomId(dbBomMain.getId());
        if (!subResult.getSuccess()) {
            log.error("[BomController][deleteBom]更新bomSub.isDelete失败！bomId={}", id);
        }
        jsonResult.setData(result.getSuccess());
        return jsonResult;
    }

    /**
     * BOM零配件列表-删除行
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteBomSub", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteBomSub(HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        String id = request.getParameter("id");
        BomSub dbBomSub = bomSubService.getById(Long.parseLong(id));
        if (dbBomSub == null) {
            log.error("[BomController][deleteBomSub]BOM SUB不存在，操作失败！bomSubId={}", id);
            jsonResult.setMessage("BOM零配件数据不存在，操作失败！");
            return jsonResult;
        }
        BomMain dbBomMain = bomMainService.getById(dbBomSub.getBomId());
        if (dbBomMain == null) {
            log.error("[BomController][deleteBomSub]BOM不存在，操作失败！bomId={}", dbBomSub.getBomId());
            jsonResult.setMessage("BOM不存在，操作失败！");
            return jsonResult;
        }
        if (BomMain.StatusEnum.CLOSED.getStatus().equals(dbBomMain.getStatus())) {
            log.error("[BomController][deleteBomSub]该BOM已结案，操作失败！bomId={}", id);
            jsonResult.setMessage("该BOM已结案，操作失败！");
            return jsonResult;
        }
        ServiceResult<Integer> subResult = bomSubService.deleteById(dbBomSub.getId());
        if (!subResult.getSuccess()) {
            log.error("[BomController][deleteBomSub]更新bomSub.isDelete失败！bomId={}", id);
            jsonResult.setMessage("操作失败！");
            return jsonResult;
        }
        jsonResult.setData(subResult.getSuccess());
        return jsonResult;
    }

    /**
     * 提交保存发货数量
     * @param request
     * @return
     */
    @RequestMapping(value = "/commitDeliveryAmount", method = RequestMethod.POST)
    @ResponseBody
    public Object commitDeliveryAmount(HttpServletRequest request) {
        HttpJsonResult<Object> jsonResult = new HttpJsonResult<Object>();
        List<BomDeliveryDetail> bomDeliveryDetailList = new ArrayList<BomDeliveryDetail>();
        Long bomId = null;
        String nickName = getCurrentUserNickName(request);
        String jsonDataStr = request.getParameter("jsonDataStr");
        List<BomSub> list = JSONArray.parseArray(jsonDataStr, BomSub.class);
        for(int i = 0; i < list.size(); i++){
            BomDeliveryDetail bomDeliveryDetail = new BomDeliveryDetail();
            if(i == 0){
                bomId = list.get(i).getBomId();
            }
            bomDeliveryDetail.setBomId(bomId);
            bomDeliveryDetail.setSerialNo(list.get(i).getSerialNo());
            bomDeliveryDetail.setName(list.get(i).getName());
            bomDeliveryDetail.setBrand(list.get(i).getBrand());
            bomDeliveryDetail.setSpecifications(list.get(i).getSpecifications());
            bomDeliveryDetail.setUnit(list.get(i).getUnit());
            bomDeliveryDetail.setDeliveryDate(new Date());
            bomDeliveryDetail.setDeliveryAmount(list.get(i).getCurrentDeliveryAmount());
            bomDeliveryDetail.setDeliveryBy(nickName);
            bomDeliveryDetail.setCreatedBy(nickName);
            bomDeliveryDetail.setUpdatedBy(nickName);
            bomDeliveryDetailList.add(bomDeliveryDetail);
        }
        if(bomDeliveryDetailList.size() > 0){
            ServiceResult<Integer> batchInsertResult = bomDeliveryDetailService.batchInsert(bomDeliveryDetailList);
            if(!batchInsertResult.getSuccess()){
                log.error("[BomController][commitDeliveryAmount]提交保存发货数量失败,error={}", batchInsertResult.getMessage());
                jsonResult.setMessage(batchInsertResult.getMessage());
                return jsonResult;
            }
        }
        BomMain bomMain = null;
        if(bomId != null){
            bomMain = bomMainService.getById(bomId);
        }
        if(bomMain == null){
            log.error("[BomController][commitDeliveryAmount]提交保存发货数量失败,BOM主数据为空!bomId={}", bomId);
            jsonResult.setMessage("提交保存发货数量失败,BOM主数据为空");
            return jsonResult;
        }
        //更新BomSub的已发货数量
        for(int i = 0; i < list.size(); i++){
            BomSub bomSub = new BomSub();
            bomSub.setId(list.get(i).getId());
            bomSub.setSingleAmount(list.get(i).getSingleAmount());
            bomSub.setTotalAmount(bomSub.getSingleAmount() * bomMain.getNum());
            bomSub.setCurrentDeliveryAmount(list.get(i).getCurrentDeliveryAmount());
            bomSubService.updateDeliveryAmount(bomSub);
        }
        jsonResult.setData(true);
        return jsonResult;
    }

    @RequestMapping(value = { "bomDeliveryDetailList" })
    public void bomDeliveryDetailList(HttpServletRequest request, HttpServletResponse response) {
        Map <String, Object> criteria = Maps.newHashMap();
        try {
            String bomId = request.getParameter("bomId");
            if(bomId != null && !"".equals(bomId)){
                criteria.put("bomId", Long.parseLong(bomId));
            }
            String deliveryDateStart = request.getParameter("deliveryDateStart");
            if(deliveryDateStart != null && !"".equals(deliveryDateStart)){
                criteria.put("deliveryDateStart", CommUtil.getStringToDate(deliveryDateStart + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
            }
            String deliveryDateEnd = request.getParameter("deliveryDateEnd");
            if(deliveryDateEnd != null && !"".equals(deliveryDateEnd)){
                criteria.put("deliveryDateEnd", CommUtil.getStringToDate(deliveryDateEnd + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
            }
            Map<String, Object> dataMap = new HashMap<String, Object>();
            PagerInfo pager = WebUtil.handlerPagerInfo(request, dataMap);
            ServiceResult<Map<String, Object>> serviceResult = bomDeliveryDetailService.getBomDeliveryDetailList(criteria, pager);
            if(serviceResult.getSuccess()){
                Map<String, Object> map = serviceResult.getResult();
                if(map!=null&&map.size()>0){
                    List<BomDeliveryDetail> list = (List<BomDeliveryDetail>)map.get("data");
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
            log.error("[BomController][bomDeliveryDetailList]BOM发货明细列表查询失败,error={}", e.getMessage());
            throw new BusinessException("BOM发货明细列表查询失败" + e.getMessage());
        }
    }

}  