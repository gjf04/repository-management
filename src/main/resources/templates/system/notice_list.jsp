<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="security" uri="/security-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<script type="text/javascript" src="${staticURL}/hzscripts/enterSearch.js"></script>
	<style type="text/css">
		._oneline{
			width :100%;
			height:30px;
		}
		._item50{
			width : 50%;
			float : left;
		}
		._item100{
			width : 100%;
			float : left;
		}
		._lefttext{
			width : 20%;
			text-align: right;
			float : left;
			padding-right:10px;
		}
		._righttext{
			width:70%;
			float : left;
		}
	</style>
	<title>公告管理查询</title>
</head>
<body >
<div id="layout" class="easyui-layout" fit="true">
	<div data-options="region:'north',border:false"
		 class="zoc" title="查询条件" style="height: 60px; overflow: auto;" >
		<form onsubmit="return false;" id="searchForm">
			<table class="fixedTb">
				<tr>
					<td class="cxlabel">开始时间:</td>
					<td class="cxinput">
						<input id="start" name="notice.start"  data-options="sharedCalendar:'#sc',editable:false" style="width:100%;">
						<div id="sc" class="easyui-calendar"></div>
					</td>
					<td class="cxlabel">结束时间:</td>
					<td class="cxinput">
						<input id="end" name="notice.end" data-options="sharedCalendar:'#sc',editable:false" style="width:100%;">
					</td>
					<td class="cxlabel">公告标题:</td>
					<td class="cxinput">
						<input name="notice.title" class="easyui-textbox" style="width:100%;">
					</td>
					<td class="cxlabel">
						<a id="searchPt" href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-search'" onclick="loaddata()">查询</a>
					</td>
					<td class="cxlabel"></td>
					<td class="cxinput"></td>
				</tr>
			</table>
		</form>
	</div>

	<div  data-options="region:'center',border:false">
		<table id="dg">
			<thead>
			<tr>
				<th data-options="field:'id',checkbox:true"></th>
				<th data-options="field:'title',width:800,align:'left',halign:'center'">公告标题</th>
				<th data-options="field:'start',width:200,align:'center',formatter:formatDateTime">开始时间</th>
				<th data-options="field:'end',width:200,align:'center',formatter:formatDateTime">结束时间</th>
				<th data-options="field:'createByName',width:200,align:'left',align:'left',halign:'center'">创建人</th>
				<th data-options="field:'create',width:200,align:'center',formatter:formatDateTime">创建时间</th>
			</tr>
			</thead>
		</table>
		<form method="post" id="fm" style="display: none;"></form>
		<div id="tb" >
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="false" onclick="delNotice();">删除</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="false" onclick="updateNotice();">修改</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="false" onclick="addNotice();">新增</a>
		</div>
	</div>
	<div id="win" class="easyui-dialog" style="width:570px;height:455px;padding: 2px 2px 2px 2px"
		 data-options="iconCls:'icon-save',modal:true,closed:true,buttons:'#bb',resizable:false">
		<form id="ff" method="post">
			<input id="notice_id" type="hidden" name="notice.id" />
			<div class="_oneline">
				<div class="_item100">
					<div class="_lefttext">所属部门<font color="red">*</font>:</div>
					<div class="_righttext">
						<input name="departmentIds" id="departmentIds" class="easyui-combotree"  style="width:100%"
							   data-options="
								multiple:true,
								url: '${dynamicURL}/basic/departmentTree.action',
								animate: true,required:true"/>
					</div>
				</div>
			</div>
			<div class="_oneline">
				<div class="_item100">
					<div class="_lefttext">所属岗位<font color="red">*</font>:</div>
					<div class="_righttext">
						<select id="userRoleCodes" name="userRoleCodes" class="easyui-combotree" style="width:100%;"
								data-options="multiple:true,required:true,url:'${dynamicURL}/security/searchRoleCombo.action'"></select>
					</div>
				</div>
			</div>
			<div class="_oneline">
				<div class="_item100">
					<div class="_lefttext">公告标题<span style="color:red;">*</span>:</div>
					<div class="_righttext">
						<input id="notice_title" class="easyui-textbox" name="notice.title" data-options="required:true,missingMessage:'该输入项为必输项'" style="width: 100%;"></input>
					</div>
				</div>
			</div>
			<div class="_oneline">
				<div class="_item100">
					<div class="_lefttext">开始时间<span style="color:red;">*</span>:</div>
					<div class="_righttext">
						<input id="notice_start" name="notice.start" type="text" class="easyui-datebox" data-options="sharedCalendar:'#sc',required:true,editable:false" style="width: 100%;"></input>
					</div>
				</div>
			</div>
			<div class="_oneline">
				<div class="_item100">
					<div class="_lefttext">结束时间<span style="color:red;">*</span>:</div>
					<div class="_righttext">
						<input id="notice_end" name="notice.end" type="text" class="easyui-datebox" data-options="sharedCalendar:'#sc',required:true,editable:false" style="width: 100%;"></input>
					</div>
				</div>
			</div>
			<div class="_oneline">
				<div class="_item100">
					<div class="_lefttext">公告内容<span style="color:red;">*</span>:</div>
					<div class="_righttext">
						<input id="notice_content" class="easyui-textbox" name="notice.content" data-options="multiline:true,required:true,missingMessage:'该输入项为必输项'" style="height:255px;width:97%"></input>
					</div>
				</div>
			</div>
			<%-- <table >
                <tr>
                    <td style="text-align: right;width:100px;">所属部门<font color="red">*</font>:</td>
                    <td  colspan="3">
                        <input name="departmentIds" id="departmentIds" class="easyui-combotree"  style="width:100%"
                            data-options="
                            multiple:true,
                            url: '${dynamicURL}/basic/departmentTree.action',
                            animate: true,required:true"/>
                    </td>
                </tr>
                <tr>
                    <td style="text-align: right;">所属岗位<font color="red">*</font>:</td>
                    <td colspan="3">
                         <select id="userRoleCodes" name="userRoleCodes" class="easyui-combotree" style="width:100%;"
                              data-options="multiple:true,required:true,url:'${dynamicURL}/security/searchRoleCombo.action'"></select>
                      </td>
                  </tr>
                <tr>
                    <td  style="text-align: right;">公告标题<span style="color:red;">*</span>:</td>
                    <td colspan="3">
                        <input id="notice_title" class="easyui-textbox" name="notice.title" data-options="required:true,missingMessage:'该输入项为必输项'" style="width: 465px;"></input>
                    </td>
                </tr>
                <tr>
                    <td  style="text-align: right;">开始时间<span style="color:red;">*</span>:</td>
                    <td >
                        <input id="notice_start" name="notice.start" type="text" class="easyui-datebox" data-options="sharedCalendar:'#sc',required:true,editable:false" style="width: 200px;"></input>
                    </td>
                    <td  style="text-align: right;">结束时间<span style="color:red;">*</span>:</td>
                    <td >
                        <input id="notice_end" name="notice.end" type="text" class="easyui-datebox" data-options="sharedCalendar:'#sc',required:true,editable:false" style="width: 200px;"></input>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: right;">公告内容<span style="color:red;">*</span>:</td>
                    <td colspan="3">
                        <input id="notice_content" class="easyui-textbox" name="notice.content" data-options="multiline:true,required:true,missingMessage:'该输入项为必输项'" style="height:255px;width: 465px;"></input>
                    </td>
                </tr>
            </table> --%>
		</form>
	</div>
	<div id="bb">
		<a id="saveButton" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="submitForm();">保存</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="cancle();">取消</a>
	</div>
