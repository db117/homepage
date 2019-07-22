<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>用户列表</title>
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
    <div class="layui-btn-group demoTable">
        <button class="layui-btn layui-btn-normal layui-btn-radius" data-type="addObject">添加用户</button>
    </div>
</fieldset>
<div class="demoTable layui-form">
    <div class="layui-inline">
        <label class="layui-form-label" for="username">用户名</label>
        <div class="layui-input-inline">
            <input class="layui-input" id="username" autocomplete="off">
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
            {title: '用户名', field: 'username', width: '15%'},
            {title: '所属部门', field: 'deptName', sortable: false, width: '15%'},
            {title: '邮箱', field: 'email', width: '15%'},
            {title: '手机号', field: 'mobile', width: '15%'},
            {
                title: '状态', field: 'status', width: '15%', templet: function (d) {
                    return d.status === 0 ?
                            '<span style="color: #3fcc2f;">' + '禁用' + '</span>' :
                            '<span style="color: #6fcca8;">' + '正常' + '</span>';
                }
            },
            {
                title: '创建时间', field: 'createTime', width: '15%'
                , templet: '<div>{{ layui.util.toDateString(d.createTime) }}</div>'
            }
            , {field: 'tools', fixed: 'right', title: '操作', width: '10%', align: 'center', toolbar: '#barDemo'}
        ];
        tableRender('#LAY_table_object', '${request.contextPath}/sys/user/data', clos);

        //监听工具条
        table.on('tool(task)', function (obj) {    //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data;                    //获得当前行数据
            var layEvent = obj.event;               //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr;                        //获得当前行 tr 的DOM对象
            var id = data.userId;

            if (layEvent === 'del') {          //删除
                dataDel(obj, '${request.contextPath}/sys/user/delete?userIds=' + id);
            } else if (layEvent === 'edit') {        //编辑
                openFull('${request.contextPath}/sys/user/form.html?userId=' + id, '编辑');
            }
        });

        active = {
            reload: function () {
                //执行重载
                tableReload({
                    username: $('#username').val()
                });
            }
            , addObject: function () {
                openFull('${request.contextPath}/sys/user/form.html', '添加用户');
            }
        };

        $('.demoTable .layui-btn').on('click', function () {
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //监听复选框选择
        table.on('checkbox(testReload)', function (obj) {
            console.log(obj.checked); //当前是否选中状态
            console.log(obj.data); //选中行的相关数据
            console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
        });
    });
</script>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>
</body>
</html>