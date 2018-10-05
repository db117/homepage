<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8"/>
    <title>个人首页</title>
    <link rel="stylesheet" type="text/css" href="${request.contextPath}/static/plugin/layui/css/layui.css">
</head>

<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <a href="">
            <div class="layui-logo">个人首页管理</div>
        </a>
        <!-- 头部区域（可配合layui已有的水平导航） -->
    <#--<ul class="layui-nav layui-layout-left">-->
    <#--<li class="layui-nav-item">-->
    <#--<a href="/">返回前台</a>-->
    <#--</li>-->
    <#--<li class="layui-nav-item">-->
    <#--<a href="">商品管理</a>-->
    <#--</li>-->
    <#--<li class="layui-nav-item">-->
    <#--<a href="">用户</a>-->
    <#--</li>-->
    <#--</ul>-->
        <ul class="layui-nav layui-layout-right">
            <a href="javascript:void(0);" class="layTabPlus"
               tab_url="${request.contextPath}/sys/user/info.html?userId=${sysUser.userId}"
               style="font-size: large">个人信息</a>
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
                                <dd><a href="javascript:void(0);" class="layTabPlus"
                                       tab_url="${childMenu.url}">${childMenu.name}</a></dd>
                            </#list>
                        </dl>
                    </li>
                </#list>
            </ul>
        </div>
    </div>
<#-- style="background: linear-gradient(to left bottom, hsl(198, 100%, 85%) 0%,hsl(215, 100%, 85%) 100%);"-->
    <div class="layui-body"
         style="background: url('${request.contextPath}/statics/images/bg1.png')no-repeat;background-size: 100% 100%">
        <!-- 内容主体区域 -->
        <div class="layui-tab" lay-filter="demoTab" lay-allowClose="true">
            <ul class="layui-tab-title">
            <#--<li class="layui-this" lay-id='base_info'>基本信息</li>-->
            </ul>
            <div class="layui-tab-content" style="padding:0px;">
            <#--<div class="layui-tab-item layui-show">-->
            <#--<div class="sysNotice col">-->
            <#--<blockquote class="layui-elem-quote title">基本信息</blockquote>-->
            <#--<table class="layui-table">-->
            <#--<tbody>-->
            <#--<tr>-->
            <#--<td>当前版本</td>-->
            <#--<td class="version">V1.2</td>-->
            <#--</tr>-->
            <#--<tr>-->
            <#--<td>开发作者</td>-->
            <#--<td class="author">Leytton</td>-->
            <#--</tr>-->
            <#--<tr>-->
            <#--<td>项目首页</td>-->
            <#--<td class="homePage"><a href="https://gitee.com/Leytton/layTabPlus">https://gitee.com/Leytton/layTabPlus</a></td>-->
            <#--</tr>-->
            <#--<tr>-->
            <#--<td colspan="2">-->
            <#--<script src='https://gitee.com/Leytton/layTabPlus/widget_preview'></script>-->
            <#--<style>-->
            <#--.pro_name a{color: #4183c4;}-->
            <#--.osc_git_title{background-color: #fff;}-->
            <#--.osc_git_box{background-color: #fff;}-->
            <#--.osc_git_box{border-color: #E3E9ED;}-->
            <#--.osc_git_info{color: #666;}-->
            <#--.osc_git_main a{color: #9B9B9B;}-->
            <#--</style>-->
            <#--</td>-->
            <#--</tr>-->
            <#--</tbody>-->
            <#--</table>-->
            <#--</div>-->
            <#--</div>-->
            </div>
        </div>
    </div>
    <div class="layui-footer">
        © 2018 <a>数据服务平台</a>
    </div>
    <a style="display: none" id="download"></a>
</div>
<script src="${request.contextPath}/static/lib/jquery.min.js" charset="utf-8"></script>
<script src="${request.contextPath}/static/plugin/layui/layui.js"></script>
<script>
    var $ = layui.jquery;
    var layer = layui.layer;
    var element = layui.element;
    var util = layui.util;
    var table = layui.table;
</script>
<script src="${request.contextPath}/static/plugin/layTabPlus.js"></script>
<script>
    layTabPlus.init({
        lay_filter: 'demoTab'
    });
    $("dd :eq(0)").click();
    $(function () {

        $(".layui-nav-item").eq(2).hide();
    })
</script>
</body>

</html>