</div>
<script type="text/javascript">
	var buttons = $.extend([], $.fn.datebox.defaults.buttons);
	buttons.splice(1, 0, {
		text: '清空',
		handler: function(target){
			$(target).datebox("setValue","");
			$(this).parent().next().children().first().click();
		}
	});

	function loaddata(){
		$('#dg').datagrid('load',sy.serializeObject($("#searchForm").form()));
	}
	function formatDateTime(val,row){
		if(!val.time){
			return "";
		}
		var date = new Date(val.time);
		return defaultDateFormatter(date);
	}

	$(function(){

		$('#start').datebox({
			buttons: buttons
		});
		$('#end').datebox({
			buttons: buttons
		});
		$('#dg').datagrid({
			title:'公告管理',
			toolbar:'#tb',
			singleSelect:false,
			fit:true,
			fitColumns:true,
			collapsible: true,
			rownumbers: true, //显示行数 1，2，3，4...
			pagination: true, //显示最下端的分页工具栏
			pagePosition : 'bottom',
			pageList: [5,10,15,20], //可以调整每页显示的数据，即调整pageSize每次向后台请求数据时的数据
			pageSize: 20, //读取分页条数，即向后台读取数据时传过去的值
			url:'${dynamicURL}/system/searchNotice.action',
			nowrap : true,
			border : false,
			remoteSort : false,
			onDblClickRow:function(rowIndex, rowData){
				//双击查看
			}
		});
	});
	//打开增加公告面板
	function addNotice(){
		$("#ff").form("clear");
		$("#win").attr("type","add");
		$("#win").dialog("setTitle","新增公告").dialog("open");
	}
	//打开修改公告面板
	function updateNotice(){
		var rows = $('#dg').datagrid("getSelections");
		if(rows.length == 0){
			$.messager.alert("提示","请选择要修改的记录！","warning");
			return;
		}else if(rows.length > 1){
			$.messager.alert("提示","请选择要一条记录进行修改！","warning");
			return;
		}
		$.messager.progress({
			text : "正在加载公告信息，请稍后...",
			interval : 100
		});
		var row = rows[0];
		$.post("${dynamicURL}/system/searchNotice!getNoticeInfo.action",
				{
					"id" : row.id
				},function(result){
					$("#ff").form("clear");
					$("#notice_id").val(result.notice.id);
					$("#notice_title").textbox("setValue",result.notice.title);
					$("#notice_start").datebox("setValue",result.notice.start);
					$("#notice_end").datebox("setValue",result.notice.end);
					$("#notice_content").textbox("setValue",result.notice.content);
					$("#win").attr("type","update");
					$.messager.progress('close');
					$("#win").dialog("setTitle","修改公告").dialog("open");
				},"json");
	}

	function delNotice(){
		var rows = $('#dg').datagrid("getSelections");
		if(rows.length == 0){
			$.messager.alert("提示","请选择要删除的记录！","warning");
			return;
		}
		var ids = "";
		$.messager.confirm('确认','您确认想要删除选中记录吗？',function(r){
			if (r){
				for(var i =0;i<rows.length;i++){
					if(i == rows.length-1){
						ids += rows[i].id;
					}else{
						ids += rows[i].id+",";
					}
				}
				$('#fm').form('submit',{
					url :"${dynamicURL}/system/updateNotice!batchDelete.action",
					onSubmit: function(param){
						param.ids = ids;
						$.messager.progress({
							text : "正在删除，请稍后...",
							interval : 100
						});
						return true;
					},
					success:function(result){
						$.messager.progress('close');
						handleActionResult(result,{
							onSuccess:function(){
								//弹出保存/更新成功信息
								$.messager.show({
									title : '提示',
									msg : '删除成功！'
								});
								$('#dg').datagrid("reload");
							}
						});
					}
				});
			}
		});
	}
	function submitForm(){
		var type = $("#win").attr("type");
		if(type == "add"){//新增
			$('#ff').form('submit',{
				url :"${dynamicURL}/system/createNotice.action",
				onSubmit: function(){
					var flag = $(this).form('validate');
					if(flag==true){
						$.messager.progress({
							text : "正在保存，请稍后...",
							interval : 100
						});
					}
					return flag;
				},
				success:function(result){
					$.messager.progress('close');
					handleActionResult(result,{
						onSuccess:function(){
							//弹出保存/更新成功信息
							$.messager.show({
								title : '提示',
								msg : '保存成功！'
							});
							$("#win").dialog("close");
							$('#dg').datagrid("reload");
						}
					});
				}
			});
		}else{//修改
			$('#ff').form('submit',{
				url :"${dynamicURL}/system/updateNotice.action",
				onSubmit: function(){
					var flag = $(this).form('validate');
					if(flag==true){
						$.messager.progress({
							text : "正在更新，请稍后...",
							interval : 100
						});
					}
					return flag;
				},
				success:function(result){
					$.messager.progress('close');
					handleActionResult(result,{
						onSuccess:function(){
							//弹出保存/更新成功信息
							$.messager.show({
								title : '提示',
								msg : '更新成功！'
							});
							$("#win").dialog("close");
							$('#dg').datagrid("reload");
						}
					});
				}
			});
		}
	}
	function cancle(){
		$("#win").dialog("close");
	}

</script>
</body>
</html>
