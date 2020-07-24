package com.platform.entity.system;

import com.platform.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户部门实体类
 * Created by GaoJingFei on 2017/11/13.
 */
@ToString(callSuper=true)
@Getter
@Setter
public class UserDepartment  extends BaseEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4854415618775587512L;

	private Long userId;

    private Long departmentId;

}