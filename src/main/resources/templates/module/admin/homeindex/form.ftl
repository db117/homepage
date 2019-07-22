<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>链接详情</title>
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
    <legend>链接详情</legend>
</fieldset>

<form class="layui-form" id="inputForm">
    <input type="hidden" name="id" value="${homeIndex.id!}">

    <div class="layui-form-item">
        <label class="layui-form-label" for="name">名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" id="name" autocomplete="off" lay-verify="required"
                   placeholder="必填" value="${homeIndex.name!}" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label" for="url">链接</label>
        <div class="layui-input-block">
            <input type="text" name="url" id="url" lay-verify="required|url" autocomplete="off"
                   placeholder="需要填写http、https" value="${homeIndex.url!}" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label" for="sort">排序</label>
        <div class="layui-input-block">
            <input type="text" name="sort" id="sort" autocomplete="off"
                   placeholder="选填" value="${homeIndex.sort!}" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-upload">
            <label class="layui-form-label">
                <button type="button" class="layui-btn" id="test1">上传图标</button>
            </label>
            <div class="layui-upload-list">
                <input hidden name="ico" id="ico">
                <img class="layui-upload-img" id="demo1" src="data:text/html;base64,${homeIndex.ico!}"
                     style="width: 100px;height: 100px;">
                <p id="demoText"></p>
            </div>
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
    formSubmit('${request.contextPath}/admin/homeIndex/save');

    layui.use('upload', function () {
        var $ = layui.jquery
                , upload = layui.upload;

        //普通图片上传
        var uploadInst = upload.render({
            elem: '#test1'
            , url: '${request.contextPath}/admin/homeIndex/upload'
            , accept: 'file'
            , before: function (obj) {
                //预读本地文件示例，不支持ie8
                obj.preview(function (index, file, result) {
                    $('#demo1').attr('src', result); //图片链接（base64）
                });
            }
            , done: function (res) {
                //如果上传失败
                if (res.code > 0) {
                    return layer.msg('上传失败');
                }
                //上传成功
                $("#ico").val(res.data.src);
            }
            , error: function () {
                //演示失败状态，并实现重传
                var demoText = $('#demoText');
                demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
                demoText.find('.demo-reload').on('click', function () {
                    uploadInst.upload();
                });
            }
        });
    })
</script>
</body>
</html>