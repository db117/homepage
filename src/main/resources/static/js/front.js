// layui.use(['jquery', 'table', 'form', 'layer', 'util', 'element '], function () {
//     var table = layui.table
//         , form = layui.form
//         , layer = layui.layer
//         , util = layui.util
//         , element = layui.element;
//     $ = layui.jquery;
//
//
// });

// 打开页面
$(".item").click(function () {
    window.open($(this).attr('data'));
});

// 打开页面
$(".link_li").click(function () {
    window.open($(this).attr('data'));
});

// 搜索事件
$("#search").click(function () {
    var searchVal = $("#searchVal").val();
    window.open('https://www.baidu.com/s?wd=' + searchVal);
});

// 回车键事件
$('#searchVal').bind('keyup', function (event) {

    if (event.keyCode == "13") {

        //回车执行查询

        $('#search').click();

    }

});