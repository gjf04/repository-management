package com.platform.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 所有实体类的基类
 * @author gaojingfei
 *
 */
@ToString
@Getter
@Setter
public class BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4055603087217089041L;
	/**
	 * 主键自增长ID
	 */
	private Long id;
	/**
	 * 记录创建人
	 */
	private String createdBy;
	/**
	 * 记录创建时间
	 */
	private Date createdAt;
	/**
	 * 记录最后修改人
	 */
	private String updatedBy;
	/**
	 * 最后修改时间
	 */
	private Date updatedAt;
	/**
	 * 系统 保留字段，用来记录诸如 数据 迁移的备注等
	 */
	private String remark;
	/**
	 * 是否删除 1:是   0:否
	 */
	private Integer isDelete;
	
	public static enum IsDeleteEnum{
		/**
		 * 是否删除 1:是   0:否
		 */
		NO(0),YES(1);
		@Getter
		private Integer isDelete;
		private IsDeleteEnum(Integer isDelete) {
			this.isDelete = isDelete;
		}
	}
}
