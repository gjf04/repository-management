<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<#include "../common/easyui_core.ftl"/>
	<title>用户查询</title>
</head>
<body>

<div class="easyui-layout" fit="true">
	<div data-options="region:'west'" title="组织架构" style="width:245px;">
		<ul id="tree" class="easyui-tree" data-options="
          		url: '/system/departmentTree',
          		animate: true,
          		lines:true,
          		onClick: treeOnClick"></ul>
	</div>
	<div data-options="region:'center'">
		<div class="easyui-layout" fit="true">
			<div region="north" border="false" collapsible="true" collapsed="false"
				 class="zoc" title="查询条件" style="height: 60px; overflow: auto;">
				<form onsubmit="return false;" id="searchForm">
					<table class="fixedTb">
						<tr>
							<td class="cxlabel">姓名:</td>
							<td class="cxinput">
								<input name="nickName" id="nickName" class="easyui-textbox" style="width:100px;">
							</td>

							<td class="cxlabel">
									<a href="#"  id = "searchPt" class="easyui-linkbutton" iconCls="icon-search" onclick="loaddata()">查询</a>
							</td>
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
						<th data-options="field:'ck',checkbox:true,formatter : function(value, row, index) {return row.rowId;}"></th>
						<th data-options="field:'userName', halign : 'center' ,align : 'center', width:200">用户名</th>
						<th field="nickName"  halign="center"  align="center" width="200">真实姓名</th>
                        <th field="sex"  halign="center"  align="center"  width="200">性别</th>
						<th field="mobile"  halign="center"  align="center"  width="200">手机号</th>
						<th field="departmentName"  halign="center"  align="center"  width="200">部门</th>
                        <th field="roleName"  halign="center"  align="center"  width="200">职务</th>
						<th data-options="field:'status',align:'center',width:200,formatter:statusFormater">使用状态</th>
					</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<div id="tb" >
		<a href="javascript:void(0)" id="morefunction" class="easyui-menubutton"  plain="false"
		   data-options="menu:'#functions',iconCls:'icon-task'">更多</a>
		<div id="functions" style="width:150px;">
            <#if showResetPasswordButton?? && showResetPasswordButton == "YES">
                <div data-options="iconCls:'icon-redo'" onclick="resetUserPassword()">重置密码</div>
            </#if>

		</div>
    <#if showAddButton?? && showAddButton == "YES">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add"  onclick="apply()">新增</a>
    </#if>
        <#if showEditButton?? && showEditButton == "YES">
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit"  onclick="updateUser()">修改</a>
        </#if>
        <#--<#if showAuditButton?? && showAuditButton == "YES">-->
            <#--<a href="#" class="easyui-linkbutton" iconCls="icon-edit"  onclick="auditUser()">审核通过</a>-->
        <#--</#if>-->
        <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="exportUser()">导出</a>
	</div>

	<div id="dlg" class="easyui-dialog" style="width:370px;height:200px;padding-top: 10px;"
		 closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post" novalidate >
			<table style="width:100%;">
                <input name="userId" id="reset_password_user_id" type="hidden"/>
				<tr style="height: 25px;">
					<td >&nbsp;&nbsp;新密码<font color="red">*</font>:</td>
					<td style="padding-right: 20px;">
						<input id="user_newPassword"  name="newPassword"  class="easyui-textbox" style="width:100%"
							   data-options="required:true,type:'password',missingMessage:'该输入项为必输项'"/>
					</td>
				</tr>
				<tr style="height: 25px;">
					<td >&nbsp;&nbsp;确认密码<font color="red">*</font>:</td>
					<td style="padding-right: 20px;">
						<input id="user_confirmPassword" name="confirmPassword"  class="easyui-textbox" style="width:100%"
							   data-options="required:true,type:'password',missingMessage:'该输入项为必输项'"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</div>

