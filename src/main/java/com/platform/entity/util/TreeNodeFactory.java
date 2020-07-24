package com.platform.entity.util;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用来构建树形结构的工厂接口
 * @author GaoJingFei
 * @since 3.0
 */
public abstract class TreeNodeFactory<T> {
	public List<TreeNode> buildTreeNodeList(List<T> objects){
		List<TreeNode> result = new ArrayList<TreeNode>();
		//将原始列表元素转化成树节点放到map中，key:树节点id,value树节点
		Map<String, TreeNode> treeNodeMap = new LinkedHashMap<String,TreeNode>();
		for(T element : objects){
			TreeNode treeNode = this.buildTreeNode(element);
			treeNodeMap.put(getNodeId(element), treeNode);
		}

		//组合父子关系
		for(T element : objects){
			String parentId = this.getParentId(element);
			String nodeId = getNodeId(element);
			//父节点为空或者父节点id与当前节点id一致的表示为根节点
			if(StringUtils.isBlank(parentId) || StringUtils.equals(parentId, nodeId)){
				result.add(treeNodeMap.get(nodeId));
			}else{
				if(treeNodeMap.containsKey(parentId)){//当前节点父节点信息在列表中
					TreeNode parentNode = treeNodeMap.get(parentId);
					parentNode.getChildren().add(treeNodeMap.get(nodeId));
					parentNode.setState("closed");
				}else{//有父节点，但父节点未在给定的列表中，视为根节点
					result.add(treeNodeMap.get(nodeId));
				}
			}
		}
		return result;
	}
	protected abstract String getNodeId(T element);
	protected abstract String getParentId(T element);
	protected abstract TreeNode buildTreeNode(T element);
}
