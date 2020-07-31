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

<!-- 双击弹出框-查看明细 -->
<div id="showDetailWin" class="easyui-window" title="查看明细" style="width:1000px;height:630px"
     data-options="closed:true,iconCls:'icon-search',modal:true,collapsible:false,minimizable:false,maximizable:false">
    <table id="dataGridBomSub"></table>
</div>

<!-- 导入窗口 -->
<div id="importDialog" class="easyui-window" title="导入BOM（目前只支持xls格式，请另存为xls后导入）"  style="width:460px;height:120px"
     data-options="closed:true,iconCls:'icon-excel',modal:true,collapsible:false,minimizable:false,maximizable:false">
    <form onsubmit="return false;" id="importForm" enctype="multipart/form-data" method="post"
          action="/bom/importBomMain">
        <table class="fixedTb">
            <tr>
                <td class="formlabletd">文件:</td>
                <td width="200px;">
                    <input id="importFile" type="file"  name="file" onchange="checkExt(this.id, 'xls',this.value)" style="width:200px">
                    <input id="importFileName" type="hidden"  name="fileName" >
                </td>
                <td style="width:100px;padding-left: 20px;padding-right: 20px;">
                    <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-excel'" onclick="importRow();">导入</a>
                </td>
            </tr>

        </table>
    </form>
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

//新增
$("#add").click(function(){
    openImportWin();
});

//角色修改
$("#update").click(function(){

});

//角色删除
$("#delete").click(function(){

});

//打开导入窗口
function openImportWin(){
    $('#importForm').form("clear");
    $("#importDialog").window("open");
}
//检查导入文件种类
function checkExt(fileId, ext, val) {
    var result = true;
    var tempext = ext;
    ext = ',' + ext + ','; // ,xls,xlxs,
    var value = $("#" + fileId).val(); // 111.xlxs
    if (value == "")
        return false;
    if (value.indexOf(".") > 0) {
        var o = value.split("."); // 111.xlxs ==> o[111] o[xlxs]
        var e = ',' + o[o.length - 1].toLowerCase() + ','; // ,xlxs,
        if (ext.indexOf(e) == -1) // ext中不包含e
            result = false;
    } else{
        result = false;
    }
    if (!result) {
        $.messager.alert('提示', '请选择Excel文件！', 'warning');
        document.getElementById(fileId).outerHTML = document
                .getElementById(fileId).outerHTML.replace(/(value=\").+\"/i,
                "$1\"");
    } else {
        $('#importFileName').val(val);
    }
}
//上传文件
function importRow(){
    $('#importForm').form('submit',{
        onSubmit : function(param) {
            if($("#importFile").val()==""){
                $.messager.alert('提示','请选择要上传的Excel！','warning');
                return false;
            }
            $.messager.progress({
                text : '正在上传，请稍后...',
                interval : 100
            });
            return true;
        },
        success : function(result) {
            var data = eval('(' + result + ')');
            $.messager.progress('close');
            if(!data.success){
                $.messager.alert('提示',data.message,'warning');
            }else{
                $.messager.alert('提示','BOM导入成功');
                $("#importDialog").window("close");
                loaddata();
            }
        }
    });
}

</script>
</body>