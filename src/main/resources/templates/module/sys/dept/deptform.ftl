<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>机构表单</title>
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
    <legend>部门</legend>
</fieldset>

<form class="layui-form" id="inputForm">
    <input type="hidden" name="deptId" value="${dept.deptId!}">

    <div class="layui-form-item">
        <label class="layui-form-label">部门名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" lay-verify="required" autocomplete="off" placeholder="请输入用户名"
                   value="${dept.name!}" class="layui-input">
        </div>
    </div>


    <div class="layui-form-item">
        <label class="layui-form-label">上级部门</label>
        <div class="layui-input-block">
            <input type="hidden" name="parentId" id="parentId" value="${dept.parentId!}">
            <input type="text" name="parentName" id="parentName" lay-verify="required" autocomplete="off"
                   placeholder="请选择部门"
                   value="${dept.parentName!}" class="layui-input" readonly onclick="selectDept()">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">排序号</label>
        <div class="layui-input-block">
            <input type="number" name="orderNum" autocomplete="off" placeholder="请输入排序号"
                   value="${dept.orderNum!}" class="layui-input">
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
    //监听提交
    <#if !dept.deptId??>
    formSubmit('${request.contextPath}/sys/dept/save');
    </#if>
    <#if dept.deptId??>
         formSubmit('${request.contextPath}/sys/dept/update');
    </#if>
    function selectDept() {
        openSmall('${Request.request.contextPath}/sys/dept/tree.html', '部门', 300, 700)
    };

    function setDept(id, name) {
        var deptId = ${dept.deptId!"-1"};
        if (deptId == id) {
            layer.alert("不能选择本部门为上级部门")
        } else {
            document.getElementById('parentId').setAttribute('value', id);
            document.getElementById('parentName').setAttribute('value', name);
        }
    }

</script>
</body>
</html>