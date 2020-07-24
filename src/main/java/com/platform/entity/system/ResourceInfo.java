package com.platform.entity.system;

import com.platform.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 资源实体类
 * Created by GaoJingFei on 2017/11/13.
 */
@ToString(callSuper=true)
@Getter
@Setter
public class ResourceInfo  extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2409219216758363466L;

	private String code;

    private String name;

    private String description;

    private String url;

    private Integer type;//类别 0:模块 1:页面  2:按钮

    private Integer orderIndex;

    private Long parentId;

    private List<ResourceInfo> children;

    public static enum TypeEnum{
        /**
         * 类别 0:模块 1:页面  2:按钮
         */
        MODULE(0),PAGE(1),BUTTON(2);
        @Getter
        private Integer type;
        private TypeEnum(Integer type) {
            this.type = type;
        }
    }

}