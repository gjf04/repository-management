package com.platform.entity.system;

import com.platform.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;
@ToString(callSuper=true)
@Getter
@Setter
public class DeviceUploadData extends BaseEntity {

    private String serialNo;

    private String simCode;

    private String telecomOperator;

    private BigDecimal dbm;

    private BigDecimal power;

    private BigDecimal boxTemperature;

    private BigDecimal residualCurrent;

    private BigDecimal aVoltage;

    private BigDecimal aTemperature;

    private BigDecimal aCurrent;

    private BigDecimal bVoltage;

    private BigDecimal bTemperature;

    private BigDecimal bCurrent;

    private BigDecimal cVoltage;

    private BigDecimal cTemperature;

    private BigDecimal cCurrent;

    private Integer smokeAlarm;

    private Integer infraredAlarm;

    private Integer airSwitch;

    private Integer alarm;

    private Date collectTime;

}