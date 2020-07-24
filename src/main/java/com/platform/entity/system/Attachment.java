package com.platform.entity.system;

import com.platform.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 附件实体类
 * Created by GaoJingFei on 2017/11/13.
 */
@ToString(callSuper=true)
@Getter
@Setter
public class Attachment  extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -9086126311607335864L;

	private String fileCode;

    private String fileName;

    private Boolean fileType;

    private String url;

}