<!-- 注册 -->
<div id="applyDiv"  class="easyui-dialog" title=""   closed="true"
     data-options="resizable:true,modal:true" >
    <table style="font-weight: 400;" border="0">
        <tr>
            <td style="text-align: right;">用户名<span class="star">*</span>:</td>
            <td>
                <input id="duserName" name="duserName" size="54" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" style="width: 200px;"/></td>
        </tr>
        <tr>
            <td style="text-align: right;">密码<span class="star">*</span>:</td>
            <td><input id="dpassword" name="dpassword" size="54" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" style="width: 200px;"/></td>
        </tr>

        <tr>
            <td style="text-align: right;">手机号<span class="star">*</span>:</td>
            <td><input id="dmobile" name="dmobile" size="54" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" style="width: 200px;"/></td>
        </tr>

        <tr>
            <td style="text-align: right;">邮箱:</td>
            <td><input id="demail" name="demail" size="54" class="easyui-textbox" style="width: 200px;"/></td>
        </tr>

        <tr>
            <td style="text-align: right;">真实姓名<span class="star">*</span>:</td>
            <td><input id="dnickName" name="dnickName" size="54" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" style="width: 200px;"/></td>
        </tr>

        <tr>
            <td style="text-align: right;">性别<span class="star">*</span>:</td>
            <td>
                <input id="dsex1" type="radio" name="dsex"
                       class="easyui-validatebox" checked="checked" value="男"><label>男</label></input>
                <input id="dsex2" type="radio" name="dsex"
                       class="easyui-validatebox" value="女"><label>女</label></input>
            </td>
        </tr>

        <tr>
            <td style="text-align: right;">身份证号:</td>
            <td><input id="didentityNo" name="didentityNo" size="54" class="easyui-textbox" style="width: 200px;"/></td>
        </tr>

        <tr>
            <td style="text-align: right;">生日:</td>
            <td>
                <input id="dbirthday" name="dbirthday" size="54" type="text" class="easyui-datebox"  data-options="sharedCalendar:'#sc',editable:false" style="width:200px;" />
                <div id="sc" class="easyui-calendar"></div>
            </td>
        </tr>

        <tr>
            <td style="text-align: right;">家庭住址:</td>
            <td><input id="daddress" name="daddress" size="54" class="easyui-textbox" style="width: 200px;"/></td>
        </tr>
        <tr>
            <td style="text-align: right;">选择部门<span class="star">*</span>:</td>
            <td>
                <input name="aduit_departmentId" id="aduit_departmentId" class="easyui-combotree"  style="width:100%"
                       data-options="
						url: '/system/departmentTree',
						animate: true,required:true"/>
            </td>
        </tr>
        <tr>
            <td style="text-align: right;">设置角色<span class="star">*</span>:</td>
            <td>
                <select id="aduit_roleId" name="aduit_roleId" class="easyui-combotree" style="width:100%;"
                        data-options="required:true,url:'/system/searchRoleCombo'"></select>
            </td>
        </tr>

    </table>

</div>

<!-- 用户审核 -->
<!--
<div id="aduitUserDiv"  class="easyui-dialog" title=""   closed="true"
     data-options="resizable:true,modal:true" >
    <table style="font-weight: 400;" border="0">
        <tr>
            <td style="text-align: right;">真实姓名:</td>
            <td>
                <input id="aduit_nickName" name="aduit_nickName" size="54" class="easyui-textbox" style="width: 200px;"/></td>
        </tr>
        <tr>
            <td style="text-align: right;">选择部门<span class="star">*</span>:</td>
            <td>
                <input name="aduit_departmentId" id="aduit_departmentId" class="easyui-combotree"  style="width:100%"
                       data-options="
						url: '/system/departmentTree',
						animate: true,required:true"/>
			</td>
        </tr>
        <tr>
            <td style="text-align: right;">设置角色<span class="star">*</span>:</td>
            <td>
                <select id="aduit_roleId" name="aduit_roleId" class="easyui-combotree" style="width:100%;"
                        data-options="required:true,url:'/system/searchRoleCombo'"></select>
            </td>
        </tr>
        <tr>
            <td style="text-align: right;">直线上级<span class="star">*</span>:</td>
            <td>
                <select id="aduit_parentId" name="aduit_parentId" class="easyui-combotree" style="width:100%;"
                        data-options="required:true,url:'/system/searchParentUserCombo'"></select>
            </td>
        </tr>

    </table>

