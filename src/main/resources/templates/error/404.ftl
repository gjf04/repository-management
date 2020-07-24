<!DOCTYPE html>
<html lang="en">
<head>
    <title>错误提示</title>
    <!-- 页面必引 start -->
    <#include "/include/header_css_and_js.ftl">
    <!-- 页面必引 end -->
    <style>
        .noaddressList{
            width: 100%;
            height: auto;
            overflow: hidden;
            text-align: center;
        }
        .noaddressList>img{
            width: 1.65rem;
            height: 1.1rem;
            margin-top: 0.55rem;

        }
        .noaddressList>p{
            font-size: 0.13rem;
            color: #757575;
            margin-top: 0.15rem;
        }
        .goHome{
            width: 200px;
            height: 40px;
            line-height: 40px;
            border:1px solid #60ba72;
            color: #60ba72;
            border-radius: 0.05rem;
            margin: 0 auto;
            margin-top: 30px;
        }
    </style>
</head>
<body>
<div class="boss">
    <!-- 没有地址显示 区域-->
    <div class="noaddressList">
        <img src="../images/404.png" alt="">
        <p>服务器休息啦~</p>
        <a href="/index.html"><div class="goHome">返回首页</div></a>
    </div>
    <!-- 返回按钮 -->
    <#include "/include/gobye_btn.ftl">
</div>
<!-- 页面必引 start -->
<#include "/include/footer_js.ftl">
<!-- 页面必引 end -->
<script>
    $(function(){
        $('.boss').css('height',$(document).height()+'px')
    })
</script>
</body>
</html>