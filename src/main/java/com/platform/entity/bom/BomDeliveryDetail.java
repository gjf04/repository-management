package com.platform.entity.bom;

import com.platform.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * BOM发货明细实体类
 * Created by GaoJingFei on 2020/07/28.
 */
@ToString(callSuper=true)
@Getter
@Setter
public class BomDeliveryDetail extends BaseEntity{
    /**
     *
     */
    private static final long serialVersionUID = -1647851620613858307L;

    private Long bomId;

    private String serialNo;

    private String name;

    private String brand;

    private String specifications;

    private String unit;

    private Date deliveryDate;

    private Integer deliveryAmount;

    private String deliveryBy;

}