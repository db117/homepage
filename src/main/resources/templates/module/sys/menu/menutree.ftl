<!DOCTYPE html>
<HTML>
<HEAD>
    <TITLE>部门树</TITLE>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${Request.request.contextPath}/static/plugin/ztree/css/metroStyle/metroStyle.css"
          type="text/css">
    <script type="text/javascript" src="${Request.request.contextPath}/static/lib/jquery.min.js"></script>
    <script type="text/javascript"
            src="${Request.request.contextPath}/static/plugin/ztree/jquery.ztree.all.min.js"></script>
    <SCRIPT type="text/javascript">

        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    idKey: "menuId",
                    pIdKey: "parentId",
                    rootPId: -1
                },
                key: {
                    url: "nourl"
                }
            },
            callback: {
                onClick: zTreeOnClick
            }
        };
        //生成树
        $.get("${Request.request.contextPath}/sys/menu/treeData", function (r) {
            ztree = $.fn.zTree.init($("#treeDemo"), setting, r);
        });

        /**
         * 点击调用父页面的setDept方法
         * @param event
         * @param treeId
         * @param treeNode
         */
        function zTreeOnClick(event, treeId, treeNode) {
            parent.window.setMenu(treeNode.menuId, treeNode.name);

            var index = parent.layer
                    .getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); //再执行关闭
        }
    </SCRIPT>
</HEAD>

<BODY>
<div class="content_wrap">
    <div class="zTreeDemoBackground left">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
</div>
</BODY>
</HTML>