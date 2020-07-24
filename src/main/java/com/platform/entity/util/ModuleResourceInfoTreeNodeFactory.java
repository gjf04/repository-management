package com.platform.entity.util;

import com.platform.entity.system.ResourceInfo;

/**
 * 用来构建用户可访问的资源树形结构
 * @author GaoJingFei
 *
 */
public class ModuleResourceInfoTreeNodeFactory extends TreeNodeFactory<ResourceInfo> {

	@Override
	protected String getNodeId(ResourceInfo element) {
		return String.valueOf(element.getId());
	}

	@Override
	protected String getParentId(ResourceInfo element) {
		return element.getParentId().toString();
	}

	@Override
	protected TreeNode buildTreeNode(ResourceInfo element) {
		TreeNode node = new TreeNode();
		node.setId(element.getId().toString());
		node.setText(element.getName());
		node.getAttributes().put("code", element.getCode());
		node.getAttributes().put("description", element.getDescription());
		node.getAttributes().put("url", element.getUrl());
		node.getAttributes().put("type", String.valueOf(element.getType()));
		node.getAttributes().put("orderIndex", String.valueOf(element.getOrderIndex()));
		return node;
	}

}
