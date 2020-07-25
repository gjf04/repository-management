package com.platform.entity.system;

import com.platform.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 部门实体类
 * Created by GaoJingFei on 2020/07/25.
 */
@ToString(callSuper=true)
@Getter
@Setter
public class Department extends BaseEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3628838287611717188L;

	private String code;

    private String name;

    private Long principalUserId;

    private String principalNickName;

    private String description;

    private String address;

    private Department parent;

}