<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>菜单表单</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="${request.contextPath}/static/plugin/layui/css/layui.css" media="all">
    <style>
        body {
            overflow-y: scroll;
        }
    </style>
</head>
<body>

<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
    <legend>菜单</legend>
</fieldset>

<form class="layui-form" id="inputForm">
    <input type="hidden" name="menuId" value="${menu.menuId!}">

    <div class="layui-form-item">
        <label class="layui-form-label">类型</label>
        <div class="layui-input-block">
            <input lay-filter="menuType" type="radio" name="type" value="0" title="目录"
                   <#if menu.type??&&menu.type==0>checked=""</#if>>
            <input lay-filter="menuType" type="radio" name="type" value="1" title="菜单"
                   <#if !menu.type??||(menu.type??&&menu.type==1)>checked=""</#if>>
            <input lay-filter="menuType" type="radio" name="type" value="2" title="按钮"
                   <#if menu.type??&&menu.type==2>checked=""</#if>>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">菜单名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" lay-verify="required" autocomplete="off" placeholder="请输入菜单名称"
                   value="${menu.name!}" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">上级菜单</label>
        <div class="layui-input-block">
            <input type="hidden" name="parentId" id="parentId" value="${menu.parentId!}">
            <input type="text" name="parentName" id="parentName" lay-verify="required" autocomplete="off"
                   placeholder="请选择上级菜单"
                   value="${menu.parentName!}" class="layui-input" readonly onclick="selectMenu()">
        </div>
    </div>
    <div class="layui-form-item" id="urlShow">
        <label class="layui-form-label">菜单URL</label>
        <div class="layui-input-block">
            <input type="text" name="url" autocomplete="off" placeholder="请输入菜单URL"
                   value="${menu.url!}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item" id="permsShow">
        <label class="layui-form-label">授权</label>
        <div class="layui-input-block">
            <input type="text" name="perms" autocomplete="off" placeholder="请输入授权"
                   value="${menu.perms!}" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item" id="iocnShow">
        <label class="layui-form-label">菜单图标</label>
        <div class="layui-input-block">
            <input type="text" name="icon" autocomplete="off" placeholder="请输入菜单图标"
                   value="${menu.icon!}" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item" id="numShow">
        <label class="layui-form-label">排序号</label>
        <div class="layui-input-block">
            <input type="number" name="orderNum" autocomplete="off" placeholder="请输入排序号"
                   value="${menu.orderNum!}" class="layui-input">
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
<script src="${request.contextPath}/static/plugin/layui/layui.js" charset="utf-8"></script>
<script src="${request.contextPath}/static/js/layuicommon.js" charset="utf-8"></script>
<script>
    layui.use(['form', 'jquery'], function () {
        var form = layui.form;
        $ = layui.jquery;

        form.on('radio(menuType)', function (data) {
            // console.log(data.elem); //得到radio原始DOM对象
            // console.log(data.value); //被点击的radio的value值
            selectShow(data.value);
        });
    })
</script>
<script>
    //监听提交
    <#if !menu.menuId??>
    formSubmit('${request.contextPath}/sys/menu/save');
    </#if>
    <#if menu.menuId??>
         formSubmit('${request.contextPath}/sys/menu/update');
         selectShow(${menu.type})
    </#if>

    //弹窗选择父级菜单
    function selectMenu() {
        openSmall('${Request.request.contextPath}/sys/menu/tree.html', '部门', 300, 700)
    }

    /**
     * 设置父级菜单
     */
    function setMenu(id, name) {
        // alert(id + name);
        var menuId = ${menu.menuId!"-1"};
        if (menuId == id) {
            layer.alert("不能选择自己")
        } else {
            document.getElementById('parentId').setAttribute('value', id);
            document.getElementById('parentName').setAttribute('value', name);
        }
    }

    /**
     * 选择显示的内容
     * @param type 菜单类型
     */
    function selectShow(type) {
        var $url = document.getElementById('urlShow');
        var $permsShow = document.getElementById('permsShow');
        var $iocnShow = document.getElementById('iocnShow');
        var $numShow = document.getElementById('numShow');

        if (type == 0) {
            $url.style.display = 'none';
            $permsShow.style.display = 'none';
            $iocnShow.style.display = 'block';
            $numShow.style.display = 'block';
        }
        if (type == 1) {
            $url.style.display = 'block';
            $permsShow.style.display = 'block';
            $iocnShow.style.display = 'block';
            $numShow.style.display = 'block';
        }
        if (type == 2) {
            $url.style.display = 'none';
            $permsShow.style.display = 'block';
            $iocnShow.style.display = 'none';
            $numShow.style.display = 'none';
        }
    }
</script>
</body>
</html>