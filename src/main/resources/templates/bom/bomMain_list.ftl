<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>BOM列表</title>
<#include "../common/easyui_core.ftl"/>
</head>
<body>
<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 60px;" class="zoc">
        <form onsubmit="return false;" id="searchForm">
            <table class="fixedTb">
                <tr>
                    <td class="cxlabel">机种名称:</td>
                    <td class="cxinput">
                        <input id="deviceName" name="deviceName" class="easyui-textbox" style="width:100px;">
                    </td>
                    <td class="cxlabel">客户编码:</td>
                    <td class="cxinput">
                        <input id="customerCode" name="customerCode" class="easyui-textbox" style="width:100px;">
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

<!-- 双击弹出框-查看明细 -->
<div id="showDetailWin" class="easyui-window" title="查看明细" style="width:1000px;height:630px"
     data-options="closed:true,iconCls:'icon-search',modal:true,collapsible:false,minimizable:false,maximizable:false">
    <table id="dataGridBomSub"></table>
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

//查询
$(function(){
    queryParameters = {
        deviceName:$("#deviceName").val(),
        customerCode:$("#customerCode").val()
    };
    if(datagrid){
        //grid加载
        $('#dataGrid').datagrid('load',queryParameters);
    }else{
        datagrid = $('#dataGrid').datagrid({
            title:'BOM列表',
            toolbar:'#tb',
            singleSelect:true,
            fitColumns:true,
            fit:true,
            collapsible: true,
            rownumbers: true, //显示行数 1，2，3，4...
            pagination: true, //显示最下端的分页工具栏
            pageList: [5,10,15,20], //可以调整每页显示的数据，即调整pageSize每次向后台请求数据时的数据
            pageSize: 20, //读取分页条数，即向后台读取数据时传过去的值
            url:'/bom/bomMainList',
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
                        field: 'customerCode',
                        title: '客户编码',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'deviceName',
                        title: '机种名称',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'pmName',
                        title: 'PM担当',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'meName',
                        title: 'ME担当',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'deliveryDate',
                        title: '物料交期',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'productionDate',
                        title: '制作日期',
                        width: 150,
                        align: 'center'
                    },
                    {
                        field: 'num',
                        title: '总数量',
                        width: 150,
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
                        field: 'status',
                        title: '状态',
                        width: 150,
                        align: 'center',
                        formatter: function(value,row,index){
                            if (row.status == '1'){
                                return "已结案";
                            }else {
                                return "未结案";
                            }
                        }
                    }
                ]
            ],
            onDblClickRow : function(rowIndex,rowData){
                showFormWin(rowIndex,rowData);
            }
        });
    }
});

//双击看明细
function showFormWin(rowIndex,rowData){
    var selectedRow = $('#dataGrid').datagrid('getSelected');
    var queryParametersBomSub = {
        bomId:selectedRow.id
    };
    //console.log(selectedRow.id);
    $("#showDetailWin").window("open");
    $('#dataGridBomSub').datagrid({
        title:'BOM零配件列表',
        //toolbar:'#tb',
        singleSelect:true,
        fitColumns:true,
        fit:true,
        collapsible: true,
        rownumbers: false, //显示行数 1，2，3，4...
        pagination: true, //显示最下端的分页工具栏
        pageList: [5,10,15,20], //可以调整每页显示的数据，即调整pageSize每次向后台请求数据时的数据
        pageSize: 20, //读取分页条数，即向后台读取数据时传过去的值
        url:'/bom/bomSubList',
        queryParams:queryParametersBomSub,
        columns: [
            [
                {
                    field: 'serialNo',
                    title: '序号',
                    width: 60,
                    align: 'center'
                },
                {
                    field: 'name',
                    title: '零配件名称',
                    width: 180,
                    align: 'center'
                },
                {
                    field: 'brand',
                    title: '材质/品牌',
                    width: 120,
                    align: 'center'
                },
                {
                    field: 'specifications',
                    title: '规格/尺寸',
                    width: 300,
                    align: 'center'
                },
                {
                    field: 'unit',
                    title: '单位',
                    width: 80,
                    align: 'center'
                },
                {
                    field: 'singleAmount',
                    title: '单台用量',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'totalAmount',
                    title: '总用量',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'stockAmount',
                    title: '库存',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'stockUpAmount',
                    title: '备货',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'purchaseAmount',
                    title: '申购',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'deliveryDate',
                    title: '物料交期',
                    width: 150,
                    align: 'center',
                },
                {
                    field: 'deliveryAmount',
                    title: '已发数量',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'remark',
                    title: '备注',
                    width: 150,
                    align: 'center',
                }
            ]
        ]

    });
}

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