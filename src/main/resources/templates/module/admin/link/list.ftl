<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>网址</title>
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
        <button class="layui-btn layui-btn-normal layui-btn-radius" data-type="addObject">添加网址</button>
    </div>
</fieldset>
<div class="demoTable layui-form">
    <div class="layui-inline">
        <label class="layui-form-label" for="name">名称</label>
        <div class="layui-input-inline">
            <input class="layui-input" id="name" autocomplete="off">
        </div>
    </div>

    <div class="layui-inline">
        <label class="layui-form-label">分类</label>
        <div class="layui-input-inline">
            <select name="typeId" id="typeId" lay-verify="required" lay-search="">
                <option value="">直接选择或搜索选择</option>
                <#list types as type>
                    <option value="${type.id}" <#if typeId =type.id>selected</#if>>${type.name}</option>
                </#list>
            </select>
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
            {title: '名称', field: 'name'},
            {title: '链接', field: 'url'},
            {title: '排序', field: 'sort'},
            {
                title: '创建时间', field: 'createDate'
                , templet: '<div>{{ layui.util.toDateString(d.createDate) }}</div>'
            }
            , {field: 'tools', fixed: 'right', title: '操作', align: 'center', toolbar: '#barDemo'}
        ];
        tableRender('#LAY_table_object', '${request.contextPath}/admin/homeLink/data', clos, {
            typeId:${typeId}
        });

        //监听工具条
        table.on('tool(task)', function (obj) {    //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data;                    //获得当前行数据
            var layEvent = obj.event;               //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr;                        //获得当前行 tr 的DOM对象
            var id = data.id;

            if (layEvent === 'del') {          //删除
                dataDel(obj, '${request.contextPath}/admin/homeLink/delete?id=' + id);
            } else if (layEvent === 'edit') {        //编辑
                openFull('${request.contextPath}/admin/homeLink/form.html?id=' + id, '编辑');
            }
        });

        active = {
            reload: function () {
                //执行重载
                tableReload({
                    name: $('#name').val()
                    , typeId: $('#typeId').val()
                });
            }
            , addObject: function () {
                openFull('${request.contextPath}/admin/homeLink/form.html', '添加网址');
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