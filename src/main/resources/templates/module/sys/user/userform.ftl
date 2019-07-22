<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>用户详情</title>
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
    <legend>用户详情</legend>
</fieldset>

<form class="layui-form" id="inputForm">
    <input type="hidden" name="userId" value="${sysUser.userId!}">

    <div class="layui-form-item">
        <label class="layui-form-label">用户名</label>
        <div class="layui-input-block">
            <input type="text" name="username" lay-verify="required" autocomplete="off" placeholder="请输入用户名"
                   value="${sysUser.username!}" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">角色</label>
        <div class="layui-input-block">
            <#list roleList as role>
                <input type="checkbox" name="roleIdList" title="${role.roleName}" value="${role.roleId}"
                       <#if sysUser.roleIdList??>
                           <#list sysUser.roleIdList as roleId>
                       <#if roleId==role.roleId>checked=""</#if>
                           </#list>
                       </#if>
                >
            </#list>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">部门</label>
        <div class="layui-input-block">
            <input type="hidden" name="deptId" id="deptId" value="${sysUser.deptId!}">
            <input type="text" name="deptName" id="deptName" lay-verify="required" autocomplete="off"
                   placeholder="请选择部门"
                   value="${sysUser.deptName!}" class="layui-input" readonly onclick="selectDept()">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">状态</label>
        <div class="layui-input-block">
            <input type="radio" name="status" value="0" title="禁用"
                   <#if sysUser.status??&&sysUser.status==0>checked=""</#if>>
            <input type="radio" name="status" value="1" title="正常"
                   <#if sysUser.status??&&sysUser.status==1>checked=""</#if>>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">密码</label>
        <div class="layui-input-block">
            <input type="password" name="password" autocomplete="off" placeholder="请输入密码"
                   value="" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">邮箱</label>
        <div class="layui-input-block">
            <input type="email" name="email" autocomplete="off" placeholder="请输入对象型号"
                   value="${sysUser.email!}" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label">手机号</label>
        <div class="layui-input-block">
            <input type="text" name="mobile" autocomplete="off" placeholder="请输入手机号" lay-verify="phone"
                   value="${sysUser.mobile!}" class="layui-input">
        </div>
    </div>

<#--<div class="layui-form-item">-->
<#--<label class="layui-form-label">创建时间</label>-->
<#--<div class="layui-input-inline">-->
<#--<input type="text" name="createTime" autocomplete="off" readonly-->
<#--value="${(sysUser.createTime?string("yyyy-MM-dd HH:mm:ss"))!}"-->
<#--class="layui-input datetime">-->
<#--</div>-->
<#--</div>-->

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
    <#if !sysUser.userId??>
         formSubmit('${request.contextPath}/sys/user/save');
    </#if>
    <#if sysUser.userId??>
        formSubmit('${request.contextPath}/sys/user/update');
    </#if>

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