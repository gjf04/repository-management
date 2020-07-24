package com.platform.entity.system;

import com.platform.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 系统日志实体类
 * Created by GaoJingFei on 2017/11/13.
 */
@ToString(callSuper=true)
@Getter
@Setter
public class SysLog  extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5364982612282718212L;

	private String userName;

    private String ip;

    private String pageName;

    private String eventName;

}