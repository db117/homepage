<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <title>大兵首页</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">

    <link rel="stylesheet" href="${request.contextPath}/static/plugin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/static/css/index.css" media="all">
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">首页管理</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
    <#--<ul class="layui-nav layui-layout-left">-->
    <#--<li class="layui-nav-item"><a href="">控制台</a></li>-->
    <#--<li class="layui-nav-item"><a href="">商品管理</a></li>-->
    <#--<li class="layui-nav-item"><a href="">用户</a></li>-->
    <#--<li class="layui-nav-item">-->
    <#--<a href="javascript:;">其它系统</a>-->
    <#--<dl class="layui-nav-child">-->
    <#--<dd><a href="">邮件管理</a></dd>-->
    <#--<dd><a href="">消息管理</a></dd>-->
    <#--<dd><a href="">授权管理</a></dd>-->
    <#--</dl>-->
    <#--</li>-->
    <#--</ul>-->
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="${request.contextPath}/sys/user/info.html?userId=${sysUser.userId}" target="main"
                   style="font-size: large">${sysUser.username}</a>
            <#--<dl class="layui-nav-child">-->
            <#--<dd><a href="">基本资料</a></dd>-->
            <#--<dd><a href="">安全设置</a></dd>-->
            <#--</dl>-->
            </li>
            <li class="layui-nav-item"><a href="${request.contextPath}/logout">退出</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree">
                <#list menuList as prantMenu>
                    <li class="layui-nav-item <#if prantMenu_index==0>layui-nav-itemed</#if>">
                        <a href="javascript:;">${prantMenu.name!}</a>
                        <dl class="layui-nav-child">
                            <#list prantMenu.list as childMenu>
                                <dd><a href="${childMenu.url}" target="main">${childMenu.name}</a></dd>
                            </#list>
                        </dl>
                    </li>
                </#list>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <!-- 内容主体区域 -->
        <iframe id="iframe" name="main" width="100%"
                height="100%"></iframe>
    </div>

    <div class="layui-footer">
        <!-- 底部固定区域 -->
        © 首页管理
    </div>
</div>
<script src="${request.contextPath}/static/plugin/layui/layui.js"></script>
<script src="${request.contextPath}/static/lib/jquery.min.js"></script>
<script>
    //JavaScript代码区域
    layui.use(['element', 'jquery'], function () {
        var element = layui.element;
        var $ = layui.jquery;

        $(".layui-logo").click(function () {
            location.href = '/';
        })
    });
</script>
</body>
</html>
