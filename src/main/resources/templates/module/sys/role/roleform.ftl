<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>角色</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="https://www.layuicdn.com/layui/css/layui.css" media="all">
<#--<link rel="stylesheet" href="${Request.request.contextPath}/static/plugin/ztree/css/metroStyle/metroStyle.css"-->
<#--type="text/css">-->
<#--<script type="text/javascript" src="${Request.request.contextPath}/static/lib/jquery.min.js"></script>-->
<#--<script type="text/javascript"-->
<#--src="${Request.request.contextPath}/static/plugin/ztree/jquery.ztree.all.min.js"></script>-->
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/zTree.v3/3.5.33/js/jquery.ztree.all.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/zTree.v3/3.5.33/css/metroStyle/metroStyle.min.css"
          rel="stylesheet">
    <style>
        body {
            overflow-y: scroll;
        }
    </style>
</head>
<body>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>角色</legend>
</fieldset>

<form class="layui-form" id="inputForm">
    <input type="hidden" name="roleId" value="${role.roleId!}">

    <div class="layui-form-item">
        <label class="layui-form-label">角色名称</label>
        <div class="layui-input-block">
            <input type="text" name="roleName" lay-verify="required" autocomplete="off" placeholder="请输入角色名称"
                   value="${role.roleName!}" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">部门</label>
        <div class="layui-input-block">
            <input type="hidden" name="deptId" id="deptId" value="${role.deptId!}">
            <input type="text" name="deptName" id="deptName" lay-verify="required" autocomplete="off"
                   placeholder="请选择部门"
                   value="${role.deptName!}" class="layui-input" readonly onclick="selectDept()">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">备注</label>
        <div class="layui-input-block">
            <input type="text" name="remark" autocomplete="off" placeholder="请输入备注"
                   value="${role.remark!}" class="layui-input">
        </div>
    </div>

    <div class="layui-row">
        <div class="layui-col-xs6">
            <ul id="menuTree" class="ztree"></ul>
        </div>
        <div class="layui-col-xs6">
            <ul id="dataTree" class="ztree"></ul>
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn" lay-submit="" lay-filter="submit">立即提交</button>
            <button type="reset" class="layui-btn layui-btn-primary">重置</button>
            <button class="layui-btn" onclick="goBack()">返回</button>
        </div>
    </div>
</form>
<script src="https://www.layuicdn.com/layui-v2.5.4/layui.js" charset="utf-8"></script>
<script src="${request.contextPath}/static/js/layuicommon.js" charset="utf-8"></script>
<script>
    //菜单树
    var menu_ztree;
    var menu_setting = {
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
        check: {
            enable: true,
            nocheckInherit: true
        }
    };
    //加载菜单树
    $.get("${Request.request.contextPath}/sys/menu/treeData", function (r) {
        menu_ztree = $.fn.zTree.init($("#menuTree"), menu_setting, r);
        //展开所有节点
        menu_ztree.expandAll(true);

        <#if role.deptId??>
        var menuIds = [];
            <#list role.menuIdList as menuId>
                 menuIds.push("${menuId}");
            </#list>
        for (var i = 0; i < menuIds.length; i++) {
            var node = menu_ztree.getNodeByParam("menuId", menuIds[i]);
            menu_ztree.checkNode(node, true, false);
        }
        </#if>
    });


    //数据树
    var data_ztree;
    var data_setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "deptId",
                pIdKey: "parentId",
                rootPId: -1
            },
            key: {
                url: "nourl"
            }
        },
        check: {
            enable: true,
            nocheckInherit: true,
            chkboxType: {"Y": "", "N": ""}
        }
    };

    //加载数据树
    $.get("${Request.request.contextPath}/sys/dept/treeData", function (r) {
        data_ztree = $.fn.zTree.init($("#dataTree"), data_setting, r);
        //展开所有节点
        data_ztree.expandAll(true);

         <#if role.deptIdList??>
        var deptIds = [];
             <#list role.deptIdList as menuId>
                 deptIds.push("${menuId}");
             </#list>
        for (var i = 0; i < deptIds.length; i++) {
            var node = data_ztree.getNodeByParam("deptId", deptIds[i]);
            data_ztree.checkNode(node, true, false);
        }
         </#if>
    });

    // setTimeout(selectData, 1000);

    function selectData() {
        //获取选择的菜单
        var nodes = menu_ztree.getCheckedNodes(true);
        for (var i = 0; i < nodes.length; i++) {
            var input = document.createElement("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("value", nodes[i].menuId);
            input.setAttribute("name", "menuIdList[" + i + "]");

            document.getElementById("inputForm").appendChild(input);
        }

        //获取选择的数据
        var nodes = data_ztree.getCheckedNodes(true);
        for (var i = 0; i < nodes.length; i++) {
            var input = document.createElement("input");
            input.setAttribute("type", "hidden");
            input.setAttribute("value", nodes[i].deptId);
            input.setAttribute("name", "deptIdList[" + i + "]");

            document.getElementById("inputForm").appendChild(input);
        }
    }

</script>
<script>
    //监听提交
    var url = '${request.contextPath}/sys/role/<#if !role.roleId??>save</#if><#if role.roleId??>update</#if>';

    layui.use(['form', 'jquery', 'layer'], function () {
        var form = layui.form;
        var $ = layui.jquery;
        //监听提交
        form.on('submit(submit)', function (data) {
            selectData();
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
    <#--formSubmit('${request.contextPath}/sys/role/save');-->

    /**
     * 部门赋值
     * @param id 部门id
     * @param name 部门名称
     */
    function setDept(id, name) {
        document.getElementById('deptId').setAttribute('value', id);
        document.getElementById('deptName').setAttribute('value', name);
    };

    /**
     * 选择部门
     */
    function selectDept() {
        openSmall('${Request.request.contextPath}/sys/dept/tree.html', '部门', 300, 700)
    }
</script>
</body>
</html>