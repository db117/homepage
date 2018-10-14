<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>列表</title>
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
    <legend>访问记录</legend>
</fieldset>
<div class="demoTable layui-form">
    <div class="layui-inline">
        <label class="layui-form-label" for="name">ip</label>
        <div class="layui-input-inline">
            <input class="layui-input" id="ip" autocomplete="off">
        </div>
    </div>

    <button class="layui-btn" data-type="reload">搜索</button>
</div>

<table class="layui-hide" id="LAY_table_object" lay-filter="task"></table>


<script src="${request.contextPath}/static/plugin/layui/layui.js" charset="utf-8"></script>
<script src="${request.contextPath}/static/js/layuicommon.js" charset="utf-8"></script>
<script>
    layui.use(['jquery', 'table', 'form', 'layer', 'util'], function () {
        var table = layui.table
                , form = layui.form
                , layer = layui.layer
                , util = layui.util;
        $ = layui.jquery;

        //表头
        var clos = [//表头
            // {checkbox: true, fixed: true}
            {title: 'ip', field: 'ip'},
            {
                title: '登录时间', field: 'createDate'
                , templet: '<div>{{ layui.util.toDateString(d.createDate) }}</div>'
            }
        ];
        tableRender('#LAY_table_object', '${request.contextPath}/admin/homeAccess/data', clos);


        active = {
            reload: function () {
                //执行重载
                tableReload({
                    ip: $('#ip').val()
                });
            }
        };

        $('.demoTable .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

    });
</script>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
</body>
</html>