</div>
-->

<!-- 双击弹出框-查看明细 -->
<div id="showDetailWin" class="easyui-window" title="查看明细" style="width:800px;height:240px"
     data-options="closed:true,iconCls:'icon-search',modal:true,collapsible:false,minimizable:false,maximizable:false">
    <div class="easyui-panel" title="用户信息">
        <table id="rounded-corner" style="width: 100%;">
            <tr style="height: 25px;">
                <td width="10%">&nbsp;&nbsp;用户名:</td>
                <td width="23%" style="padding-right: 20px;">
                    <input id="show_userName" name="show_userName"  class="easyui-textbox" style="width:100%"
                           data-options="editable:false,iconCls:'icon-lock'" />
                </td>
                <td width="10%">&nbsp;&nbsp;真实姓名:</td>
                <td width="24%" style="padding-right: 20px;">
                    <input id="show_nickName"  name="show_nickName" class="easyui-textbox" style="width:100%"
                           data-options="editable:false,iconCls:'icon-lock'" />
                </td>
                <td width="10%">&nbsp;&nbsp;性别:</td>
                <td width="23%" style="padding-right: 20px;">
                    <input id="show_sex" name="show_sex"  class="easyui-textbox" style="width:100%"
                           data-options="editable:false,iconCls:'icon-lock'" />
                </td>
            </tr>
            <tr style="height: 25px;">
                <td width="10%">&nbsp;&nbsp;手机号:</td>
                <td width="23%" style="padding-right: 20px;">
                    <input id="show_mobile" name="show_mobile"  class="easyui-textbox" style="width:100%"
                           data-options="editable:false,iconCls:'icon-lock'" />
                </td>
                <td width="10%">&nbsp;&nbsp;邮箱:</td>
                <td width="24%" style="padding-right: 20px;">
                    <input id="show_email"  name="show_email" class="easyui-textbox" style="width:100%"
                           data-options="editable:false,iconCls:'icon-lock'" />
                </td>
                <td width="10%">&nbsp;&nbsp;生日:</td>
                <td width="23%" style="padding-right: 20px;">
                    <input id="show_birthday" name="show_birthday"  class="easyui-textbox" style="width:100%"
                           data-options="editable:false,iconCls:'icon-lock'" />
                </td>
            </tr>
            <tr style="height: 25px;">
                <td width="10%">&nbsp;&nbsp;身份证号:</td>
                <td width="23%" style="padding-right: 20px;">
                    <input id="show_identityNo" name="show_identityNo"  class="easyui-textbox" style="width:100%"
                           data-options="editable:false,iconCls:'icon-lock'" />
                </td>
                <td width="10%">&nbsp;&nbsp;家庭住址:</td>
                <td width="24%" style="padding-right: 20px;">
                    <input id="show_address"  name="show_address" class="easyui-textbox" style="width:100%"
                           data-options="editable:false,iconCls:'icon-lock'" />
                </td>
                <td width="10%">&nbsp;&nbsp;上级姓名:</td>
                <td width="24%" style="padding-right: 20px;">
                    <input id="show_parentNickName"  name="show_parentNickName" class="easyui-textbox" style="width:100%"
                           data-options="editable:false,iconCls:'icon-lock'" />
                </td>
            </tr>
        </table>
    </div>
</div>

