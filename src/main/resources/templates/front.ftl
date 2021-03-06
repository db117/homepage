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

    <link rel="stylesheet" href="https://www.layuicdn.com/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${request.contextPath}/static/css/front.css" media="all">
</head>

<body>
<a href="/adminIndex.html" class="layui-btn layui-bg-blue login">管理</a>
<div class="search">
    <form action="https://mijisou.com/" target="_blank">
        <div class="layui-form-item">
            <div class="search_div">
                <input type="text" id="searchVal" name="q" autocomplete="off" placeholder="秘迹搜索" baiduSug="1"
                       class="layui-input">
            </div>
            <button id="search" type="submit" class="layui-btn">秘迹搜索</button>
        </div>
    </form>
</div>

<div class="content">
    <div class="index">
        <ul>
            <#list homeVo.indexList as index>
            <li data="${index.url}" class="item">
                <img class="clickUrl" src="data:image/x-icon;base64,${index.ico}">
                <span class="clickUrl">${index.name}</span>
                </#list>
        </ul>
    </div>

    <div class="clearfloat"></div>

    <div class="link">
        <#list homeVo.typeList as type>
            <div>
                <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
                    <legend>${type.name}</legend>
                </fieldset>
                <ul class="link_ul">
                    <#list type.linkList as link>
                        <li class="link_li" data="${link.url}">
                            <button class="layui-btn layui-bg-green">${link.name}</button>
                        </li>
                    </#list>
                </ul>
            </div>
            <div class="clearfloat"></div>
        </#list>
    </div>

</div>
<#--<script src="https://www.layuicdn.com/layui-v2.5.4/layui.js"></script>-->
<script src="https://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<#--<script src="https://lib.sinaapp.com/js/jquery/2.2.4/jquery-2.2.4.min.js"></script>-->
<script src="${request.contextPath}/static/js/front.js"></script>
<script type="text/javascript" src="https://www.baidu.com/js/opensug.js"></script>
</body>
</html>
