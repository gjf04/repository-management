package com.platform.entity.util;

import com.platform.entity.system.Department;
import org.apache.commons.lang.ObjectUtils;

/**
 * 用来构建用户可访问的组织树形结构
 * @author GaoJingFei
 *
 */
public class DepartmentTreeNodeFactory extends TreeNodeFactory<Department> {

	@Override
	protected String getNodeId(Department element) {
		return String.valueOf(element.getId());
	}

	@Override
	protected String getParentId(Department element) {
		Department parent = element.getParent();
		if(parent == null){
			return null;
		}
		return ObjectUtils.toString(parent.getId());
	}

	@Override
	protected TreeNode buildTreeNode(Department element) {
		TreeNode node = new TreeNode();
		node.setId(getNodeId(element));
		node.setText(element.getName());
		node.getAttributes().put("code", element.getCode());
		node.getAttributes().put("description", element.getName());
		return node;
	}
}
