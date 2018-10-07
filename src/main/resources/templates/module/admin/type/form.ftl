<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>类型详情</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${request.contextPath}/static/plugin/layui/css/layui.css" media="all">

    <style>
        body {
            overflow-y: scroll;
        }
    </style>
</head>
<body>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>类型详情</legend>
</fieldset>

<form class="layui-form" id="inputForm">
    <input type="hidden" name="id" value="${homeType.id!}">

    <div class="layui-form-item">
        <label class="layui-form-label" for="name">名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" id="name" autocomplete="off" lay-verify="required"
                   placeholder="必填" value="${homeType.name!}" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label" for="sort">排序</label>
        <div class="layui-input-block">
            <input type="text" name="sort" id="sort" autocomplete="off"
                   placeholder="选填" value="${homeType.sort!}" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="submit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            <button class="layui-btn" onclick="goBack()">返回</button>
        </div>
    </div>
</form>
<script src="${request.contextPath}/static/plugin/layui/layui.js" charset="utf-8"></script>
<script src="${request.contextPath}/static/js/layuicommon.js" charset="utf-8"></script>

<script>
    formSubmit('${request.contextPath}/admin/homeType/save');
</script>
</body>
</html>