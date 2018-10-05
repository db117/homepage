/**
 * 显示日期 把日期的class加上date
 */
layui.use('laydate', function () {
    var laydate = layui.laydate;
    //日期
    lay('.date').each(function () {
        laydate.render({
            elem: this
            , trigger: 'click'
            , type: 'date'
        });
    });
});
/**
 * 显示日期时间 把日期的class加上datetime
 */
layui.use('laydate', function () {
    var laydate = layui.laydate;
    //日期
    lay('.datetime').each(function () {
        laydate.render({
            elem: this
            , trigger: 'click'
            , type: 'datetime'
        });
    });
});

/**
 * 渲染表格
 * @param id 表格id
 * @param url 请求数据URL
 * @param cols 表头参数
 * @param where 查询条件
 */
function tableRender(id, url, cols, where) {
    layui.use(['element', 'table', 'jquery'], function () {
        var element = layui.element
            , table = layui.table;
        var $ = layui.jquery;

        //方法级渲染
        table.render({
            elem: id
            , url: url
            , request: {
                pageName: 'current'          //页码的参数名称，默认：page
                , limitName: 'size'     //每页数据量的参数名，默认：limit
            }
            , method: 'post'
            , where: where
            , cols: [cols]
            , id: 'reload'          //重载id
            , page: true
            , height: 'full'        //适应性高度
            , limit: 30             //默认分页数
            , cellMinWidth: 100       //最小行宽
            , done: function () {
                var tools = $("[data-field='tools'] :eq(0)").width();
                // console.log(tools);
                if (tools > 500) {
                    tableReload();
                }
            }
        });
    });
}

/**
 * 表格重载
 * @param where 重载条件
 */
function tableReload(where) {
    layui.use('table', function () {
        var table = layui.table;
        table.reload("reload", {
            page: {
                curr: 1 //重新从第 1 页开始
            }
            , where: where
        })
    });
}


/**
 * 打开弹窗
 * @param url 目标URL
 * @param title 标题
 * @param width 宽度
 * @param height 高度
 */
function openSmall(url, title, width, height) {
    layui.use('layer', function () {
        var layer = layui.layer;
        layer.open({
            title: title
            , area: [width + 'px', height + 'px']
            , type: 2
            , fixed: false //不固定
            , maxmin: true
            , content: url
            , scrollbar: false
        });
    });
}

/*placeholder颜色兼容所有浏览器*/


/**
 * 全屏打开弹窗
 * @param url 目标URL
 * @param title 标题
 */
function openFull(url, title) {
    layui.use('layer', function () {
        var layer = layui.layer;
        var full = layer.open({
            title: title
            , type: 2
            , fixed: false //不固定
            , maxmin: true
            , content: url
            , scrollbar: false
        });
        layer.full(full);
    });
}

/**
 * 数据表格删除操作
 * @param obj 列对象
 * @param url 删除的完整URL  包括?id=
 */
function dataDel(obj, url) {
    layui.use(['layer', 'jquery'], function () {
        var $ = layui.jquery;
        layer.confirm("是否确认删除", {
            title: "信息"
        }, function (index) {
            //向服务端发送删除指令
            $.ajax({
                type: "get",
                url: url,
                dataType: "json",
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code === 0) {
                        obj.del();                        //删除对应行（tr）的DOM结构，并更新缓存
                        layer.close(index);
                    } else {
                        layer.alert(data.msg)
                    }
                }
            });
        })
    });
}

/**
 * 数据表格批量删除操作
 * @param url 删除URL
 */
function dataAllDel(url) {
    layui.use(['layer', 'jquery', 'table'], function () {
        var $ = layui.jquery
            , table = layui.table;
        var checkStatus = table.checkStatus('reload')
            , data = checkStatus.data;
        if (data.length === 0) {
            layer.alert('请选择数据!!!');
            return;
        }
        layer.confirm('真的全部删除么', function (index) {
            var ids = '';
            for (var i = 0; i < data.length; i++) {
                ids = ids + ',' + data[i].id;
            }
            //向服务端发送删除指令
            $.ajax({
                type: "get",
                url: url + '?ids=' + ids,
                dataType: "json",
                // contentType:"application/json;charset=UTF-8",
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code === 0) {
                        layer.close(index);
                        location.reload();//刷新页面
                    } else {
                        layer.alert(data.msg)
                    }
                }
            });
        })
    });
}

/**
 * 监听表单提交
 * @param url 提交地址
 */
function formSubmit(url) {
    layui.use(['form', 'jquery', 'layer'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        //监听提交
        form.on('submit(submit)', function (data) {
            var form = new FormData(document.getElementById("inputForm"));
            $.ajax({
                type: "post",
                url: url,
                dataType: "json",
                data: form,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code === 0) {
                        var index = parent.layer
                            .getFrameIndex(window.name); //先得到当前iframe层的索引
                        parent.layer.close(index); //再执行关闭
                        parent.location.reload();//刷新父级页面
                    } else {
                        layer.alert(data.msg)
                    }
                }
            });
            return false;
        });
    })
}

/**
 * 兼容ie下载功能
 * @param url 下载链接
 */
function ieDownload(url) {
    $down = window.parent.document.getElementById("download");
    if ($down == null) {
        $down = window.parent.parent.document.getElementById("download");
    }
    $down.setAttribute('href', url);
    $down.click();
}

/**
 * 弹窗关闭
 */
function goBack() {
    var index = parent.layer
        .getFrameIndex(window.name); //先得到当前iframe层的索引
    parent.layer.close(index); //再执行关闭
}