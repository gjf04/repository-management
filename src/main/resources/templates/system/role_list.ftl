<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>角色列表</title>
<#include "../common/easyui_core.ftl"/>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 60px;" class="zoc">
        <form onsubmit="return false;" id="searchForm">
            <table class="fixedTb">
                <tr>
                    <td class="cxlabel">角色名称:</td>
                    <td class="cxinput">
                        <input id="name" name="name" class="easyui-textbox" style="width:100px;">
                    </td>
                    <td class="cxlabel">
                        <a href="#"  id = "searchPt"  class="easyui-linkbutton" iconCls="icon-search" onclick="loaddata()">查询</a>
                    </td>

                </tr>
            </table>

        </form>
    </div>
    <div region="center" border="false">
        <table id="dataGrid"></table>
    </div>
    <div id="tb" >
        <#if showAddButton?? && showAddButton == "YES">
            <a id="add" href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="false" >新增</a>
        </#if>
        <#if showEditButton?? && showEditButton == "YES">
            <a id="update" href="#" class="easyui-linkbutton" iconCls="icon-edit"  plain="false"  >修改</a>
        </#if>
        <#if showRemoveButton?? && showRemoveButton == "YES">
            <a id="delete" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="false">删除</a>
        </#if>
    </div>
</div>

<div id="roleInputInfo" style="padding:10px;display:none;" title="新增角色">
    <table>
        <tr>
            <td>角色编码</td>
            <td><input id="code_edit" type="text"/></td>
        </tr>
        <tr>
            <td>角色名称</td>
            <td><input id="name_edit" type="text"/></td>
        </tr>
        <tr>
            <td>角色描述</td>
            <td><input id="description_edit" type="text"/></td>
        </tr>
    </table>
    </br>
    <div style="text-align:center"><input id="saveBtn" type="button" value="保存" class="l-btn" style=" font-size: 12px;line-height: 24px; width: 52px; font-family: 微软雅黑"/>
    </div>
</div>

<script type="text/javascript">
var datagrid;
var queryParameters;

function loaddata(){
    $('#dataGrid').datagrid('load',sy.serializeObject($("#searchForm").form()));
}

// 判断是否为空
$.isNotBlank = function(value) {
    if (value != undefined && value != "undefined" && value != null && value != "null" && value != "") {
        return true;
    }
    return false;
};

//角色查询
$(function(){
    queryParameters = {
        name:$("#name").val(),
        description:$("#description").val()
    };
    if(datagrid){
        //grid加载
        $('#dataGrid').datagrid('load',queryParameters);
    }else{
        datagrid = $('#dataGrid').datagrid({
            title:'角色列表',
            toolbar:'#tb',
            singleSelect:true,
            fitColumns:true,
            fit:true,
            collapsible: true,
            rownumbers: true, //显示行数 1，2，3，4...
            pagination: true, //显示最下端的分页工具栏
            pageList: [5,10,15,20], //可以调整每页显示的数据，即调整pageSize每次向后台请求数据时的数据
            pageSize: 20, //读取分页条数，即向后台读取数据时传过去的值
            url:'/system/roleList',
            queryParams:queryParameters,
            columns: [
                [
                    {
                        field: 'id',
                        title: '编号',
                        width: 10,
                        align: 'center',
                        hidden:true
                    },
                    {
                        field: 'checked',
                        width: 10,
                        align: 'center',
                        checkbox:true
                    },
                    {
                        field: 'name',
                        title: '角色名称',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'code',
                        title: '角色编码',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'description',
                        title: '角色描述',
                        width: 200,
                        align: 'center'
                    },
                    {
                        field: 'createdBy',
                        title: '创建者',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'createdAt',
                        title: '创建时间',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'updatedBy',
                        title: '最后修改人',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'updatedAt',
                        title: '最后更新时间',
                        width: 150,
                        align: 'center'
                    }
                ]
            ]
        });
    }
});

//角色新增
$("#add").click(function(){
    $("#name_edit").val("");
    $("#description_edit").val("");

    $("#roleInputInfo").show();
    $("#roleInputInfo").dialog({
        collapsible: true,
        minimizable: false,
        maximizable: false,
        height:200,
        width:300
    });
});

//角色新增提交
$("#saveBtn").click(function(){
    if(!$.isNotBlank($("#code_edit").val())){
        $.messager.alert("提示","请填写角色编码","info")
        return false;
    }
    if(!$.isNotBlank($("#name_edit").val())){
        $.messager.alert("提示","请填写角色名称","info")
        return false;
    }
    if(!$.isNotBlank($("#description_edit").val())){
        $.messager.alert("提示","请填写角色描述","info")
        return false;
    }
    $.messager.progress({text:"提交中..."});
    jQuery.ajax({
        url: "/system/saveRole",
        data:{
            "code": $("#code_edit").val(),
            "name": $("#name_edit").val(),
            "description": $("#description_edit").val()
        },
        type: "POST",
        success: function(result) {
            $.messager.progress('close');
            if(result.success == true){
                $('#dataGrid').datagrid('reload');
                $("#roleInputInfo").dialog("close");
            }else
                $.messager.alert('错误', result.message, 'error');
        },
        fail: function(data) {
            $.messager.progress('close');
            $.messager.alert('错误',"保存信息出错,请联系管理员！");
        }
    });
});

//角色修改
$("#update").click(function(){

});

//角色删除
$("#delete").click(function(){

});

</script>
</body>