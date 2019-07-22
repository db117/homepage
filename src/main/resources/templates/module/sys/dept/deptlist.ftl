<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>tree-table</title>
    <link rel="stylesheet" href="https://www.layuicdn.com/layui/css/layui.css" media="all">
<#--<link rel="stylesheet" href="assets/common.css"/>-->
    <style>
        input {
            height: 33px;
            line-height: 33px;
            padding: 0 7px;
            border: 1px solid #ccc;
            border-radius: 2px;
            margin-bottom: -2px;
            outline: none;
        }

        input:focus {
            border-color: #009E94;
        }
    </style>
</head>
<body>
<div class="layui-container">
    <br><br>
    <div class="layui-btn-group">
        <button class="layui-btn" id="btn-expand">全部展开</button>
        <button class="layui-btn" id="btn-fold">全部折叠</button>
        <button class="layui-btn" id="btn-add">添加部门</button>
    </div>
    &nbsp;&nbsp;
    <input id="edt-search" type="text" placeholder="输入关键字" style="width: 120px;"/>&nbsp;&nbsp;
    <button class="layui-btn" id="btn-search">&nbsp;&nbsp;搜索&nbsp;&nbsp;</button>

</div>
<table id="auth-table" class="layui-table" lay-filter="auth-table"></table>

<!-- 操作列 -->
<script type="text/html" id="auth-state">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="edit">修改</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script src="https://www.layuicdn.com/layui-v2.5.4/layui.js" charset="utf-8"></script>
<script src="${request.contextPath}/static/js/layuicommon.js" charset="utf-8"></script>
<script>
    layui.config({
        base: '${Request.request.contextPath}/static/plugin/layui/lay/modules/'
    }).extend({
        treetable: 'treetable-lay/treetable'
    }).use(['table', 'treetable'], function () {
        var $ = layui.jquery;
        var table = layui.table;
        var treetable = layui.treetable;

        // 渲染表格
        layer.load(2);
        treetable.render({
            treeColIndex: 2,
            treeSpid: 0,
            treeIdName: 'deptId',
            treePidName: 'parentId',
            elem: '#auth-table',
            url: '${Request.request.contextPath}/sys/dept/data',
            page: false,
            cols: [[
                {type: 'numbers'},
                {field: 'name', minWidth: 200, title: '部门名称'},
                {field: 'parentName', title: '上级部门名称'},
                {field: 'orderNum', title: '排序'},
                {templet: '#auth-state', width: 120, align: 'center', title: '操作'}
            ]],
            done: function () {
                layer.closeAll('loading');
            }
        });

        $('#btn-expand').click(function () {
            treetable.expandAll('#auth-table');
        });

        $('#btn-fold').click(function () {
            treetable.foldAll('#auth-table');
        });

        $('#btn-search').click(function () {
            var keyword = $('#edt-search').val();
            var searchCount = 0;
            $('#auth-table').next('.treeTable').find('.layui-table-body tbody tr td').each(function () {
                $(this).css('background-color', 'transparent');
                var text = $(this).text();
                if (keyword != '' && text.indexOf(keyword) >= 0) {
                    $(this).css('background-color', 'rgba(250,230,160,0.5)');
                    if (searchCount == 0) {
                        treetable.expandAll('#auth-table');
                        $('html,body').stop(true);
                        $('html,body').animate({scrollTop: $(this).offset().top - 150}, 500);
                    }
                    searchCount++;
                }
            });
            if (keyword == '') {
                layer.msg("请输入搜索内容", {icon: 5});
            } else if (searchCount == 0) {
                layer.msg("没有匹配结果", {icon: 5});
            }
        });

        $('#btn-add').click(function () {
            openFull('${Request.request.contextPath}/sys/dept/form.html', '添加部门');
        })

        //监听工具条
        table.on('tool(auth-table)', function (obj) {    //注：tool是工具条事件名，test是table原始容器的属性 lay-filter="对应的值"
            var data = obj.data;                    //获得当前行数据
            var layEvent = obj.event;               //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
            var tr = obj.tr;                        //获得当前行 tr 的DOM对象
            var id = data.deptId;

            if (layEvent === 'del') {          //删除
                dataDel(obj, '${request.contextPath}/sys/dept/delete?deptId=' + id);
            } else if (layEvent === 'edit') {        //编辑
                openFull('${request.contextPath}/sys/dept/form.html?deptId=' + id, '编辑');
            }
        });
    });
</script>
</body>
</html>