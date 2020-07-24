package com.platform.service.system;

import com.platform.entity.system.RoleResource;
import com.gao.common.ServiceResult;

import java.util.List;

/**
 * Created by GaoJingFei on 2017/11/13.
 */

public interface RoleResourceService {


	public ServiceResult<List<RoleResource>> selectAllByRoleId(Long roleId);
	
	public ServiceResult<Integer> insert(RoleResource roleResource);
	
	public ServiceResult<Integer> deleteByRoleId(Long roleId);

	public ServiceResult<List<RoleResource>> getByRoleIdAndResourceId(RoleResource roleResource);
}
