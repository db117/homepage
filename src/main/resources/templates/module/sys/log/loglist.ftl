<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>系统日志</title>
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

<div class="demoTable layui-form">
    <div class="layui-inline">
        <label class="layui-form-label" for="username">用户名</label>
        <div class="layui-input-inline">
            <input class="layui-input" id="username" autocomplete="off" type="text">
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label" for="operation">用户操作</label>
        <div class="layui-input-inline">
            <input class="layui-input" id="operation" autocomplete="off" type="text">
        </div>
    </div>

    <button class="layui-btn" data-type="reload">搜索</button>
</div>

<table class="layui-hide" id="LAY_table_object" lay-filter="task"></table>


<script src="https://www.layuicdn.com/layui-v2.5.4/layui.js" charset="utf-8"></script>
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
            {title: '用户名', field: 'username', width: '10%'},
            {title: '用户操作', field: 'operation', width: '10%'},
            {title: '请求方法', field: 'method', width: '20%'},
            {title: '请求参数', field: 'params', width: '20%'},
            {title: '执行时长(毫秒)', field: 'time', width: '10%'},
            {title: 'IP地址', field: 'ip', width: '15%'},
            {title: '创建时间', field: 'createDate', width: '15%'}

        ];
        tableRender('#LAY_table_object', '${request.contextPath}/sys/log/data', clos);


        active = {
            reload: function () {
                //执行重载
                tableReload({
                    username: $('#username').val()
                    , operation: $('#operation').val()
                });
            }
        };

        $('.demoTable .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });
    });
</script>
</body>
</html>