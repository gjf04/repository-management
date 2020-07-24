<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="/security-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>资源列表</title>
</head>
<body>
<div class="easyui-layout" fit="true">
	<div region="north" border="false" collapsible="true" collapsed="false"
		 class="zoc" title="查询条件" style="height: 60px; overflow: auto;">
		<form onsubmit="return false;" id="searchForm">
			<table>
				<tr>
					<td>资源名称:</td>
					<td><input name="resource.name"/></td>
					<td>资源类型:</td>
					<td><select id="typeSL" name="resource.type" class="easyui-combobox" data-options="editable:false,required:true">
						<option value="9">全部</option>
						<option value="0">URL资源</option>
						<option value="1">组件资源</option>
					</select></td>
					<td><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="loaddata()">查询</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div region="center" border="false">
		<table id="dg">
			<thead>
			<tr>
				<th data-options="field:'ck',checkbox:true"></th>
				<th data-options="field:'name',formatter:resourceDetailUrlFormatter">资源名称</th>
				<th field="code">资源编码</th>
				<th field="description">资源描述</th>
				<th data-options="field:'type',formatter:typeFormater">类型</th>
				<th data-options="field:'status',formatter:statusFormater">状态</th>
				<th data-options="field:'iconUrl',">图标地址</th>
				<th data-options="field:'width',">窗口宽度</th>
				<th data-options="field:'height',">窗口高度</th>
				<!-- <th data-options="field:'fileId',formatter:imageFormater">图标</th> -->
			</tr>
			</thead>
		</table>
		<div id="tb" style="padding:5px;height:auto">
			<div style="margin-bottom:5px">
				<a href="${dynamicURL}/security/createResourceInit.action" class="easyui-linkbutton" iconCls="icon-add" plain="true">新增</a>
				<security:auth code="SECURITY_DELETE_RESOURCE">
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delResource()">删除</a>
				</security:auth>
			</div>
		</div>
		<form method="post" id="fm" style="display: none;"></form>
	</div>
</div>
<script type="text/javascript">
	function loaddata(){
		$('#dg').datagrid('load',sy.serializeObject($("#searchForm").form()));
	}
	function formatDateTime(val,row){
		if(!val.time){
			return "";
		}
		var date = new Date(val.time);
		return defaultDateTimeFormatter(date);
	}
	var typeMap = {'1':'组件资源','0':'URL资源'};
	function typeFormater(val,row){
		return typeMap[val];
	}
	var statusMap = {"1":'显示','0':'隐藏'};
	function statusFormater(val,row){
		return statusMap[val];
	}
	function resourceDetailUrlFormatter(val,row){
		return "<a href='${dynamicURL}/security/updateResourceInit.action?resource.id="+row.id+"'>"+val+"</a>";
	}
	function imageFormater(val,row){
		if(val == null){
			return "";
		}
		return "<img src='${dynamicURL}/security/download.action?id="+val+"'></img";
	}
	$(function(){
		$('#dg').datagrid({
			title:'资源列表',
			toolbar:'#tb',
			singleSelect:true,
			fitColumns:true,
			fit:true,
			collapsible: true,
			rownumbers: true, //显示行数 1，2，3，4...
			pagination: true, //显示最下端的分页工具栏
			pageList: [5,10,15,20], //可以调整每页显示的数据，即调整pageSize每次向后台请求数据时的数据
			pageSize: 20, //读取分页条数，即向后台读取数据时传过去的值
			url:'${dynamicURL}/security/searchResource.action',
		});
	});
	function delResource(){
		var row = $('#dg').datagrid('getSelected');
		if (!row){
			$.messager.alert('操作提示','请选择要删除的数据！','info');
			return;
		}
		$('#fm').form('submit',{
			url: '${dynamicURL}/security/deleteResource.action?resourceId='+row.id,
			onSubmit: function(){
				return $(this).form('validate');
			},
			success: function(result){
				handleActionResult(result,{
					onSuccess:function(){
						$('#dg').datagrid('reload');//重新加载数据
					}
				});
			}
		});
	}
</script>
</body>
</html>
