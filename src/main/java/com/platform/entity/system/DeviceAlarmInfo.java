package com.platform.entity.system;

import com.platform.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@ToString(callSuper=true)
@Getter
@Setter
public class DeviceAlarmInfo extends BaseEntity {

    private String serialNo;

    private Byte type;

    private String description;

    private Date collectTime;

    private Integer settleUserId;

    private String settleNickName;

    private String settleType;

    private Byte isSettle;

    private Byte isScene;

    private String serviceType;

    private String settleRecord;

    private Date settleTime;


}