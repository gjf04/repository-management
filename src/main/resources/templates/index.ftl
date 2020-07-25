<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head >
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>智慧管理平台</title>
    <link href="${urls.getForLookupPath('/css/login_index.css')}" rel="stylesheet" />
<#include "common/easyui_core.ftl"/>
    <!-- 简单的样式 -->
    <style type="text/css">
        ul:hover {
            cursor: pointer;
        }
        .footer .foot {
            text-align: center;
            color: #FFFFFF;
        }
    </style>
</head>
<body class="easyui-layout">
<!---TOP S--->
<div class="head" region="north" split="true"
     style="height: 100px;">
    <div class="top">
        <div class="logotitle">
            <div class="to3dcbd"> &nbsp;</div>
            <div class="nav" style="font-size: 35px; color:#fff; line-height:70px;">
                智慧管理平台
            </div>
            <div class="welcomequit">您好，<strong id="User" >${(user.nickName)!''}</strong>&nbsp;&nbsp;！ <a href="#" onclick="logoutCommit();">[退出]</a> <a href="#"> [网站首页]</a></div>
        </div>
        <div class="clear"></div>
    </div>
</div>
<!---TOP END--->

<!---foot S--->
<!-- 底部一般放版权什么的 -->
<div class="footer" region="south" split="true" style="height: 10px;">
    <div class="foot">
        Copyright © 2020 All Rights Reserved 版权所有
    </div>
</div>
<!---foot END--->

<!---left S--->
<!-- 左边可以放菜单栏,来显示菜单,可以用tree形式显示 -->
<div region="west" split="true" title="功能选项" style="width: 120px;">
    <div class="easyui-accordion"
         data-options="border:false,fit:true" animate="false">
        <!-- 可用c标签的foreach来读取后台的菜单树 -->
    <#if moduleList?? && moduleList?size &gt; 0>
        <#list moduleList as module>
            <div title="&nbsp;&nbsp;&nbsp;&nbsp;${(module.name)!""}">
                <!--ul的id可以设置成,这样就能出发点击事件或者别的事件 -->
                <#if module.children?? && module.children?size &gt; 0>
                    <#list module.children as resource>
                        <ul style="text-align: center;" onclick="addTab(this.innerHTML,'${(resource.url)!""}')">${(resource.name)!""}</ul>
                    </#list>
                </#if>
            </div>
        </#list>
    </#if>

    </div>
</div>
<!---left END--->

<!---center S--->
<!-- 中间可以用来存放tabs,通过左边点击事件来填充tabs -->
<div region="center"
     style="padding: 5px; background: #eee;">
    <div id="tts" class="easyui-tabs"
         data-options="border:false,fit:true">

        <#--<div id="myCustomer" style="width: 100%;height: 50px;">-->
            <#--<input id="q_responsiblePersonId" type="hidden"  name="q_responsiblePersonId" value="${responsiblePersonId}">-->
            <#--<table id="dg">-->
                <#--<thead>-->
                <#--<tr>-->
                    <#--<th data-options="field:'ck',checkbox:true,formatter : function(value, row, index) {return row.id;}"></th>-->
                    <#--<th data-options="field:'customerName',width:150,halign:'center',align:'left'">客户名称</th>-->
                    <#--<th data-options="field:'phone',width:100,halign:'center',align:'left'">电话</th>-->
                    <#--<th data-options="field:'address',width:250,halign:'center',align:'left'">地址</th>-->
                    <#--<th data-options="field:'manager',width:100,halign:'center',align:'left'">公司法人/总经理</th>-->
                    <#--<th data-options="field:'responsiblePerson',width:100,halign:'center',align:'left'">业务人员</th>-->
                    <#--<th data-options="field:'helpPerson',width:100,halign:'center',align:'left'">协助人</th>-->
                <#--</tr>-->
                <#--</thead>-->
            <#--</table>-->
        <#--</div>-->

    </div>
</div>
<!---center END--->

</body>
<script type="text/javascript">
    //官方demo
    function addTab(title, url){
        if ($('#tts').tabs('exists', title)){
            $('#tts').tabs('select', title);
        } else {
            var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
            $('#tts').tabs('add',{
                title:title,
                content:content,
                closable:true
            });
        }
    }

    //退出登录
    function logoutCommit() {
        $.ajax({
            url:'/logoutCommit',
            dataType : 'json',
            type : 'POST',
            async:false,
            success: function (data){
                window.location.href = "/login.html";
            }
        });
    }

//    $(function(){
//        var q_responsiblePersonId = $("#q_responsiblePersonId").val();
//        var datagrid = $('#dg').datagrid({
//            title:'我的客户',
//            toolbar:'#tb',
//            singleSelect:true,
//            fit:true,
//            fitColumns:true,
//            collapsible: true,
//            rownumbers: true, //显示行数 1，2，3，4...
//            pagination: true, //显示最下端的分页工具栏
//            pagePosition : 'bottom',
//            pageList: [5,10,15,20], //可以调整每页显示的数据，即调整pageSize每次向后台请求数据时的数据
//            pageSize: 20, //读取分页条数，即向后台读取数据时传过去的值
//            queryParams : {
//                'q_responsiblePersonId' : q_responsiblePersonId
//            },
//            url:'/customer/customerList',
//            nowrap : true,
//            border : false
//        });
//    });

</script>
</html>
