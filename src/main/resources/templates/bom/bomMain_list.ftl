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
                    <td class="cxlabel">创建时间:</td>
                    <td class="cxinput">
                        <input type="text" id="createdAtStart" name="createdAtStart" class="Wdate {required:true}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:100px;"/>
                    </td>
                    <td class="cxinput">
                        -&nbsp;<input type="text" id="createdAtEnd" name="createdAtEnd" class="Wdate {required:true}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:100px;"/>
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
            <a id="update" href="#" class="easyui-linkbutton" iconCls="icon-edit"  plain="false"  >结案</a>
        </#if>
        <#if showRemoveButton?? && showRemoveButton == "YES">
            <a id="delete" href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="false">删除</a>
        </#if>
            <a href="#"  id = "searchBomDeliveryDetail"  class="easyui-linkbutton" iconCls="icon-search" onclick="showBomDeliveryDetail()">发货明细</a>
    </div>
</div>

<!-- 双击弹出框-查看明细 -->
<div id="showDetailWin" class="easyui-window" title="查看明细" style="width:1200px;height:600px"
     data-options="closed:true,iconCls:'icon-search',modal:true,collapsible:false,minimizable:false,maximizable:false">
    <table id="dataGridBomSub"></table>
    <input name="bomStatus" id="bomStatus" type="hidden"/>
    <input name="showDetailWinBomId" id="showDetailWinBomId" type="hidden"/>
</div>

<div id="showDetailWinTb" >
<#if showAddButton?? && showAddButton == "YES">
    <a id="saveDeliveryAmount" href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="false" >提交</a>
    <a id="addDetailLine" href="#" class="easyui-linkbutton" iconCls="icon-add"  plain="false" >新增行</a>
    <a id="deleteDetailLine" href="#" class="easyui-linkbutton" iconCls="icon-remove"  plain="false" >删除行</a>
</#if>
</div>

<!-- 查看发货明细 -->
<div id="showDeliveryDetailWin" class="easyui-window" title="查看发货明细" style="width:1200px;height:600px"
     data-options="closed:true,iconCls:'icon-search',modal:true,collapsible:false,minimizable:false,maximizable:false">
    <div data-options="region:'north',title:'查询条件',border:false" style="height: 40px;" class="zoc">
        <form onsubmit="return false;" id="searchDeliveryDetailForm">
            <input name="bomId" id="bomId" type="hidden"/>
            <table class="fixedTb">
                <tr>
                    <td class="cxlabel">发货时间:</td>
                    <td class="cxinput">
                        <input type="text" id="deliveryDateStart" name="deliveryDateStart" class="Wdate {required:true}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:100px;"/>
                    </td>
                    <td class="cxinput">
                        -&nbsp;<input type="text" id="deliveryDateEnd" name="deliveryDateEnd" class="Wdate {required:true}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="width:100px;"/>
                    </td>
                    <td class="cxlabel">
                        <a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="loadBomDeliveryDetail()">查询</a>
                    </td>

                </tr>
            </table>

        </form>
    </div>
    <div region="center" border="false" style="height: 600px;">
        <table id="dataGridDeliveryDetail"></table>
    </div>

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

<!-- BOM零配件列表-新增行 -->
<div id="rowInputInfo" style="padding:10px;display:none;" title="新增行">
    <table>
        <tr>
            <td>序号</td>
            <td><input id="serialNo_add" type="text"/></td>
        </tr>
        <tr>
            <td>零配件名称</td>
            <td><input id="name_add" type="text"/></td>
        </tr>
        <tr>
            <td>材质/品牌</td>
            <td><input id="brand_add" type="text"/></td>
        </tr>
        <tr>
            <td>规格/尺寸</td>
            <td><input id="specifications_add" type="text"/></td>
        </tr>
        <tr>
            <td>单位</td>
            <td><input id="unit_add" type="text"/></td>
        </tr>
        <tr>
            <td>单台用量</td>
            <td><input id="singleAmount_add" type="text"/></td>
        </tr>
        <tr>
            <td>库存</td>
            <td><input id="stockAmount_add" type="text"/></td>
        </tr>
        <tr>
            <td>备货</td>
            <td><input id="stockUpAmount_add" type="text"/></td>
        </tr>
        <tr>
            <td>申购</td>
            <td><input id="purchaseAmount_add" type="text"/></td>
        </tr>
        <tr>
            <td>物料交期</td>
            <td><input id="deliveryDate_add" type="text"/></td>
        </tr>
        <tr>
            <td>备注</td>
            <td><input id="remark_add" type="text"/></td>
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

