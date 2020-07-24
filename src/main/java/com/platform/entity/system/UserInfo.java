package com.platform.entity.system;

import com.platform.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 用户实体类
 * Created by GaoJingFei on 2017/11/13.
 */
@ToString(callSuper=true)
@Getter
@Setter
public class UserInfo  extends BaseEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1649691620709858307L;

	private String userName;

    private String password;

    private Integer status;

    private String sex;

    private String mobile;

    private String email;

    private String nickName;

    /**
     * 生日
     */
    private Date birthday;

    private String identityNo;

    private String address;

    private Long parentId;

    private String parentNickName;

    private String roleName;

    private String departmentName;

    public static enum StatusEnum{
        INIT(0),ENABLE(1),DISABLE(-1),LOCKED(-2);
        @Getter
        private Integer status;
        private StatusEnum(Integer status) {
            this.status = status;
        }
    }

}