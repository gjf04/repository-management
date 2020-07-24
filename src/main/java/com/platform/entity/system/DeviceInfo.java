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
public class DeviceInfo extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1649691620709788307L;

	private String type;

    private String serialNo;

    private String name;

    private Integer status;

    private String ip;

    private Long departmentId;

    private String departmentName;

    private Long parentId;

    private String parentNickName;

    private String simNo;

    private String version;

    private Date activeAt;

    private Integer isInstall;

    private String provinceName;

    private String provinceCode;

    private String cityName;

    private String cityCode;

    private String regionName;

    private String regionCode;

    private String address;

    private String position;

    private BigDecimal longitude;

    private BigDecimal latitude;

    public static enum StatusEnum{
        INIT(0),ENABLE(1),OFFLINE(2),EARLY_WARN(3),WARN(4);
        @Getter
        private Integer status;
        private StatusEnum(Integer status) {
            this.status = status;
        }
    }

}