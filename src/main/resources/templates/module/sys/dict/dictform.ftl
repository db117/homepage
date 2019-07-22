<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>数据字典</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="https://www.layuicdn.com/layui/css/layui.css" media="all">
    <style>
        body {
            overflow-y: scroll;
        }
    </style>
</head>
<body>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>数据字典</legend>
</fieldset>

<form class="layui-form" id="inputForm">
    <input type="hidden" name="id" value="${dict.id!}">

    <div class="layui-form-item">
        <label class="layui-form-label">字典名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" lay-verify="required" autocomplete="off" placeholder="请输入字典名称"
                   value="${dict.name!}" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">字典类型</label>
        <div class="layui-input-block">
            <input type="text" name="type" autocomplete="off" placeholder="请输入字典类型"
                   value="${dict.type!}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">字典码</label>
        <div class="layui-input-block">
            <input type="text" name="code" autocomplete="off" placeholder="请输入字典码"
                   value="${dict.code!}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">字典值</label>
        <div class="layui-input-block">
            <input type="text" name="value" autocomplete="off" placeholder="请输入字典值"
                   value="${dict.value!}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">排序</label>
        <div class="layui-input-block">
            <input type="text" name="orderNum" autocomplete="off" placeholder="请输入排序"
                   value="${dict.orderNum!}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <input type="text" name="remark" autocomplete="off" placeholder="请输入备注"
                   value="${dict.remark!}" class="layui-input">
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
<script src="https://www.layuicdn.com/layui-v2.5.4/layui.js" charset="utf-8"></script>
<script src="${request.contextPath}/static/js/layuicommon.js" charset="utf-8"></script>

<script>
    //监听提交
    <#if !dict.id??>
         formSubmit('${request.contextPath}/sys/dict/save');
    </#if>
    <#if dict.id??>
        formSubmit('${request.contextPath}/sys/dict/update');
    </#if>
</script>
</body>
</html>