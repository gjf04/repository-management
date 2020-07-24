<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<#import "/common/macro_dataformat.ftl" as dataformat/>
<#import "/common/macro_controller.ftl" as cont/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<meta http-equiv="X-UA-Compatible" content="IE=8;IE=EDGE" />
<title></title>

<link rel="stylesheet" type="text/css" href="${domainUrlUtil.staticURL}/portal/js/HoorayLibs/hooraylibs.css">
<link rel="stylesheet" type="text/css" href="${domainUrlUtil.staticURL}/portal/img/ui/index.css">
<link type="text/css" rel="stylesheet" media="all" href="${domainUrlUtil.staticURL}/portal/img/skins/default.css" id="window-skin" />

<script type="text/javascript" src="${domainUrlUtil.staticURL}/portal/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${domainUrlUtil.staticURL}/portal/js/HoorayLibs/hooraylibs.js" ></script>
<script type="text/javascript" src="${domainUrlUtil.staticURL}/portal/js/util.js"></script>
<script type="text/javascript" src="${domainUrlUtil.staticURL}/portal/js/core.js"></script>
<script type="text/javascript" src="${domainUrlUtil.staticURL}/portal/js/templates.js"></script>
<script type="text/javascript" src="${domainUrlUtil.staticURL}/portal/js/artDialog4.1.6/jquery.artDialog.js?skin=default"></script>
<script type="text/javascript" src="${domainUrlUtil.staticURL}/portal/js/artDialog4.1.6/plugins/iframeTools.js"></script>

<script language="javascript">
$(function(){
	$.ajaxSetup({ 
		cache: false 
	}); 
})
</script>
</head>
<body class="easyui-layout">
<#assign form=JspTaglibs["/WEB-INF/tld/spring-form.tld"]>