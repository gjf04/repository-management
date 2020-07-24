package com.platform.activemq;

import com.alibaba.fastjson.JSONObject;
import com.gao.common.ServiceResult;
import com.gao.common.util.DateUtil;
import com.platform.entity.system.DeviceUploadData;
import com.platform.service.system.DeviceUploadDataService;
import com.platform.service.system.ResourceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
@Slf4j
@Component
public class ActiveMQConsumer {
    @Resource
    private DeviceUploadDataService deviceUploadDataService;

    //@JmsListener(destination="springboot.queue")
    public void ListenQueue(String msg){
        log.info("接收到queue消息：{}" , msg);
    }

    //@JmsListener(destination="sc.electricity.platform", containerFactory = "jmsTopicListenerContainerFactory")
    public void ListenTopic(String msg){
        log.info("接收到topic消息：{}", msg);
        JSONObject dataJson = (JSONObject) JSONObject.parseObject(msg);
        String serialNo = dataJson.get("serialNo") == null ? "" : dataJson.getString("serialNo");
        String simCode = dataJson.get("simCode") == null ? "" : dataJson.getString("simCode");
        String telecomOperator = dataJson.get("telecomOperator") == null ? "" : dataJson.getString("telecomOperator");
        String dbm = dataJson.get("dbm") == null ? "" : dataJson.getString("dbm");
        String power = dataJson.get("power") == null ? "" : dataJson.getString("power");
        String boxTemperature = dataJson.get("boxTemperature") == null ? "" : dataJson.getString("boxTemperature");
        String residualCurrent = dataJson.get("residualCurrent") == null ? "" : dataJson.getString("residualCurrent");
        String aVoltage = dataJson.get("aVoltage") == null ? "" : dataJson.getString("aVoltage");
        String aTemperature = dataJson.get("aTemperature") == null ? "" : dataJson.getString("aTemperature");
        String aCurrent = dataJson.get("aCurrent") == null ? "" : dataJson.getString("aCurrent");
        String bVoltage = dataJson.get("bVoltage") == null ? "" : dataJson.getString("bVoltage");
        String bTemperature = dataJson.get("bTemperature") == null ? "" : dataJson.getString("bTemperature");
        String bCurrent = dataJson.get("bCurrent") == null ? "" : dataJson.getString("bCurrent");
        String cVoltage = dataJson.get("cVoltage") == null ? "" : dataJson.getString("cVoltage");
        String cTemperature = dataJson.get("cTemperature") == null ? "" : dataJson.getString("cTemperature");
        String cCurrent = dataJson.get("cCurrent") == null ? "" : dataJson.getString("cCurrent");
        String smokeAlarm = dataJson.get("smokeAlarm") == null ? "" : dataJson.getString("smokeAlarm");
        String infraredAlarm = dataJson.get("infraredAlarm") == null ? "" : dataJson.getString("infraredAlarm");
        String airSwitch = dataJson.get("airSwitch") == null ? "" : dataJson.getString("airSwitch");
        String alarm = dataJson.get("alarm") == null ? "" : dataJson.getString("alarm");
        String collectTime = dataJson.get("collectTime") == null ? "" : dataJson.getString("collectTime");
        DeviceUploadData deviceUploadData = new DeviceUploadData();
        deviceUploadData.setSerialNo(serialNo);
        deviceUploadData.setSimCode(simCode);
        deviceUploadData.setTelecomOperator(telecomOperator);
        deviceUploadData.setDbm(new BigDecimal(dbm));
        deviceUploadData.setPower(new BigDecimal(power));
        deviceUploadData.setBoxTemperature(new BigDecimal(boxTemperature));
        deviceUploadData.setResidualCurrent(new BigDecimal(residualCurrent));
        deviceUploadData.setAVoltage(new BigDecimal(aVoltage));
        deviceUploadData.setATemperature(new BigDecimal(aTemperature));
        deviceUploadData.setACurrent(new BigDecimal(aCurrent));
        deviceUploadData.setBVoltage(new BigDecimal(bVoltage));
        deviceUploadData.setBTemperature(new BigDecimal(bTemperature));
        deviceUploadData.setBCurrent(new BigDecimal(bCurrent));
        deviceUploadData.setCVoltage(new BigDecimal(cVoltage));
        deviceUploadData.setCTemperature(new BigDecimal(cTemperature));
        deviceUploadData.setCCurrent(new BigDecimal(cCurrent));
        deviceUploadData.setSmokeAlarm(Integer.parseInt(smokeAlarm));
        deviceUploadData.setInfraredAlarm(Integer.parseInt(infraredAlarm));
        deviceUploadData.setAirSwitch(Integer.parseInt(airSwitch));
        deviceUploadData.setAlarm(Integer.parseInt(alarm));
        deviceUploadData.setCollectTime(DateUtil.parse(collectTime, "yyyy-MM-dd HH:mm:ss"));
        ServiceResult<DeviceUploadData> result = deviceUploadDataService.createDeviceUploadData(deviceUploadData);
        if(result.getSuccess()){
            log.info("设备状态数据保存成功！序列号：{}, 时间：{}" ,serialNo, collectTime);
        }else{
            log.error("设备状态数据保存失败！序列号：{}, 时间：{}, error:{}", serialNo, collectTime, result.getMessage());
        }
    }
}