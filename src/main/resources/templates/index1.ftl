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
</head>
<style>
    body {
        overflow: scroll;
    }

    .search {
        top: 100px;
        position: absolute;
        left: 50%;
        transform: translateX(-50%); /* 移动元素本身50% */
    }

    .content {
        position: absolute;
        top: 200px;
        width: 80%;
        left: 10%;
        right: 10%;
    }

    .item {
        position: relative;
        float: left;
        width: 7%;
        padding: 1%;
    }

    img {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
    }

    ul span {
        position: absolute;
        top: 90%;
        left: 50%;
        transform: translate(-50%, -50%);
    }
</style>

<body>
<div class="search">
    <div class="layui-form-item">
        <div class="layui-input-inline">
            <input type="text" id="searchVal" autocomplete="off" placeholder="百度一下"
                   class="layui-input">
        </div>
        <button id="search" class="layui-btn">百度一下</button>
    </div>
</div>

<div class="content">
    <ul>
        <#list homeIndex as index>
             <li data="${index.url}" class="item">
            <img class="clickUrl" src="data:image/x-icon;base64,${index.ico}">
            <span class="clickUrl">${index.name}</span>
        </#list>
    </ul>
</div>
<script src="${request.contextPath}/static/plugin/layui/layui.js"></script>
<script src="${request.contextPath}/static/lib/jquery.min.js"></script>
<script>
    //JavaScript代码区域
    layui.use(['jquery', 'table', 'form', 'layer', 'util', 'element '], function () {
        var table = layui.table
                , form = layui.form
                , layer = layui.layer
                , util = layui.util
                , element = layui.element;
        $ = layui.jquery;


    });

    var width = $(".item:first").width();
    $(".item").height(width);
    $("img").height(width * 0.7).width(width * 0.7);
    // $("img").width(width);

    $("li").click(function () {
        window.open('http://' + $(this).attr('data'));
    });
    // 搜索事件
    $("#search").click(function () {
        var searchVal = $("#searchVal").val();
        window.open('https://www.baidu.com/s?wd=' + searchVal);
    });
</script>
</body>
</html>
