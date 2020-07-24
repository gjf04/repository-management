package com.platform.entity.system;

import com.platform.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色实体类
 * Created by GaoJingFei on 2017/11/13.
 */
@ToString(callSuper=true)
@Getter
@Setter
public class Role  extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -550322401297851576L;
    public static final long BUSINESS_ROLE_ID = 5L;//业务人员角色id

	private String code;

    private String name;

    private String description;

}