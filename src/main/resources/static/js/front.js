layui.use(['jquery', 'table', 'form', 'layer', 'util', 'element '], function () {
    var table = layui.table
        , form = layui.form
        , layer = layui.layer
        , util = layui.util
        , element = layui.element;
    $ = layui.jquery;


});

// 图片宽度
var width = $(".item:first").width();
$(".item").height(width);
$("img").height(width * 0.7).width(width * 0.7);
// $("img").width(width);

// 打开页面
$(".item").click(function () {
    window.open('http://' + $(this).attr('data'));
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