package com.platform.entity.util;

import com.platform.entity.system.Department;

/**
 * 用来构建用户可访问的资源树形结构
 * @author GaoJingFei
 *
 */
public class ClosedDepartmentTreeNodeFactory extends DepartmentTreeNodeFactory {

	@Override
	protected TreeNode buildTreeNode(Department element) {
		TreeNode node = super.buildTreeNode(element);
		node.setState("open");
		return node;
	}
}
