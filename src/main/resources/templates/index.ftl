<!DOCTYPE html>

<html>
<head>
    <meta name="viewport" content="width=device-width" />
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <title>智慧管理平台</title>
    <link href="${urls.getForLookupPath('/css/bootstrap.min.css')}" rel="stylesheet"/>
    <link href="${urls.getForLookupPath('/css/animate.css')}" rel="stylesheet" />
    <script src="${urls.getForLookupPath('/js/jquery.min.js')}"></script>


    <script type="text/javascript" src="${urls.getForLookupPath('/js/jQuery.cookie.js')}"></script>
    <link href="${urls.getForLookupPath('/css/style.css')}" rel="stylesheet" />
    <script type="text/javascript" src="${urls.getForLookupPath('/js/default.js')}"></script>
    <link href="${urls.getForLookupPath('/css/Style2.css')}" rel="stylesheet" />

    <link href="${urls.getForLookupPath('/css/jquery.fancybox.css')}" rel="stylesheet"/>

    <script src="${urls.getForLookupPath('/js/jquery.fancybox.js')}"></script>

    <script type="text/javascript">



    </script>




</head>
<body>



<div class="contentBox contentMain contentActive">

    <div class="HeadBox">
        <div class="WebTitle">
            智慧管理平台
        </div>

        <div class="leftMenu userTool"><a href="javascript:void(0);" onclick="window.top.changPwd()" title="修改密码"><img src="${urls.getForLookupPath('/images/password.png')}" /></a></div>


        <div class="rightMenu userTool">
            <a href="/login" title="注销登录" target="_top"><img src="${urls.getForLookupPath('/images/logout.png')}" /></a>
        </div>
    </div>
    <div class="NavBodyBox">
        <table class="NavTable">
            <tr>
                <td class="NavCell">
                    <div class="NavMenuBox">

                        <div class="LeftNavBox" style="left:100px;width:1100px;">



                            <div class="MenuRow">

                                <#--<#if showElecModule?? && showElecModule == "YES">-->
                                <#--<div class="MenuItem" onclick="location.href='firePre.html'">-->
                                    <#--<div class="MenuItem1" style="background-color:#2e8d95">-->
                                        <#--<div class="MenuContent">-->
                                            <#--<div class="MenuPic" style="background-image:url(${urls.getForLookupPath('/images/elec.png')})"></div>-->
                                        <#--</div>-->
                                        <#--<div class="MenuTitle">-->
                                            <#--智慧用电管理-->
                                        <#--</div>-->
                                    <#--</div>-->
                                <#--</div>-->
                                <#--</#if>-->
                                <#--<#if showSetModule?? && showSetModule == "YES">-->
                                <#--<div class="MenuItem" onclick="location.href='/system/device.html'">-->
                                    <#--<div class="MenuItem1" style="background-color:#2e8d95">-->
                                        <#--<div class="MenuContent">-->
                                            <#--<div class="MenuPic" style="background-image:url(${urls.getForLookupPath('/images/elec.png')})"></div>-->
                                        <#--</div>-->
                                        <#--<div class="MenuTitle">-->
                                            <#--设备管理-->
                                        <#--</div>-->
                                    <#--</div>-->
                                <#--</div>-->
                                <#--</#if>-->
                                <#if showUserModule?? && showUserModule == "YES">
                                    <div class="MenuItem" onclick="location.href='/system/user.html'">
                                        <div class="MenuItem1" style="background-color:#2e8d95">
                                            <div class="MenuContent">
                                                <div class="MenuPic" style="background-image:url(${urls.getForLookupPath('/images/elec.png')})"></div>
                                            </div>
                                            <div class="MenuTitle">
                                                用户管理
                                            </div>
                                        </div>
                                    </div>
                                </#if>
                                <#if showDepartmentModule?? && showDepartmentModule == "YES">
                                <div class="MenuItem" onclick="location.href='/system/department.html'">
                                    <div class="MenuItem1" style="background-color:#2e8d95">
                                        <div class="MenuContent">
                                            <div class="MenuPic" style="background-image:url(${urls.getForLookupPath('/images/elec.png')})"></div>
                                        </div>
                                        <div class="MenuTitle">
                                            部门管理
                                        </div>
                                    </div>
                                </div>
                                </#if>
                                <#if showRoleModule?? && showRoleModule == "YES">
                                <div class="MenuItem" onclick="location.href='/system/role.html'">
                                    <div class="MenuItem1" style="background-color:#2e8d95">
                                        <div class="MenuContent">
                                            <div class="MenuPic" style="background-image:url(${urls.getForLookupPath('/images/elec.png')})"></div>
                                        </div>
                                        <div class="MenuTitle">
                                            角色管理
                                        </div>
                                    </div>
                                </div>
                                </#if>
                                <#if showRoleResourceModule?? && showRoleResourceModule == "YES">
                                <div class="MenuItem" onclick="location.href='/system/roleResource.html'">
                                    <div class="MenuItem1" style="background-color:#2e8d95">
                                        <div class="MenuContent">
                                            <div class="MenuPic" style="background-image:url(${urls.getForLookupPath('/images/elec.png')})"></div>
                                        </div>
                                        <div class="MenuTitle">
                                            权限管理
                                        </div>
                                    </div>
                                </div>
                                </#if>






                                <div id="clear"></div>
                            </div>

                        </div>



                        <div id="clear"></div>
                    </div>
                </td>
            </tr>
        </table>

    </div>
