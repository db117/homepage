

// 打开页面
$(".item").click(function () {
    window.open($(this).attr('data'));
});

// 打开页面
$(".link_li").click(function () {
    window.open($(this).attr('data'));
});


// 回车键事件
$('#searchVal').bind('keyup', function (event) {

    if (event.keyCode == "13") {

        //回车执行查询

        $('#search').click();

    }

});