<script type="text/javascript">
var datagrid;
var nickName;

	function loaddata(){
        nickName = $("#nickName").val();
		$('#dg').datagrid('load',sy.serializeObject($("#searchForm").form()));
	}
	function formatDateTime(val,row){
		if(!val.time){
			return "";
		}
		var date = new Date(val.time);
		return defaultDateTimeFormatter(date);
	}
	function formatDate(val,row){
		if(!val.time){
			return "";
		}
		var date = new Date(val.time);
		return defaultDateFormatter(date);
	}
	var statusMap = {'1':'启用','0':'待审核'};
	function statusFormater(val,row){
		return statusMap[val];
	}
	<#--function userDetailUrlFormatter(val,row){-->
		<#--return "<a href='${dynamicURL}/security/updateUserInit.action?user.id="+row.id+"'>"+val+"</a>";-->
	<#--}-->
	$(function(){
        nickName = $("#nickName").val();
        datagrid = $('#dg').datagrid({
			title:'用户列表',
			toolbar:'#tb',
			singleSelect:true,
			fit:true,
			fitColumns:true,
			collapsible: true,
			rownumbers: true, //显示行数 1，2，3，4...
			pagination: true, //显示最下端的分页工具栏
			pagePosition : 'bottom',
			pageList: [5,10,15,20], //可以调整每页显示的数据，即调整pageSize每次向后台请求数据时的数据
			pageSize: 15, //读取分页条数，即向后台读取数据时传过去的值
			queryParams : {
				'departmentId' : '0'
			},
			url:'/system/userInfoList',
			nowrap : true,
			border : false,
            onDblClickRow : function(rowIndex,rowData){
                showFormWin(rowIndex,rowData);
            }
        });
	});
	//部门树点击事件
	function treeOnClick(treeNode) {
		var node = $('#tree').tree("getSelected");
		var nodeTarget = treeNode.target;
		var isLeaf = $('#tree').tree('isLeaf',nodeTarget);
		$("#departmentId").val(node.id);
		$('#dg').datagrid('load',sy.serializeObject($("#searchForm").form()));
         	$('#dg').datagrid('load',{
         		'departmentId' : node.id
         	});
	}
	//双击看明细
	function showFormWin(rowIndex,rowData){
		var selectedRow = $('#dg').datagrid('getSelected');
        $('#show_userName').textbox("setValue",selectedRow.userName);
        $('#show_mobile').textbox("setValue",selectedRow.mobile);
        $('#show_email').textbox("setValue",selectedRow.email);
        $('#show_nickName').textbox("setValue",selectedRow.nickName);
        $('#show_sex').textbox("setValue",selectedRow.sex);
        $('#show_identityNo').textbox("setValue",selectedRow.identityNo);
        $('#show_birthday').textbox("setValue",selectedRow.birthday);
        $('#show_address').textbox("setValue",selectedRow.address);
        $("#show_parentNickName").textbox("setValue",selectedRow.parentNickName);
        $("#showDetailWin").window("open");
	}
