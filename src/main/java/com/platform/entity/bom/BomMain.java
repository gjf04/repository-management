package com.platform.entity.bom;

import com.platform.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * BOM主表实体类
 * Created by GaoJingFei on 2020/07/27.
 */
@ToString(callSuper=true)
@Getter
@Setter
public class BomMain extends BaseEntity{
    /**
     *
     */
    private static final long serialVersionUID = -1649691620504858307L;

	private String customerCode;

    private String pmName;

    private String deviceName;

    private String meName;

    private String deliveryDate;

    private String productionDate;

    private Integer num;

    private Integer status;

    private Date closedAt;

    private String closedBy;

    public static enum StatusEnum{
        UNCLOSED(0),CLOSED(1);
        @Getter
        private Integer status;
        private StatusEnum(Integer status) {
            this.status = status;
        }
    }

}