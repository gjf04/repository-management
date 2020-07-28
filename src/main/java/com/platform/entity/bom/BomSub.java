package com.platform.entity.bom;

import com.platform.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * BOM子表实体类
 * Created by GaoJingFei on 2020/07/27.
 */
@ToString(callSuper=true)
@Getter
@Setter
public class BomSub extends BaseEntity{
    /**
     *
     */
    private static final long serialVersionUID = -1647851620504858307L;

    private Long bomId;

    private String serialNo;

    private String name;

    private String brand;

    private String specifications;

    private String unit;

    private Integer singleAmount;

    private Integer totalAmount;

    private Integer stockAmount;

    private Integer stockUpAmount;

    private Integer purchaseAmount;

    private String deliveryDate;

    private Integer deliveryAmount;

}