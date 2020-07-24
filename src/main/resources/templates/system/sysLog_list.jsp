<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="/security-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="${staticURL}/hzscripts/enterSearch.js"></script>
	<title>日志管理</title>
	<style type="text/css"></style>

</head>
<body >
<div id="layout" class="easyui-layout" fit="true">
	<div region="north" border="false" collapsible="true" collapsed="false"
		 class="zoc" title="查询条件" style="height: 60px; overflow: inherit;">
		<form onsubmit="return false;" id="searchForm">
			<table id="searchTable" class="fixedTb">
				<tr>
					<td class="cxlabel">用户名称:</td>
					<td class="cxinput">
						<%-- <input id="searchArea" class="easyui-combobox" name="model.areaCode" style="width:160px;"
                                    data-options="valueField:'id',textField:'text',editable:false,panelHeight:200,url:'${dynamicURL}/basic/departmentCombox.action?type=010101'" /> --%>
						<input id="searchUserName" class="easyui-textbox" name="model.userName" style="width:100%;"/>
					</td>
					<!-- <td class="formlabletd">创建时间:</td>
                    <td>
                         <input id="searchYear" class="easyui-combobox" name="model.year"
                            data-options="valueField:'id',textField:'text'" />
                    </td> -->
					<td class="cxlabel">
						<a id="searchPt" class="easyui-linkbutton" iconCls="icon-search" onclick="loaddata()">查询</a>
					</td>
					<td class="cxlabel"></td>
					<td class="cxinput"></td>
					<td class="cxlabel"></td>
					<td class="cxinput"></td>
					<td class="cxlabel"></td>
					<td class="cxinput"></td>
				</tr>
			</table>
		</form>
	</div>

	<div region="center" border="false">
		<table id="dg">
			<thead>
			<tr>
				<th data-options="field:'userName',halign:'center',align:'left',width:40">用户名称</th>
				<th data-options="field:'userIp',halign:'center',align:'center',width:40">当前登录IP</th>
				<th data-options="field:'eventName',halign:'center',align:'left',width:50">事件名称</th>
				<th data-options="field:'remark',halign:'center',align:'left',width:120">备注说明</th>
				<th data-options="field:'create',halign:'center',align:'center',width:40,formatter:formatDateTime">创建时间</th>
			</tr>
			</thead>
		</table>
	</div>

	<div id="tb" >
		<a class="easyui-linkbutton" iconCls="icon-excel" plain="false" onclick="exportsyslog()">导出</a>
	</div>
</div>

<div style="display:none;">
	<form id="exportForm" method="post" action="${dynamicURL}/system/exportSysLog.action">
		<input type="hidden" name="model.userName">
	</form>
</div>

<script type="text/javascript">
	function formatDateTime(val, row) {
		if (!val.time) {
			return "";
		}
		var date = new Date(val.time);
		return defaultDateTimeFormatter(date);
	}
	// 格式化位数
	function formatMoney(s, n) {
		n = n >= 0 && n <= 20 ? n : 2;
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
		t = "";
		for (i = 0; i < l.length; i++) {
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
		}
		return t.split("").reverse().join("") + "." + r;
	}
	function yearFormater(val, row) {
		if (val == 0) {
			return '';
		}
		return val;
	}
	function formatFeeRate(val, row) {
		if (val == 0 || val == null) {
			return '';
		}
		return formatMoney(val, 4);
	}
	function totalFormater(val, row) {
		if (val == 0) {
			return '';
		}
		return formatMoney(val);
	}
	/* var syslogTotal = {
	 'userName' : '',
	 'userIp' : '',
	 'eventName' : '',
	 'remark' : '',
	 'create' : ''
	 }; */

	function loaddata() {
		$('#dg').datagrid('load', sy.serializeObject($("#searchForm").form()));
	}
	/* 	// 清空数据
	 function zeroTotal() {
	 syslogTotal = {
	 'userName' : '',
	 'userIp' : '',
	 'eventName' : '',
	 'remark' : '',
	 'create' : ''
	 };
	 } */

	$(function() {
		//年份下拉数据
		/* var yeardata = [];
		 var date = new Date();
		 var year = date.getFullYear();
		 for(var i=year-5;i<year+4;i++){
		 yeardata.push({
		 id:i,
		 text:i
		 });
		 }
		 $('#searchYear').combobox({
		 data : yeardata
		 });
		 $('#searchYear').combobox('select',year); */
		$('#dg').datagrid({
			title : '日志管理<label style="text-align: right!important;"></label>',
			toolbar : '#tb',
			singleSelect : true,
			striped : true,
			fit : true,
			fitColumns : true,
			collapsible : true,
			rownumbers : true, // 显示行数 1，2，3，4...
			pagination : true, // 显示最下端的分页工具栏
			pagePosition : 'bottom',
			pageList : [ 5, 10, 15, 20 ], // 可以调整每页显示的数据，即调整pageSize每次向后台请求数据时的数据
			pageSize : 20, // 读取分页条数，即向后台读取数据时传过去的值
			url : '${dynamicURL}/system/searchSysLog.action',
			nowrap : true,
			border : false,

			rowStyler : function(index, row) {
				/*  if (row.areaName == '总计') {
				 return 'background-color:#C1D9F3;';
				 }  */
			}
		});
	});

	// 导出
	function exportsyslog() {
		var rows = $('#dg').datagrid('getRows');
		if(rows.length>0){
			var data = {
				'model.userName' : $('#searchUserName').val()
				/* 'model.year' : $('#create').combobox('getValue') */
			};
			$('#exportForm').form('load',data).submit();
		}else{
			$.messager.alert('提示','无可导出记录！','warning');
		}
	}
</script>
</body>
</html>
