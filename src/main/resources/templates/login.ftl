<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <title>个人首页</title>
    <link rel="stylesheet" href="${request.contextPath}/static/plugin/login/css/lrtk.css">
</head>
<body>
<!-- 代码 开始 -->
<div id="login">
    <div class="wrapper">
        <div class="login">
            <form id="inputForm" method="post" class="container offset1 loginform">
                <div id="owl-login">
                    <div class="hand"></div>
                    <div class="hand hand-r"></div>
                    <div class="arms">
                        <div class="arm"></div>
                        <div class="arm arm-r"></div>
                    </div>
                </div>
                <div class="pad">
                <#--<input type="hidden" name="_csrf" value="9IAtUxV2CatyxHiK2LxzOsT6wtBE6h8BpzOmk=">-->
                    <div class="control-group">
                        <div class="controls">
                            <label for="username" class="control-label fa fa-envelope"></label>
                            <input id="username" type="text" name="username" placeholder="请输入用户名" tabindex="1"
                                   autofocus="autofocus" class="form-control input-medium">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <label for="password" class="control-label fa fa-asterisk"></label>
                            <input id="password" type="password" name="password" placeholder="请输入密码" tabindex="2"
                                   class="form-control input-medium">
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <label for="rememberMe" class="control-label fa fa-asterisk">记住我</label>
                            <input type="checkbox" id="rememberMe" name="rememberMe">
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                <#--<a href="http://www.htmlsucai.com" tabindex="5"-->
                <#--class="btn pull-left btn-link text-muted">Forgot password?</a>-->
                <#--<a href="http://www.htmlsucai.com" tabindex="6" class="btn btn-link text-muted">Sign Up</a>-->
                    <button type="button" id="sum" tabindex="4" class="btn btn-primary">登录</button>
                </div>
            </form>
        </div>
    </div>
    <script src="${request.contextPath}/static/lib/jquery.min.js"></script>
    <script type="text/javascript">
        $(function () {

            $('#login #password').focus(function () {
                $('#owl-login').addClass('password');
            }).blur(function () {
                $('#owl-login').removeClass('password');
            });
        });
    </script>
    <script type="text/javascript">
        $("#sum").click(function () {

            if ($("#username").val() == null || $("#username").val() == '' || $("#password").val() == null | $("#password").val() == "") {
                alert("请输入账号密码");
            } else {
                var form = new FormData(document.getElementById("inputForm"));
                $.ajax({
                    type: "post",
                    url: "${request.contextPath}/sys/login",
                    dataType: "json",
                    data: form,
                    contentType: false,
                    processData: false,
                    success: function (data) {
                        if (data.code === 0) {
                            window.location.href = "${request.contextPath}/adminIndex.html"
                        } else {
                            alert(data.msg)
                        }
                    }
                });
            }
        })

    </script>
</div>
<!-- 代码 结束 -->
</body>
</html>