function loadBomDeliveryDetail(){
    $('#dataGridDeliveryDetail').datagrid('load',sy.serializeObject($("#searchDeliveryDetailForm").form()));
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
        customerCode:$("#customerCode").val(),
        createdAtStart:$("#createdAtStart").val(),
        createdAtEnd:$("#createdAtEnd").val()
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
    $('#bomStatus').val(selectedRow.status);
    $('#showDetailWinBomId').val(selectedRow.id);
    var queryParametersBomSub = {
        bomId:selectedRow.id
    };
    $("#showDetailWin").window("open");
    $('#dataGridBomSub').datagrid({
        title:'BOM零配件列表',
        toolbar:'#showDetailWinTb',
        singleSelect:true,
        fitColumns:true,
        fit:true,
        collapsible: true,
        rownumbers: false, //显示行数 1，2，3，4...
        pagination: true, //显示最下端的分页工具栏
        pageList: [5,10,15,20], //可以调整每页显示的数据，即调整pageSize每次向后台请求数据时的数据
        pageSize: 20, //读取分页条数，即向后台读取数据时传过去的值
        onClickCell: function(index,field,value){
            $(this).datagrid('beginEdit', index);
            var ed = $(this).datagrid('getEditor', {index:index,field:field});
        },
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
                    align: 'center',
                    editor: {type:'numberbox',options:{precision:0}}
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
                    align: 'center'
                },
                {
                    field: 'deliveryAmount',
                    title: '已发数量',
                    width: 100,
                    align: 'center'
                },
                {
                    field: 'currentDeliveryAmount',
                    title: '本次发货数量',
                    width: 150,
                    align: 'center',
                    editor: {type:'numberbox',options:{precision:0}}
                },
                {
                    field: 'remark',
                    title: '备注',
                    width: 150,
                    align: 'center'
                }

            ]
        ]

    });
}


//新增
$("#add").click(function(){
    openImportWin();
});

//提交保存发货数量
$("#saveDeliveryAmount").click(function(){
    var bomStatus = $("#bomStatus").val();
    if(bomStatus == 1){
        $.messager.alert('提示','该BOM已结案！');
        return false;
    }
    $.messager.confirm('提示', '确定提交吗？', function(r){
        if (r){
            $.messager.progress({text:"提交中..."});
            var rows = $("#dataGridBomSub").datagrid("getRows");
            for(var i=0;i<rows.length;i++)
            {
                $('#dataGridBomSub').datagrid('endEdit', i);
            }
            var jsonDataStr = JSON.stringify(rows);
            //console.log(jsonDataStr);
            $.ajax({
                type:"POST",
                url: "/bom/commitDeliveryAmount",
                dataType: "json",
                data: "jsonDataStr=" + jsonDataStr,
                cache:false,
                success:function(data){
                    $.messager.progress('close');
                    if (data.success) {
                        $("#showDetailWin").window("close");
                        $('#dataGrid').datagrid('reload');
                    } else {
                        $.messager.alert('提示',data.message);
                    }

                }
            });
        }
    });

});

//BOM零配件列表-删除行
$("#deleteDetailLine").click(function(){
    var selectedRow = $('#dataGridBomSub').datagrid('getSelected');
    if(!selectedRow){
        $.messager.alert('提示','请选择操作行。');
        return;
    }
    $.messager.confirm('提示', '确定删除该行数据吗？', function(r){
        if (r){
            $.messager.progress({text:"提交中..."});
            $.ajax({
                type:"POST",
                url: "/bom/deleteBomSub",
                dataType: "json",
                data: "id=" + selectedRow.id,
                cache:false,
                success:function(data){
                    $.messager.progress('close');
                    if (data.success) {
                        $('#dataGridBomSub').datagrid('reload');
                    } else {
                        $.messager.alert('提示',data.message);
                        $('#dataGridBomSub').datagrid('reload');
                    }

                }
            });
        }
    });
});

//BOM零配件列表-新增行
$("#addDetailLine").click(function(){
    $("#serialNo_add").val("");
    $("#name_add").val("");
    $("#brand_add").val("");
    $("#specifications_add").val("");
    $("#unit_add").val("");
    $("#singleAmount_add").val("");
    $("#stockAmount_add").val("");
    $("#stockUpAmount_add").val("");
    $("#purchaseAmount_add").val("");
    $("#deliveryDate_add").val("");
    $("#remark_add").val("");

    $("#rowInputInfo").show();
    $("#rowInputInfo").dialog({
        collapsible: true,
        minimizable: false,
        maximizable: false,
        height:400,
        width:300
    });
});

