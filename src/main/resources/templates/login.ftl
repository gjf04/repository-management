<!DOCTYPE html>

<html>
<head>
    <meta name="viewport" content="width=device-width" />
    <meta http-equiv="content-type" content="text/html;charset=utf-8">
    <title>智慧管理平台</title>
    <link href="${urls.getForLookupPath('/css/bootstrap.min.css')}" rel="stylesheet"/>
    <link href="${urls.getForLookupPath('/css/animate.css')}" rel="stylesheet" />
     <script src="${urls.getForLookupPath('/js/jquery.min.js')}"></script>
    
    
    <link href="${urls.getForLookupPath('/css/login.css')}" rel="stylesheet" />
    <script type="text/javascript">
        //回车登录提交
        function keyLogin() {
            document.onkeydown=function mykeyDown(e){
                e = e||event;
                if(e.keyCode == 13) {//回车键的键值为13
                    loginCommit(); //调用登录按钮的登录事件
                }
                return;
            }
        }

        function setNameItem(name) {
            localStorage.setItem("userName",name);
        }
        function getNameItem() {
            return localStorage.getItem("userName");
        }


        $(function () {
            var userName = $("#userName");
            var getName = getNameItem();
            if (getName) {
                userName.val(getName);
            }
            
            $("#loginBtn").click(function () {
                setNameItem(userName.val());
            });

        });

        function loginCommit() {
            var userName = $("#userName").val();
            var password = $("#password").val();
            if (userName == "") {
                $("#userName").focus();
                alert("用户名不能为空");
                return false;
            }
            if (password == "") {
                $("#password").focus();
                alert("密码不能为空");
                return false;
            }
            $.ajax({
                url:'/loginCommit',
                dataType : 'json',
                type : 'POST',
                async:false,
                data:{userName:userName, password:password},
                success: function (data){
                    if(data.data){
                        window.location.href = "/index.html";
                    }else{
                        alert(data.message);
                    }
                }
            });
        }

    </script>

    
  
</head>
<body onkeydown="keyLogin();">
    
<div class="WebTitle">智慧管理平台</div>
<div class="bodyBox">

    <table style="width:100%; height:100%;">
        <tr>
            <td style="width:100%; height:100%; text-align:center;vertical-align:middle;">
                <div class="LoginBox">
                    <div class="AppBox">
                         <a href="/Login/AppDown" ><img src="${urls.getForLookupPath('/images/erweima.jpg')}" width="160" height="160" alt="下载手机客户端" /></a>
                        <div class="appTitle">
                            <a href="/Login/AppDown" >扫描二维码关注</a>
                        </div>
                    </div>
                    <div class="LoginForm">
                        <div class="tdTitle">
                                用户登录
                        </div>
                        <div>
                            <table class="tabForm" id="tabForm" >
                                <tr>
                                    <td  class="tdLabel">账号:</td>
                                    <td class="tdContent"><input  tabindex="1" title="请输入帐号" id="userName" autocomplete="off"    name="userName" type="text" maxlength="50"  autocomplete="on" value=""/>
                                </tr>
                                <tr>
                                    <td class="tdLabel">密码:</td>
                                    <td class="tdContent"><input class="loginFormTdIpt loginFormTdIpt-focus" tabindex="2" title="请输入密码"  id="password" name="password" type="password" value="" />
                                </tr>
                                <tr>
                                    <td class="tdLabel"></td>
                                    <td class="tdBut"  style="vertical-align:middle;">
                                        <button id="loginBtn" class="logBt"  tabindex="6" onclick="loginCommit();"></button>
                                    </td>
                                </tr>
                            </table>
                        </div>

                    </div>
                </div>
            </td>
        </tr>
    </table>
        
       

</div>
<div class="bottomBox">

</div>

    
    <script src="${urls.getForLookupPath('/js/bootstrap.min.js')}"></script>
</body>
</html>