</div>

<div class="contentBox contentReport">
    <iframe id="reportFrm" src="" width="100%" height="100%" style="position:absolute;top:0px;left:0px;right:0px;bottom:0px;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>
</div>
<div class="contentBox contentModel">
    <div class="ModelHeadBox">
        <div class="webTitle"></div>
        <div class="navBack">
            <div class="backImg" onclick="goMain();"></div>
            <div class="backTitle">返回首页</div>
        </div>
    </div>
    <div style="position:absolute;top:60px;left:0px;right:0px;bottom:0px;">
        <iframe id="modelFrm" src="" width="100%" height="100%" style="position:absolute;top:0px;left:0px;right:0px;bottom:0px;" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe>
    </div>
</div>
<div class="contentBox contentShow">
    <div class="navBox">
        <div class="navBack">
            <div class="backImg" onclick="goMain();"></div>
            <div class="backTitle">返回首页</div>
        </div>


    </div>

    <div class="rightBodyBox">
        <div class="contentHeadBox">
            <div class="sysNav">
                <ul id="mainTabNav" class="nav nav-tabs">
                    <li role="presentation" id="tab_home" class="active"><a href="#tab_First" aria-controls="home" role="tab" data-toggle="tab"><i class="icon-home"></i>首页</a></li>
                </ul>
            </div>
            <div class="sysLoginDept">
                用户单位：鲁东南大区
            </div>
        </div>
        <div id="mainTabContent" class="tab-content contentBodyBox ">

        </div>

    </div>
    <div class="footBox">

    </div>
</div>

<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
    <div class="modal-dialog" id="editModalDialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="editModalLabel">&nbsp;</h4>
            </div>
            <div class="modal-body" id="editModalBody">
                <iframe id="editModelIfm" marginheight="0" marginwidth="0" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="selModal" tabindex="-1" role="dialog" aria-labelledby="selModalLabel" aria-hidden="true">
    <div class="modal-dialog" id="selModalDialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="selModalLabel">&nbsp;</h4>
            </div>
            <div class="modal-body" id="selModalBody">
                <iframe id="selModelIfm" marginheight="0" marginwidth="0" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="thirdModal" tabindex="-1" role="dialog" aria-labelledby="thirdModalLabel" aria-hidden="true">
    <div class="modal-dialog" id="thirdModalDialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="thirdModalLabel">&nbsp;</h4>
            </div>
            <div class="modal-body" id="thirdModalBody">
                <iframe id="thirdModelIfm" marginheight="0" marginwidth="0" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="fourModal" tabindex="-1" role="dialog" aria-labelledby="fourModalLabel" aria-hidden="true">
    <div class="modal-dialog" id="fourModalDialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="fourModalLabel">&nbsp;</h4>
            </div>
            <div class="modal-body" id="fourModalBody">
                <iframe id="fourModelIfm" marginheight="0" marginwidth="0" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
            </div>
        </div>
    </div>
</div>


<script src="${urls.getForLookupPath('/js/bootstrap.min.js')}"></script>
</body>
</html>