//新增-打开页面
function apply(){
    $("#applyDiv").dialog({
        title: '注册账号',
        width: 400,
        height: 400,
        top: 60,
        closed: true,
        cache: false,
        modal: true,
        buttons:[{
            text:'保存',
            iconCls:'icon-ok',
            handler:function(){
                applyCommit();
            }
        },{
            text:'取消',
            iconCls:'icon-cancel',
            handler:function(){
                $('#applyDiv').dialog('close');
            }
        }]
    });
    $('#duserName').textbox("setValue","");
    $('#dpassword').textbox("setValue","");
    $('#dmobile').textbox("setValue","");
    $('#demail').textbox("setValue","");
    $('#dnickName').textbox("setValue","");
    $("input[name='dsex'][value='男']").attr("checked",true);
    $('#didentityNo').textbox("setValue","");
    $('#dbirthday').textbox("setValue","");
    $('#daddress').textbox("setValue","");
    $("#applyDiv").dialog("open");
}
//新增-提交
function applyCommit(){
    var userName = $('#duserName').val();
    var password = $('#dpassword').val();
    var mobile = $('#dmobile').val();
    var email = $('#demail').val();
    var nickName = $('#dnickName').val();
    var sex = $("input[name='dsex'][checked]").val();
    var identityNo = $('#didentityNo').val();
    var birthday = $('#dbirthday').datebox("getValue");
    var address = $('#daddress').val();
    var departmentId = $('#aduit_departmentId').combotree("getValue");
    var roleId = $('#aduit_roleId').combotree("getValue");
    if (userName == "") {
        $("#userName").duserName();
        alert("用户名不能为空");
        return false;
    }
    if (password == "") {
        $("#dpassword").focus();
        alert("密码不能为空");
        return false;
    }
    if (nickName == "") {
        $("#dnickName").focus();
        alert("真实姓名不能为空");
        return false;
    }
    $.ajax({
        type:'post',
        url:'/system/createUser',
        dataType : "json",
        data:{userName:userName, password:password, mobile:mobile, email:email, nickName:nickName, sex:sex, identityNo:identityNo,
            birthday:birthday, address:address, departmentId:departmentId, roleId:roleId},
        cache:false,
        async:false,
        success:function(data){
            $.messager.progress('close');
            if(!data.success){
                $.messager.alert('提示',data.message);
            }
            $('#applyDiv').dialog('close');
            $.messager.alert('提示',"新增用户成功");
            loaddata();
        },
        error:function(d){
            $.messager.alert('提示',"请刷新重试");
        }
    });
}

	//修改-打开修改窗口
	function updateUser(){
		var selectedRow = $('#dg').datagrid('getSelected');
		if(!selectedRow || selectedRow == null){
			$.messager.alert('操作提示','请选择要修改的用户！','info');
			return;
		}
        $("#aduitUserDiv").dialog({
            title: '用户修改',
            width: 340,
            height: 250,
            top: 50,
            closed: true,
            cache: false,
            modal: true,
            buttons:[{
                text:'提交',
                iconCls:'icon-ok',
                handler:function(){
                    updateUserCommit();
                }
            },{
                text:'取消',
                iconCls:'icon-cancel',
                handler:function(){
                    $('#aduitUserDiv').dialog('close');
                }
            }]
        });
        $('#aduit_nickName').textbox("setValue",selectedRow.nickName);
        $('#aduit_departmentId').combotree("setValue","");
        $('#aduit_roleId').combotree("setValue","");
        $('#aduit_parentId').combotree("setValue","");
        $("#aduitUserDiv").dialog("open");

	}

    //修改-提交
    function updateUserCommit() {
        var selectedRow = $('#dg').datagrid('getSelected');
        if (!selectedRow || selectedRow == null) {
            $.messager.alert('操作提示', '请选择要修改的用户！', 'info');
            return;
        }
        var userId = selectedRow.id;
        var departmentId = $('#aduit_departmentId').combotree("getValue");
        var roleId = $('#aduit_roleId').combotree("getValue");
        var parentId = $('#aduit_parentId').combotree("getValue");
        $.ajax({
            type:'post',
            url:'/system/updateUser',
            dataType : "json",
            data:{userId:userId, departmentId:departmentId, roleId:roleId, parentId:parentId},
            cache:false,
            async:false,
            success:function(data){
                $.messager.progress('close');
                if(!data.success){
                    $.messager.alert('提示',data.message);
                }
                $('#aduitUserDiv').dialog('close');
                $('#dg').datagrid('reload');//重新加载数据
                $.messager.alert('提示',"操作成功");
            },
            error:function(d){
                $.messager.alert('提示',"请刷新重试");
            }
        });
    }

    //审核通过-打开审核窗口
    function auditUser(){
        var selectedRow = $('#dg').datagrid('getSelected');
        if(!selectedRow || selectedRow == null){
            $.messager.alert('操作提示','请选择要审核的用户！','info');
            return;
        }
        if(selectedRow.status == '1'){
            $.messager.alert('操作提示','该用户已通过审核！','info');
            return;
        }
        $("#aduitUserDiv").dialog({
            title: '用户审核',
            width: 340,
            height: 250,
            top: 50,
            closed: true,
            cache: false,
            modal: true,
            buttons:[{
                text:'提交',
                iconCls:'icon-ok',
                handler:function(){
                    auditUserCommit();
                }
            },{
                text:'取消',
                iconCls:'icon-cancel',
                handler:function(){
                    $('#aduitUserDiv').dialog('close');
                }
            }]
        });
        $('#aduit_nickName').textbox("setValue",selectedRow.nickName);
        $('#aduit_departmentId').combotree("setValue","");
        $('#aduit_roleId').combotree("setValue","");
        $('#aduit_parentId').combotree("setValue","");
        $("#aduitUserDiv").dialog("open");

    }

    //审核通过-提交
    function auditUserCommit() {
        var selectedRow = $('#dg').datagrid('getSelected');
        if (!selectedRow || selectedRow == null) {
            $.messager.alert('操作提示', '请选择要审核的用户！', 'info');
            return;
        }
        var userId = selectedRow.id;
        var departmentId = $('#aduit_departmentId').combotree("getValue");
        var roleId = $('#aduit_roleId').combotree("getValue");
        var parentId = $('#aduit_parentId').combotree("getValue");
        $.ajax({
            type:'post',
            url:'/system/auditUser',
            dataType : "json",
            data:{userId:userId, departmentId:departmentId, roleId:roleId, parentId:parentId},
            cache:false,
            async:false,
            success:function(data){
                $.messager.progress('close');
                if(!data.success){
                    $.messager.alert('提示',data.message);
                }
                $('#aduitUserDiv').dialog('close');
                $('#dg').datagrid('reload');//重新加载数据
                $.messager.alert('提示',"操作成功");
            },
            error:function(d){
                $.messager.alert('提示',"请刷新重试");
            }
        });
    }

	<#--function delUser(){-->
		<#--var selectedRow = $('#dg').datagrid('getSelected');-->
		<#--if(!selectedRow || selectedRow == null){-->
			<#--$.messager.alert('操作提示','请选择要删除的数据！','info');-->
			<#--return;-->
		<#--}-->
		<#--$.messager.confirm('删除用户确认', '确认要删除该用户吗？删除后用户数据将无法恢复。', function(r){-->
			<#--if (r){-->
				<#--$('#fm').form('submit',{-->
					<#--url: '${dynamicURL}/security/deleteUser.action?userId='+selectedRow.id,-->
					<#--onSubmit: function(){-->
						<#--$.messager.progress({-->
							<#--text : '正在删除，请稍后...',-->
							<#--interval : 100-->
						<#--});-->
						<#--return true;-->
					<#--},-->
					<#--success: function(result){-->
						<#--$.messager.progress('close');-->
						<#--handleActionResult(result,{-->
							<#--onSuccess:function(){-->
								<#--$('#dg').datagrid('reload');-->
								<#--$.messager.show({-->
									<#--title : '提示',-->
									<#--msg : '删除成功！'-->
								<#--});-->
							<#--}-->
						<#--});-->
					<#--}-->
				<#--});-->
			<#--}-->
		<#--});-->
	<#--}-->
	function resetUserPassword(){
		var row = $('#dg').datagrid('getSelected');
		if (row){
			$('#fm').form('clear');
			$('#dlg').dialog('open').dialog('setTitle','重置密码');
			$('#fm').form('load',row);
			$('#reset_password_user_id').val(row.id);
		}else{
			$.messager.alert('操作提示','请选择需要重置密码的用户！','info');
		}
	}
	function saveUser(){
        var userId = $('#reset_password_user_id').val();
        var newPassword = $('#user_newPassword').val();
        var confirmPassword = $('#user_confirmPassword').val();
        $.ajax({
            type:'post',
            url:'/system/resetPassword',
            dataType : "json",
            data:{userId:userId, newPassword:newPassword, confirmPassword:confirmPassword},
            cache:false,
            async:false,
            success:function(data){
                $.messager.progress('close');
                if(!data.success){
                    $.messager.alert('提示',data.message);
                }
                $('#dlg').dialog('close');
                $.messager.alert('提示',"操作成功");
            },
            error:function(d){
                $.messager.alert('提示',"请刷新重试");
            }
        });

	}
	<#--function doExport(){-->
		<#--$('#searchForm').form('submit',{-->
			<#--url: '${dynamicURL}/security/exportUserList.action',-->
			<#--onSubmit: function(){-->
				<#--return $(this).form('validate');-->
			<#--}-->
		<#--});-->
	<#--}-->
	function fillsize(percent){
		var bodyWidth = document.body.clientWidth;
		return (bodyWidth-90)*percent;
	}

    //导出
    function exportUser(){
        if(!datagrid){
            $.messager.alert('提示','请先查询！','info');
            return;
        }
        $.messager.confirm('确认','确定要导出吗？', function(r){
            if (r){
                window.location.href="/system/exportUserList?nickName=" + nickName;
            }
        });
    }
</script>
</body>
</html>
