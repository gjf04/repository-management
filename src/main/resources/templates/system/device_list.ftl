<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<#include "../common/easyui_core.ftl"/>
	<title>设备查询</title>
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
							<td class="cxlabel">序列号:</td>
							<td class="cxinput">
								<input name="serialNo" id="serialNo" class="easyui-textbox" style="width:100px;">
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
						<th data-options="field:'serialNo', halign : 'center' ,align : 'center', width:200">序列号</th>
						<th field="name"  halign="center"  align="center" width="200">名称</th>
                        <th field="type"  halign="center"  align="center"  width="200">型号</th>
                        <th field="version"  halign="center"  align="center"  width="200">固件版本</th>
                        <th field="simNo"  halign="center"  align="center"  width="200">物联网卡号</th>
						<th field="ip"  halign="center"  align="center"  width="200">IP</th>
						<th data-options="field:'status',align:'center',width:200,formatter:statusFormater">状态</th>
					</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
	<div id="tb" >

    <#if showAddButton?? && showAddButton == "YES">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add"  onclick="apply()">新增</a>
    </#if>
        <#--<#if showEditButton?? && showEditButton == "YES">-->
            <#--<a href="#" class="easyui-linkbutton" iconCls="icon-edit"  onclick="updateUser()">修改</a>-->
        <#--</#if>-->

	</div>


	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUser()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</div>

<!-- 新增 -->
<div id="applyDiv"  class="easyui-dialog" title=""   closed="true"
     data-options="resizable:true,modal:true" >
    <table style="font-weight: 400;" border="0">
        <tr>
            <td style="text-align: right;">型号<span class="star">*</span>:</td>
            <td>
                <input id="dtype" name="dtype" size="54" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" style="width: 200px;"/></td>
        </tr>

        <tr>
            <td style="text-align: right;">序列号<span class="star">*</span>:</td>
            <td><input id="dserialNo" name="dserialNo" size="54" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" style="width: 200px;"/></td>
        </tr>

        <tr>
            <td style="text-align: right;">名称:</td>
            <td><input id="dname" name="dname" size="54" class="easyui-textbox" style="width: 200px;"/></td>
        </tr>

        <tr>
            <td style="text-align: right;">IP<span class="star">*</span>:</td>
            <td><input id="dip" name="dip" size="54" class="easyui-textbox" data-options="required:true,missingMessage:'该输入项为必输项'" style="width: 200px;"/></td>
        </tr>

        <tr>
            <td style="text-align: right;">固件版本:</td>
            <td><input id="dversion" name="dversion" size="54" class="easyui-textbox" style="width: 200px;"/></td>
        </tr>

        <tr>
            <td style="text-align: right;">物联网卡号:</td>
            <td><input id="dsimNo" name="dsimNo" size="54" class="easyui-textbox" style="width: 200px;"/></td>
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
       

    </table>

</div>


<!-- 双击弹出框-查看明细 -->
<div id="showDetailWin" class="easyui-window" title="查看明细" style="width:800px;height:240px"
     data-options="closed:true,iconCls:'icon-search',modal:true,collapsible:false,minimizable:false,maximizable:false">
    <div class="easyui-panel" title="设备信息">
        <table id="rounded-corner" style="width: 100%;">
            <tr style="height: 25px;">
                <td width="10%">&nbsp;&nbsp;设备名:</td>
                <td width="23%" style="padding-right: 20px;">
                    <input id="show_type" name="show_type"  class="easyui-textbox" style="width:100%"
                           data-options="editable:false,iconCls:'icon-lock'" />
                </td>
                <td width="10%">&nbsp;&nbsp;真实姓名:</td>
                <td width="24%" style="padding-right: 20px;">
                    <input id="show_ip"  name="show_ip" class="easyui-textbox" style="width:100%"
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
                    <input id="show_serialNo" name="show_serialNo"  class="easyui-textbox" style="width:100%"
                           data-options="editable:false,iconCls:'icon-lock'" />
                </td>
                <td width="10%">&nbsp;&nbsp;邮箱:</td>
                <td width="24%" style="padding-right: 20px;">
                    <input id="show_name"  name="show_name" class="easyui-textbox" style="width:100%"
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
var serialNo;

	function loaddata(){
        serialNo = $("#serialNo").val();
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
	var statusMap = {'1':'正常','2':'离线','3':'报警'};
	function statusFormater(val,row){
		return statusMap[val];
	}

	$(function(){
        serialNo = $("#serialNo").val();
        datagrid = $('#dg').datagrid({
			title:'设备列表',
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
			url:'/system/deviceInfoList',
			nowrap : true,
			border : false,
            onDblClickRow : function(rowIndex,rowData){
                //showFormWin(rowIndex,rowData);
                location.href='overview.html?serialNo=' + rowData.serialNo;
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
        $('#show_type').textbox("setValue",selectedRow.type);
        $('#show_serialNo').textbox("setValue",selectedRow.serialNo);
        $('#show_name').textbox("setValue",selectedRow.name);
        $('#show_ip').textbox("setValue",selectedRow.ip);
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
        title: '新增设备',
        width: 370,
        height: 350,
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
    $('#dtype').textbox("setValue","");
    $('#dserialNo').textbox("setValue","");
    $('#dname').textbox("setValue","");
    $('#dip').textbox("setValue","");
    $('#dversion').textbox("setValue","");
    $('#dsimNo').textbox("setValue","");
    $("#applyDiv").dialog("open");
}
//新增-提交
function applyCommit(){
    var type = $('#dtype').val();
    var serialNo = $('#dserialNo').val();
    var name = $('#dname').val();
    var ip = $('#dip').val();
    var version = $('#dversion').val();
    var simNo = $('#dsimNo').val();
    var departmentId = $('#aduit_departmentId').combotree("getValue");
    if (type == "") {
        $("#type").focus();
        alert("型号不能为空");
        return false;
    }

    if (ip == "") {
        $("#dip").focus();
        alert("IP不能为空");
        return false;
    }
    $.ajax({
        type:'post',
        url:'/system/createDevice',
        dataType : "json",
        data:{type:type, serialNo:serialNo, name:name, ip:ip, departmentId:departmentId, version:version, simNo: simNo},
        cache:false,
        async:false,
        success:function(data){
            $.messager.progress('close');
            if(!data.success){
                $.messager.alert('提示',data.message);
            }
            $('#applyDiv').dialog('close');
            $.messager.alert('提示',"新增设备成功");
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
			$.messager.alert('操作提示','请选择要修改的设备！','info');
			return;
		}
        $("#aduitUserDiv").dialog({
            title: '设备修改',
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
        $('#aduit_ip').textbox("setValue",selectedRow.ip);
        $('#aduit_departmentId').combotree("setValue","");
        $('#aduit_roleId').combotree("setValue","");
        $('#aduit_parentId').combotree("setValue","");
        $("#aduitUserDiv").dialog("open");

	}

    //修改-提交
    function updateUserCommit() {
        var selectedRow = $('#dg').datagrid('getSelected');
        if (!selectedRow || selectedRow == null) {
            $.messager.alert('操作提示', '请选择要修改的设备！', 'info');
            return;
        }
        var userId = selectedRow.id;
        var departmentId = $('#aduit_departmentId').combotree("getValue");
        $.ajax({
            type:'post',
            url:'/system/updateDevice',
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
                window.location.href="/system/exportUserList?ip=" + ip;
            }
        });
    }
</script>
</body>
</html>
