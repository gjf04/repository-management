package com.platform.entity.util;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 通用树形组件，联合easyui中的tree组件使用
 * @author GaoJingFei
 * @since 3.0
 */
public class TreeNode implements Serializable {
	/**
	 * 节点ID
	 */
	private String id;
	/**
	 * 节点文案
	 */
	private String text;
	/**
	 * 节点状�?
	 */
	private String state;
	/**
	 * 是否选中
	 */
	private String checked;
	/**
	 * 节点属�?信息
	 */
	private Map<String, String> attributes = new LinkedHashMap<String, String>();
	/**
	 * 子节�?
	 */
	private Set<TreeNode> children = new LinkedHashSet<TreeNode>();
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public Set<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(Set<TreeNode> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[id=");
		builder.append(this.getId());
		builder.append(",text=");
		builder.append(this.getText());
		builder.append(",state=");
		builder.append(this.getState());
		builder.append(",checked=");
		builder.append(this.getChecked());
		builder.append(",attributes=");
		builder.append(this.getAttributes());
		builder.append(",children={");
		for(TreeNode treeNode : this.children){
			builder.append(treeNode.toString());
		}
		builder.append("}]");
		return builder.toString();
	}
}