//BOM零配件列表-新增行-保存
$("#saveBtn").click(function(){
    if(!$.isNotBlank($("#serialNo_add").val())){
        $.messager.alert("提示","请填写序号","info");
        return false;
    }
    if(!$.isNotBlank($("#name_add").val())){
        $.messager.alert("提示","请填写零配件名称","info");
        return false;
    }
    if(!$.isNotBlank($("#singleAmount_add").val())){
        $.messager.alert("提示","请填写单台用量","info");
        return false;
    }
    $.messager.progress({text:"提交中..."});
    jQuery.ajax({
        url: "/bom/saveBomSubRow",
        data:{
            "bomId": $("#showDetailWinBomId").val(),
            "serialNo": $("#serialNo_add").val(),
            "name": $("#name_add").val(),
            "brand": $("#brand_add").val(),
            "specifications": $("#specifications_add").val(),
            "unit": $("#unit_add").val(),
            "singleAmount": $("#singleAmount_add").val(),
            "stockAmount": $("#stockAmount_add").val(),
            "stockUpAmount": $("#stockUpAmount_add").val(),
            "purchaseAmount": $("#purchaseAmount_add").val(),
            "deliveryDate": $("#deliveryDate_add").val(),
            "remark": $("#remark_add").val()
        },
        type: "POST",
        success: function(result) {
            $.messager.progress('close');
            if(result.success == true){
                $('#dataGridBomSub').datagrid('reload');
                $("#rowInputInfo").dialog("close");
            }else
                $.messager.alert('错误', result.message, 'error');
        },
        fail: function(data) {
            $.messager.progress('close');
            $.messager.alert('错误',"保存信息出错,请联系管理员！");
        }
    });
});

//结案
$("#update").click(function(){
    var selectedRow = $('#dataGrid').datagrid('getSelected');
    if(!selectedRow){
        $.messager.alert('提示','请选择操作行。');
        return;
    }
    if(selectedRow.status == 1){
        $.messager.alert('提示','该BOM已经结案，无需重复操作！');
        return;
    }
    $.messager.confirm('提示', '确定结案吗？', function(r){
        if (r){
            $.messager.progress({text:"提交中..."});
            $.ajax({
                type:"POST",
                url: "/bom/closeBom",
                dataType: "json",
                data: "id=" + selectedRow.id,
                cache:false,
                success:function(data){
                    $.messager.progress('close');
                    if (data.success) {
                        $('#dataGrid').datagrid('reload');
                    } else {
                        $.messager.alert('提示',data.message);
                        $('#dataGrid').datagrid('reload');
                    }

                }
            });
        }
    });
});

//删除
$("#delete").click(function(){
    var selectedRow = $('#dataGrid').datagrid('getSelected');
    if(!selectedRow){
        $.messager.alert('提示','请选择操作行。');
        return;
    }
    if(selectedRow.status == 1){
        $.messager.alert('提示','该BOM已经结案，不可删除！');
        return;
    }
    $.messager.confirm('提示', '确定删除该BOM吗？', function(r){
        if (r){
            $.messager.progress({text:"提交中..."});
            $.ajax({
                type:"POST",
                url: "/bom/deleteBom",
                dataType: "json",
                data: "id=" + selectedRow.id,
                cache:false,
                success:function(data){
                    $.messager.progress('close');
                    if (data.success) {
                        $('#dataGrid').datagrid('reload');
                    } else {
                        $.messager.alert('提示',data.message);
                        $('#dataGrid').datagrid('reload');
                    }

                }
            });
        }
    });
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

//看发货明细
function showBomDeliveryDetail(){
    var selectedRow = $('#dataGrid').datagrid('getSelected');
    if(!selectedRow){
        $.messager.alert('提示','请选择要查看的BOM行。');
        return;
    }
    $('#bomId').val(selectedRow.id);
    $("#showDeliveryDetailWin").window("open");
    var queryParametersBomDeliveryDetail = {
        bomId:$("#bomId").val(),
        deliveryDateStart:$("#deliveryDateStart").val(),
        deliveryDateEnd:$("#deliveryDateEnd").val()
    };
    $('#dataGridDeliveryDetail').datagrid({
        title:'BOM发货明细列表',
        singleSelect:true,
        fitColumns:true,
        fit:true,
        collapsible: true,
        rownumbers: false, //显示行数 1，2，3，4...
        pagination: true, //显示最下端的分页工具栏
        pageList: [5,10,15,20], //可以调整每页显示的数据，即调整pageSize每次向后台请求数据时的数据
        pageSize: 20, //读取分页条数，即向后台读取数据时传过去的值
        url:'/bom/bomDeliveryDetailList',
        queryParams:queryParametersBomDeliveryDetail,
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
                    field: 'deliveryDate',
                    title: '发货时间',
                    width: 150,
                    align: 'center'
                },
                {
                    field: 'deliveryAmount',
                    title: '发货数量',
                    width: 100,
                    align: 'center'
                }

            ]
        ]

    });
}

</script>
</body>